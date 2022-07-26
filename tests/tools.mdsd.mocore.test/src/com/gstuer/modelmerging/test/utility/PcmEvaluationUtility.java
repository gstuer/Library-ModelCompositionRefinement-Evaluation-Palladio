package com.gstuer.modelmerging.test.utility;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;

import com.gstuer.modelmerging.instance.pcm.surrogate.element.Component;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Deployment;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.ServiceEffectSpecification;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Signature;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.InterfaceProvisionRelation;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.InterfaceRequirementRelation;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.ServiceEffectSpecificationRelation;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.SignatureProvisionRelation;

public final class PcmEvaluationUtility {
    private PcmEvaluationUtility() {
        throw new IllegalStateException("Utility class cannot be instantiated.");
    }

    public static boolean representSame(OperationSignature signature,
            org.palladiosimulator.pcm.repository.Signature otherSignature) {
        if (otherSignature instanceof OperationSignature) {
            return representSame(signature, (OperationSignature) otherSignature);
        }
        return false;
    }

    public static boolean representSame(OperationSignature signature, OperationSignature otherSignature) {
        boolean equalName = Objects.equals(signature.getEntityName(), otherSignature.getEntityName());
        boolean equalReturn = Objects.equals(signature.getReturnType__OperationSignature(),
                otherSignature.getReturnType__OperationSignature());
        boolean equalParameters = areCollectionsEqualIgnoringOrder(signature.getParameters__OperationSignature(),
                otherSignature.getParameters__OperationSignature());
        return equalName && equalReturn && equalParameters;
    }

    public static boolean representSame(OperationInterface interFace, Interface otherInterFace) {
        if (otherInterFace instanceof OperationInterface) {
            return representSame(interFace, (OperationInterface) otherInterFace);
        }
        return false;
    }

    public static boolean representSame(OperationInterface interFace, OperationInterface otherInterFace) {
        boolean equalName = Objects.equals(interFace.getEntityName(), otherInterFace.getEntityName());
        boolean equalProtocols = areCollectionsEqualIgnoringOrder(interFace.getProtocols__Interface(),
                otherInterFace.getProtocols__Interface());
        boolean equalCharacterisations = areCollectionsEqualIgnoringOrder(interFace.getRequiredCharacterisations(),
                otherInterFace.getRequiredCharacterisations());
        return equalName && equalProtocols && equalCharacterisations;
    }

    public static boolean representSame(BasicComponent component, RepositoryComponent otherComponent) {
        if (otherComponent instanceof BasicComponent) {
            return representSame(component, (BasicComponent) otherComponent);
        }
        return false;
    }

    public static boolean representSame(BasicComponent component, BasicComponent otherComponent) {
        boolean equalName = Objects.equals(component.getEntityName(), otherComponent.getEntityName());
        boolean equalType = Objects.equals(component.getComponentType(), otherComponent.getComponentType());
        boolean equalParameterUsages = areCollectionsEqualIgnoringOrder(
                component.getComponentParameterUsage_ImplementationComponentType(),
                otherComponent.getComponentParameterUsage_ImplementationComponentType());
        return equalName && equalType && equalParameterUsages;
    }

    public static boolean representSame(ResourceContainer container, ResourceContainer otherContainer) {
        boolean equalName = Objects.equals(container.getEntityName(), otherContainer.getEntityName());
        boolean equalActiveResourceSpecifications = areCollectionsEqualIgnoringOrder(
                container.getActiveResourceSpecifications_ResourceContainer(),
                otherContainer.getActiveResourceSpecifications_ResourceContainer());
        boolean equalHddResourceSpecifications = areCollectionsEqualIgnoringOrder(
                container.getHddResourceSpecifications(), otherContainer.getHddResourceSpecifications());
        return equalName && equalActiveResourceSpecifications && equalHddResourceSpecifications;
    }

    public static boolean representSame(ResourceDemandingSEFF seff,
            org.palladiosimulator.pcm.seff.ServiceEffectSpecification otherSeff) {
        if (otherSeff instanceof ResourceDemandingSEFF) {
            return representSame(seff, (ResourceDemandingSEFF) otherSeff);
        }
        return false;
    }

    public static boolean representSame(ResourceDemandingSEFF seff, ResourceDemandingSEFF otherSeff) {
        boolean equalIdentifier = Objects.equals(seff.getId(), otherSeff.getId());
        boolean equalTypeIdentifier = Objects.equals(seff.getSeffTypeID(), otherSeff.getSeffTypeID());
        boolean equalSteps = areCollectionsEqualIgnoringOrder(seff.getSteps_Behaviour(),
                otherSeff.getSteps_Behaviour());
        boolean equalInternalBehaviors = areCollectionsEqualIgnoringOrder(
                seff.getResourceDemandingInternalBehaviours(),
                otherSeff.getResourceDemandingInternalBehaviours());
        boolean equalLoopAction = Objects.equals(seff.getAbstractLoopAction_ResourceDemandingBehaviour(),
                otherSeff.getAbstractLoopAction_ResourceDemandingBehaviour());
        boolean equalBranchTransition = Objects.equals(seff.getAbstractBranchTransition_ResourceDemandingBehaviour(),
                otherSeff.getAbstractBranchTransition_ResourceDemandingBehaviour());
        return equalIdentifier && equalTypeIdentifier && equalSteps && equalInternalBehaviors
                && equalLoopAction && equalBranchTransition;
    }

