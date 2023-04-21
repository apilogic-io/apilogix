package io.gihub.apilogic.transformations.processor;

import java.util.Map;

public class ProcessorStep<I, O> implements Step<I, O>{

    private final Processor<I, O> processor;

    public ProcessorStep(Processor<I, O> processor) {
        this.processor = processor;
    }

    @Override
    public O process(I input, Map<String, Object> context) {
        processor.setContext(context);
        return processor.process(input);
    }
}
