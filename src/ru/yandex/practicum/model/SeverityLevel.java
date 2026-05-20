package ru.yandex.practicum.model;

public enum SeverityLevel {
    CRITICAL(50, "CRITICAL"),
    ERROR(40, "ERROR"),
    WARNING(30, "WARNING"),
    INFO(20, "INFO"),
    DEBUG(10, "DEBUG");

    private final int priority;
    private final String name;

    SeverityLevel(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public boolean isLoggable(SeverityLevel level) {
        return priority <= level.getPriority();
    }
}
