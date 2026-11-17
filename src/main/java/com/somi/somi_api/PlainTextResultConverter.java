package com.somi.somi_api;

import java.lang.reflect.Type;

import org.springframework.ai.tool.execution.DefaultToolCallResultConverter;
import org.springframework.ai.tool.execution.ToolCallResultConverter;

/** Returns String tool results as-is (the default converter JSON-quotes them); everything else as JSON. */
public class PlainTextResultConverter implements ToolCallResultConverter {

    private final DefaultToolCallResultConverter json = new DefaultToolCallResultConverter();

    @Override
    public String convert(Object result, Type returnType) {
        return result instanceof String s ? s : json.convert(result, returnType);
    }
}
