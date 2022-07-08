package com.gstuer.modelmerging.instance.pcm.transformation;

import java.util.List;

import org.palladiosimulator.generator.fluent.repository.api.Repo;
import org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory;
import org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationInterfaceCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.Repository;

import com.gstuer.modelmerging.framework.transformation.Transformer;
import com.gstuer.modelmerging.instance.pcm.surrogate.PcmSurrogate;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Component;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Interface;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Signature;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.InterfaceProvisionRelation;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.InterfaceRequirementRelation;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.SignatureProvisionRelation;

public class RepositoryTransformer implements Transformer<PcmSurrogate, Repository> {
    @Override
    public Repository transform(PcmSurrogate model) {
        FluentRepositoryFactory repositoryFactory = new FluentRepositoryFactory();
        Repo fluentRepository = repositoryFactory.newRepository();

        List<InterfaceProvisionRelation> provisionRelations = model.getByType(InterfaceProvisionRelation.class);
        List<InterfaceRequirementRelation> requirementRelations = model.getByType(InterfaceRequirementRelation.class);
        List<SignatureProvisionRelation> signatureRelations = model.getByType(SignatureProvisionRelation.class);
        List<Interface> interfaces = model.getByType(Interface.class);

        // Add interfaces to fluent repository
        for (Interface interfaceInstance : interfaces) {
            OperationInterfaceCreator interfaceCreator = getCreator(interfaceInstance);

            // Add signatures
            for (SignatureProvisionRelation relation : signatureRelations) {
                if (relation.getDestination().equals(interfaceInstance)) {
                    Signature signature = relation.getSource();
                    OperationSignatureCreator signatureCreator = getCreator(signature);
                    interfaceCreator.withOperationSignature(signatureCreator);
                }
            }

            fluentRepository.addToRepository(interfaceCreator);
        }

        // Add components to fluent repository
        for (Component component : model.getByType(Component.class)) {
            BasicComponentCreator componentCreator = getCreator(component);

            // Add provided interfaces
            for (InterfaceProvisionRelation relation : provisionRelations) {
                if (relation.getSource().equals(component)) {
                    String interfaceName = relation.getDestination().getValue().getEntityName();
                    OperationInterface operationInterface = repositoryFactory.fetchOfOperationInterface(interfaceName);
                    componentCreator.provides(operationInterface);
                }
            }

            // Add required interfaces
            for (InterfaceRequirementRelation relation : requirementRelations) {
                if (relation.getSource().equals(component)) {
                    String interfaceName = relation.getDestination().getValue().getEntityName();
                    OperationInterface operationInterface = repositoryFactory.fetchOfOperationInterface(interfaceName);
                    componentCreator.requires(operationInterface);
                }
            }

            fluentRepository.addToRepository(componentCreator);
        }

        return fluentRepository.createRepositoryNow();
    }

    private BasicComponentCreator getCreator(Component component) {
        BasicComponentCreator componentCreator = new FluentRepositoryFactory().newBasicComponent();

        // TODO Identify important information within wrapped component
        // Copy information from wrapped component, dismiss deprecated information.
        BasicComponent wrappedComponent = component.getValue();
        componentCreator.withName(wrappedComponent.getEntityName());

        return componentCreator;
    }

    private OperationInterfaceCreator getCreator(Interface interfaceInstance) {
        OperationInterfaceCreator interfaceCreator = new FluentRepositoryFactory().newOperationInterface();

        // TODO Identify important information within wrapped interface
        // Copy information from wrapped interface, dismiss deprecated information.
        OperationInterface wrappedInterface = interfaceInstance.getValue();
        interfaceCreator.withName(wrappedInterface.getEntityName());

        return interfaceCreator;
    }

    private OperationSignatureCreator getCreator(Signature signature) {
        OperationSignatureCreator signatureCreator = new FluentRepositoryFactory().newOperationSignature();

        // Copy information from wrapped signature, dismiss deprecated information.
        OperationSignature wrappedSignature = signature.getValue();
        signatureCreator.withName(wrappedSignature.getEntityName());
        signatureCreator.withReturnType(wrappedSignature.getReturnType__OperationSignature());
        for (Parameter parameter : wrappedSignature.getParameters__OperationSignature()) {
            signatureCreator.withParameter(parameter.getParameterName(), parameter.getDataType__Parameter(),
                    parameter.getModifier__Parameter());
        }

        return signatureCreator;
    }
}