    public static Optional<BasicComponent> getRepresentative(Repository repository, Component component) {
        List<RepositoryComponent> components = repository.getComponents__Repository();
        for (RepositoryComponent repositoryComponent : components) {
            if (representSame(component.getValue(), repositoryComponent)) {
                return Optional.of((BasicComponent) repositoryComponent);
            }
        }
        return Optional.empty();
    }

    public static Optional<OperationInterface> getRepresentative(Repository repository,
            com.gstuer.modelmerging.instance.pcm.surrogate.element.Interface interFace) {
        List<Interface> interfaces = repository.getInterfaces__Repository();
        for (Interface repositoryInterface : interfaces) {
            if (representSame(interFace.getValue(), repositoryInterface)) {
                return Optional.of((OperationInterface) repositoryInterface);
            }
        }
        return Optional.empty();
    }

    public static boolean containsRepresentative(Repository repository, Component component) {
        return getRepresentative(repository, component).isPresent();
    }

    public static boolean containsRepresentative(Repository repository,
            com.gstuer.modelmerging.instance.pcm.surrogate.element.Interface interFace) {
        return getRepresentative(repository, interFace).isPresent();
    }

    public static boolean containsRepresentative(Repository repository,
            InterfaceProvisionRelation interfaceProvision) {
        OperationInterface wrappedInterface = interfaceProvision.getDestination().getValue();
        Optional<BasicComponent> optionalComponent = getRepresentative(repository, interfaceProvision.getSource());
        if (optionalComponent.isPresent()) {
            List<ProvidedRole> roles = optionalComponent.get().getProvidedRoles_InterfaceProvidingEntity();
            return roles.stream()
                    .filter(role -> role instanceof OperationProvidedRole)
                    .map(role -> (OperationProvidedRole) role)
                    .map(OperationProvidedRole::getProvidedInterface__OperationProvidedRole)
                    .anyMatch(interFace -> representSame(wrappedInterface, interFace));
        } else {
            return false;
        }
    }

    public static boolean containsRepresentative(Repository repository,
            InterfaceRequirementRelation interfaceRequirement) {
        OperationInterface wrappedInterface = interfaceRequirement.getDestination().getValue();
        Optional<BasicComponent> optionalComponent = getRepresentative(repository, interfaceRequirement.getSource());
        if (optionalComponent.isPresent()) {
            List<RequiredRole> roles = optionalComponent.get().getRequiredRoles_InterfaceRequiringEntity();
            return roles.stream()
                    .filter(role -> role instanceof OperationRequiredRole)
                    .map(role -> (OperationRequiredRole) role)
                    .map(OperationRequiredRole::getRequiredInterface__OperationRequiredRole)
                    .anyMatch(interFace -> representSame(wrappedInterface, interFace));
        } else {
            return false;
        }
    }

    public static boolean containsRepresentative(Repository repository, SignatureProvisionRelation signatureProvision) {
        Optional<OperationInterface> optionalOperationInterface = getRepresentative(repository,
                signatureProvision.getDestination());
        return optionalOperationInterface.isPresent()
                && optionalOperationInterface.get().getSignatures__OperationInterface().stream()
                        .anyMatch(signature -> representSame(signatureProvision.getSource().getValue(), signature));
    }

    public static boolean containsRepresentative(Repository repository,
            ServiceEffectSpecificationRelation seffProvision) {
        Component provider = seffProvision.getSource().getSource().getSource();
        Signature signature = seffProvision.getSource().getDestination().getSource();
        ServiceEffectSpecification seff = seffProvision.getDestination();

        Optional<BasicComponent> optionalComponent = getRepresentative(repository, provider);
        if (optionalComponent.isPresent()) {
            BasicComponent component = optionalComponent.get();
            for (org.palladiosimulator.pcm.seff.ServiceEffectSpecification componentSeff : component
                    .getServiceEffectSpecifications__BasicComponent()) {
                if (representSame(seff.getValue(), componentSeff)) {
                    ResourceDemandingSEFF componentRdSeff = (ResourceDemandingSEFF) componentSeff;
                    return representSame(provider.getValue(),
                            componentRdSeff.getBasicComponent_ServiceEffectSpecification())
                            && representSame(signature.getValue(), componentRdSeff.getDescribedService__SEFF())
                            && containsRepresentative(repository, seffProvision.getSource().getSource())
                            && containsRepresentative(repository, seffProvision.getSource().getDestination());
                }
            }
        }
        return false;
    }

    public static boolean containsRepresentative(ResourceEnvironment resourceEnvironment, Deployment container) {
        List<ResourceContainer> containers = resourceEnvironment.getResourceContainer_ResourceEnvironment();
        return containers.stream()
                .anyMatch(environmentContainer -> representSame(container.getValue(), environmentContainer));
    }

    private static <T> boolean areCollectionsEqualIgnoringOrder(Collection<T> collection,
            Collection<T> otherCollection) {
        return collection.containsAll(otherCollection) && otherCollection.containsAll(collection);
    }
}
