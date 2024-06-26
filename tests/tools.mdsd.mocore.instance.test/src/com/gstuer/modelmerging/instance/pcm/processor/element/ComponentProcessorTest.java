package com.gstuer.modelmerging.instance.pcm.processor.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import tools.mdsd.mocore.framework.processor.ProcessorTest;
import tools.mdsd.mocore.framework.surrogate.Replaceable;
import com.gstuer.modelmerging.instance.pcm.surrogate.PcmSurrogate;
import com.gstuer.modelmerging.instance.pcm.surrogate.element.Component;
import com.gstuer.modelmerging.instance.pcm.surrogate.relation.ComponentAllocationRelation;

public class ComponentProcessorTest extends ProcessorTest<ComponentProcessor, PcmSurrogate, Component> {
    @Test
    @DisabledIf(TEST_API_ONLY_METHOD_NAME)
    public void testRefineWithValidElementAddsCorrectImplications() {
        // Test data
        PcmSurrogate model = createEmptyModel();
        ComponentProcessor processor = createProcessor(model);
        Component element = createUniqueReplaceable();

        // Assertions: Pre-execution
        assertTrue(processor.getImplications().isEmpty());

        // Execution
        processor.refine(element);
        Set<Replaceable> implications = processor.getImplications();

        // Assertions: Post-execution
        assertEquals(1, implications.size());
        Replaceable implication = implications.stream().findFirst().orElseThrow();
        assertEquals(ComponentAllocationRelation.class, implication.getClass());
        ComponentAllocationRelation relation = (ComponentAllocationRelation) implication;
        assertEquals(element, relation.getSource());
        assertTrue(relation.isPlaceholder());
        assertTrue(relation.getDestination().isPlaceholder());
    }

    @Override
    protected ComponentProcessor createProcessor(PcmSurrogate model) {
        return new ComponentProcessor(model);
    }

    @Override
    protected PcmSurrogate createEmptyModel() {
        return new PcmSurrogate();
    }

    @Override
    protected Component createUniqueReplaceable() {
        return Component.getUniquePlaceholder();
    }
}
