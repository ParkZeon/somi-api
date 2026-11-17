package com.somi.somi_api;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.somi.somi_api.notes.DailyNoteTools;

/** Registers @Tool methods with the MCP server. Add new tool beans here. */
@Configuration
public class McpToolsConfig {

    @Bean
    ToolCallbackProvider somiTools(DailyNoteTools dailyNoteTools) {
        return MethodToolCallbackProvider.builder().toolObjects(dailyNoteTools).build();
    }
}
