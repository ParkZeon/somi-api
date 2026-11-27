package com.somi.somi_api.notes;

import java.time.LocalDate;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.somi.somi_api.PlainTextResultConverter;

/** MCP tools over daily notes. Method descriptions are what Claude sees. */
@Component
public class DailyNoteTools {

    private final DailyNoteService notes;

    public DailyNoteTools(DailyNoteService notes) {
        this.notes = notes;
    }

    @Tool(name = "get_daily_note", resultConverter = PlainTextResultConverter.class,
          description = "Read the boss's daily note (schedule, running log, reflection) for one day. "
                  + "Returns the markdown, or a message saying no note exists for that day.")
    public String getDailyNote(
            @ToolParam(description = "Day in YYYY-MM-DD. Omit or pass 'today' for today.", required = false)
            String date) {
        LocalDate day = (date == null || date.isBlank() || date.equalsIgnoreCase("today"))
                ? LocalDate.now()
                : notes.parseDate(date);
        return notes.read(day).orElse("No daily note for " + day + ".");
    }
}
