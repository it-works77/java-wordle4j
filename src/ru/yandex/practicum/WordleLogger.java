package ru.yandex.practicum;

import ru.yandex.practicum.exception.LoggerException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class WordleLogger {
    private PrintStream ps;

    public WordleLogger() {
        ps = System.out;
    }

    public WordleLogger(String filename) throws LoggerException {
        try {
            ps = new PrintStream(filename, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new LoggerException("No such file", e);
        } catch (IOException e) {
            throw new LoggerException("Logger IOException", e);
        }
    }

    public void critical(String... messages) {
        log(new StringBuilder("CRITICAL: "), messages);
    }

    public void error(String... messages) {
        log(new StringBuilder("ERROR: "), messages);
    }

    public void warning(String... messages) {
        log(new StringBuilder("WARNING: "), messages);
    }

    public void info(String... messages) {
        log(new StringBuilder("INFO: "), messages);
    }

    // Todo with stacktrace
    public void debug(String... messages) {
        log(new StringBuilder("DEBUG: "), messages);
    }

    private void log(StringBuilder sb, String... messages) {
        for (String msg : messages) {
            sb.append(msg).append(" ");
        }
        ps.println(sb.toString());
    }

    public void close() {
        // Стандартный поток вывода не надо закрывать
        if (ps != System.out) {
            ps.close();
        }
    }
}
