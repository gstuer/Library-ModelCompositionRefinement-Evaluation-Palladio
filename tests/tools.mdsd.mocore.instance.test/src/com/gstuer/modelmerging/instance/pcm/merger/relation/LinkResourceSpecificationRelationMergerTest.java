package com.gstuer.modelmerging.instance.pcm.merger.relation;

import com.gstuer.modelmerging.framework.merger.RelationProcessorTest;
import com.gstuer.modelmerging.instance.pcm.surrogate.PcmSurrogate;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Deployment;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.LinkResourceSpecification;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.DeploymentDeploymentRelation;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.LinkResourceSpecificationRelation;
import com.gstuer.modelmerging.instance.pcm.utility.ElementFactory;

public class LinkResourceSpecificationRelationMergerTest
        extends RelationProcessorTest<LinkResourceSpecificationRelationMerger, PcmSurrogate,
                LinkResourceSpecificationRelation, LinkResourceSpecification, DeploymentDeploymentRelation> {
    @Override
    protected LinkResourceSpecificationRelation createRelation(LinkResourceSpecification source,
            DeploymentDeploymentRelation destination, boolean isPlaceholder) {
        return new LinkResourceSpecificationRelation(source, destination, isPlaceholder);
    }

    @Override
    protected LinkResourceSpecification getUniqueNonPlaceholderSourceEntity() {
        return ElementFactory.createUniqueLinkResourceSpecification(false);
    }

    @Override
    protected LinkResourceSpecification getPlaceholderOfSourceEntity(LinkResourceSpecification source) {
        return new LinkResourceSpecification(source.getValue(), true);
    }

    @Override
    protected DeploymentDeploymentRelation getUniqueNonPlaceholderDestinationEntity() {
        return new DeploymentDeploymentRelation(Deployment.getUniquePlaceholder(),
                Deployment.getUniquePlaceholder(), false);
    }

    @Override
    protected DeploymentDeploymentRelation getPlaceholderOfDestinationEntity(DeploymentDeploymentRelation destination) {
        return new DeploymentDeploymentRelation(destination.getSource(), destination.getDestination(), true);
    }

    @Override
    protected LinkResourceSpecificationRelationMerger createProcessor(PcmSurrogate model) {
        return new LinkResourceSpecificationRelationMerger(model);
    }

    @Override
    protected PcmSurrogate createEmptyModel() {
        return new PcmSurrogate();
    }
}
