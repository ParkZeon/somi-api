# somi-api

SOMI MCP server: a Spring Boot + Spring AI service that exposes the boss's SOMI-OS data to Claude as MCP tools.

## Tools

| Tool | What it does |
|---|---|
| `get_daily_note(date?)` | Reads `knowledge/admin/daily-notes/YYYY/MM-Month/YYYY-MM-DD.md` (defaults to today) |

## Run

```bash
./run.sh                      # http://127.0.0.1:8080/mcp (localhost only)
SOMI_ROOT=/path/to/SOMI-OS ./run.sh
```

Connect Claude Code (once, from the SOMI-OS folder):

```bash
claude mcp add --transport http somi http://127.0.0.1:8080/mcp
```

## Stack

Java 21, Spring Boot 4.1, Spring AI 2.0 MCP server (Streamable HTTP), JUnit 5.

## Roadmap

See `knowledge/dev-skills/somi-api-practice-plan.md` in SOMI-OS.
