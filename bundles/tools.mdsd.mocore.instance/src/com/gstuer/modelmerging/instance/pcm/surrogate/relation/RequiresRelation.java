package com.gstuer.modelmerging.instance.pcm.surrogate.relation;

import com.gstuer.modelmerging.framework.surrogate.Relation;
import com.gstuer.modelmerging.framework.surrogate.Replaceable;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Component;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Interface;

public class RequiresRelation extends Relation<Component, Interface> {
    public RequiresRelation(Component source, Interface destination, boolean isPlaceholder) {
        super(source, destination, isPlaceholder);
    }

    @Override
    public <U extends Replaceable> RequiresRelation replace(U original, U replacement) {
        if (!this.canReplace(original)) {
            // TODO Add message to exception
            throw new IllegalArgumentException();
        }
        if (this.equals(original)) {
            return (RequiresRelation) replacement;
        }
        Component source = getSourceReplacement(original, replacement);
        Interface destination = getDestinationReplacement(original, replacement);
        return new RequiresRelation(source, destination, this.isPlaceholder());
    }
}
