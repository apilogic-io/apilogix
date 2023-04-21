package io.gihub.apilogic.transformations.processor;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.bazaarvoice.jolt.Transform;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public abstract class AbstractJoltParserProcessor<I, O> extends Processor<I, O> {

    private final Transform jsonTransformation;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AbstractJoltParserProcessor(Path reqTransformations) {
        jsonTransformation = joltTransform(reqTransformations);
    }

    private Transform joltTransform(Path reqTransformations) {
        List<Object> specs;
        log.info("Loading the jolt transformation path {}", reqTransformations);
        try {
            specs = JsonUtils.classpathToList(reqTransformations.toString());
        } catch (Exception e) {
            specs = JsonUtils.filepathToList(reqTransformations.toString());
        }
        return Chainr.fromSpec(specs);
    }
}
