package ru.yandex.practicum;

import ru.yandex.practicum.exception.LoggerException;
import ru.yandex.practicum.model.SeverityLevel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class WordleLogger {
    private final PrintStream ps;
    private final SeverityLevel logLevel;

    public WordleLogger() {
        ps = System.out;
        logLevel = SeverityLevel.DEBUG;
    }

    public WordleLogger(SeverityLevel logLevel, String filename) throws LoggerException {
        try {
            ps = new PrintStream(filename, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new LoggerException("No such file", e);
        } catch (IOException e) {
            throw new LoggerException("Logger IOException", e);
        }
        this.logLevel = logLevel;
    }

    public void critical(String... messages) {
        if (logLevel.isLoggable(SeverityLevel.CRITICAL)) {
            log(new StringBuilder(SeverityLevel.CRITICAL.getName()).append(": "), messages);
        }
    }

    public void error(String... messages) {
        if (logLevel.isLoggable(SeverityLevel.ERROR)) {
            log(new StringBuilder(SeverityLevel.ERROR.getName()).append(": "), messages);
        }
    }

    public void warning(String... messages) {
        if (logLevel.isLoggable(SeverityLevel.WARNING)) {
            log(new StringBuilder(SeverityLevel.WARNING.getName()).append(": "), messages);
        }
    }

    public void info(String... messages) {
        if (logLevel.isLoggable(SeverityLevel.INFO)) {
            log(new StringBuilder(SeverityLevel.INFO.getName()).append(": "), messages);
        }
    }

    public void debug(String... messages) {
        if (logLevel.isLoggable(SeverityLevel.DEBUG)) {
            log(new StringBuilder(SeverityLevel.DEBUG.getName()).append(": "), messages);
        }
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
