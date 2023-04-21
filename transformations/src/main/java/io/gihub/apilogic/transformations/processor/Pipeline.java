package io.gihub.apilogic.transformations.processor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Pipeline<I, O> {
    private final Step<I, O> current;
    private final Map<String, Object> context = new HashMap<>();
    public Pipeline(Step<I, O> current) {
        this.current = current;
    }

    public <NewO> Pipeline<I, NewO> pipe(Step<O, NewO> next) {
        return new Pipeline<>((input, context) -> next.process(current.process(input, context), context));
    }
    public O execute(I input) {
        context.put("om", new ObjectMapper());
        context.put("env", System.getenv());
        return current.process(input, context);
    }

}
