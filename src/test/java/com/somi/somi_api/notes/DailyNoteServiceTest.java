package com.somi.somi_api.notes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DailyNoteServiceTest {

    @TempDir
    Path root;

    @Test
    void readsNoteFromYearMonthFolder() throws Exception {
        Path dir = Files.createDirectories(root.resolve("knowledge/admin/daily-notes/2026/11-November"));
        Files.writeString(dir.resolve("2026-11-17.md"), "﻿# 2026-11-17\n");

        var service = new DailyNoteService(root);

        assertThat(service.read(LocalDate.of(2026, 11, 17))).contains("# 2026-11-17\n");
    }

    @Test
    void missingNoteIsEmpty() {
        var service = new DailyNoteService(root);

        assertThat(service.read(LocalDate.of(2026, 1, 1))).isEmpty();
    }

    @Test
    void rejectsBadDate() {
        var service = new DailyNoteService(root);

        assertThatThrownBy(() -> service.parseDate("17/11/2026"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("YYYY-MM-DD");
    }
}
