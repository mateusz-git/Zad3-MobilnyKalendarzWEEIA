package com.example.Mobilny.Kalendarz.WEEIA;

public class EventDay {
    int day;
    String description;

    public EventDay(int day, String description) {
        this.day = day;
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EventDay{" +
                "day=" + day +
                ", description='" + description + '\'' +
                '}';
    }
}
