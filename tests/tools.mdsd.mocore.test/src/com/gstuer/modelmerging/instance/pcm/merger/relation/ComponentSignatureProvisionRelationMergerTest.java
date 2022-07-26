package com.gstuer.modelmerging.instance.pcm.merger.relation;

import com.gstuer.modelmerging.framework.merger.RelationMergerTest;
import com.gstuer.modelmerging.instance.pcm.surrogate.PcmSurrogate;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Component;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Interface;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Signature;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.ComponentSignatureProvisionRelation;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.InterfaceProvisionRelation;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.SignatureProvisionRelation;

public class ComponentSignatureProvisionRelationMergerTest
        extends RelationMergerTest<ComponentSignatureProvisionRelationMerger, PcmSurrogate,
                ComponentSignatureProvisionRelation, InterfaceProvisionRelation, SignatureProvisionRelation> {
    private static final Interface RELATION_INTERFACE = Interface.getUniquePlaceholder();

    @Override
    protected ComponentSignatureProvisionRelation createRelation(InterfaceProvisionRelation source,
            SignatureProvisionRelation destination, boolean isPlaceholder) {
        return new ComponentSignatureProvisionRelation(source, destination, isPlaceholder);
    }

    @Override
    protected InterfaceProvisionRelation getUniqueNonPlaceholderSourceEntity() {
        Component source = Component.getUniquePlaceholder();
        return new InterfaceProvisionRelation(source, RELATION_INTERFACE, false);
    }

    @Override
    protected InterfaceProvisionRelation getPlaceholderOfSourceEntity(InterfaceProvisionRelation source) {
        return new InterfaceProvisionRelation(source.getSource(), source.getDestination(), true);
    }

    @Override
    protected SignatureProvisionRelation getUniqueNonPlaceholderDestinationEntity() {
        Signature signature = Signature.getUniquePlaceholder();
        return new SignatureProvisionRelation(signature, RELATION_INTERFACE, false);
    }

    @Override
    protected SignatureProvisionRelation getPlaceholderOfDestinationEntity(SignatureProvisionRelation destination) {
        return new SignatureProvisionRelation(destination.getSource(), destination.getDestination(), true);
    }

    @Override
    protected ComponentSignatureProvisionRelationMerger createMerger(PcmSurrogate model) {
        return new ComponentSignatureProvisionRelationMerger(model);
    }

    @Override
    protected PcmSurrogate createEmptyModel() {
        return new PcmSurrogate();
    }
}