package com.gstuer.modelmerging.instance.pcm.merger.relation;

import com.gstuer.modelmerging.framework.merger.RelationMergerTest;
import com.gstuer.modelmerging.instance.pcm.surrogate.PcmSurrogate;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Component;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Deployment;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.ComponentAllocationRelation;
import com.gstuer.modelmerging.test.utility.IdentifierGenerator;

public class ComponentAllocationRelationMergerTest extends RelationMergerTest<ComponentAllocationRelationMerger,
        PcmSurrogate, ComponentAllocationRelation, Component, Deployment> {
    @Override
    protected ComponentAllocationRelation createRelation(Component source, Deployment destination,
            boolean isPlaceholder) {
        return new ComponentAllocationRelation(source, destination, isPlaceholder);
    }

    @Override
    protected Component getUniqueNonPlaceholderSourceEntity() {
        return new Component(IdentifierGenerator.getUniqueIdentifier(), false);
    }

    @Override
    protected Component getPlaceholderOfSourceEntity(Component source) {
        return new Component(source.getValue(), true);
    }

    @Override
    protected Deployment getUniqueNonPlaceholderDestinationEntity() {
        return new Deployment(IdentifierGenerator.getUniqueIdentifier(), false);
    }

    @Override
    protected Deployment getPlaceholderOfDestinationEntity(Deployment destination) {
        return new Deployment(destination.getValue(), true);
    }

    @Override
    protected ComponentAllocationRelationMerger createMerger(PcmSurrogate model) {
        return new ComponentAllocationRelationMerger(model);
    }

    @Override
    protected PcmSurrogate createEmptyModel() {
        return new PcmSurrogate();
    }
}