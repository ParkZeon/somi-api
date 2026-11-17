package com.somi.somi_api.notes;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Reads daily notes from SOMI-OS.
 * Layout (see SOMI-OS SCHEMA.md): knowledge/admin/daily-notes/YYYY/MM-Month/YYYY-MM-DD.md
 */
@Service
public class DailyNoteService {

    private static final DateTimeFormatter MONTH_DIR = DateTimeFormatter.ofPattern("MM-MMMM", Locale.ENGLISH);

    private final Path notesRoot;

    public DailyNoteService(@Value("${somi.root}") Path somiRoot) {
        this.notesRoot = somiRoot.resolve("knowledge/admin/daily-notes").normalize();
    }

    /** Parses an ISO date (YYYY-MM-DD). Throws IllegalArgumentException with a readable message. */
    public LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date.strip());
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("Date must be YYYY-MM-DD, got: " + date);
        }
    }

    Path pathFor(LocalDate date) {
        return notesRoot
                .resolve(String.valueOf(date.getYear()))
                .resolve(date.format(MONTH_DIR))
                .resolve(date + ".md");
    }

    /** The note's markdown, or empty if no note exists for that day. */
    public Optional<String> read(LocalDate date) {
        Path file = pathFor(date);
        if (!Files.isRegularFile(file)) {
            return Optional.empty();
        }
        try {
            // Strip a UTF-8 BOM left by the old Windows hooks.
            return Optional.of(Files.readString(file, StandardCharsets.UTF_8).replaceFirst("^﻿", ""));
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + file, e);
        }
    }
}
