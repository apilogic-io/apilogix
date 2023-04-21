package io.gihub.apilogic.transformations.processor;

import java.util.Map;

public interface Step<I, O> {
    O process(I input, Map<String, Object> context);
}
