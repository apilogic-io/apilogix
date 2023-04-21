package io.gihub.apilogic.transformations.processor;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public abstract class Processor<I, O> {

    private Map<String, Object> context = new HashMap<>();
    public abstract O process(I input);

    @SneakyThrows
    public InputStream getPathInputStream(Path path) {
        InputStream is;
        try {
            is = Objects.requireNonNull(getClass()
                    .getResourceAsStream(path.toString()));
        }
        catch (Exception e) {
            is = new FileInputStream(path.toString());
        }
        return is;
    }

    @SneakyThrows
    public String getVtlResult(Path template, Map<String, Object> input) {
        var stepContext = getContext();
        var context = new VelocityContext();
        input.forEach(context::put);
        stepContext.forEach(context::put);
        InputStream is = getPathInputStream(template);
        Reader templateReader = new BufferedReader(

                new InputStreamReader(is));

        StringWriter swOut = new StringWriter();
        Velocity.evaluate(context, swOut, "log tag name", templateReader);
        return swOut.toString();
    }

}
