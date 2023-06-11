package com.example.ngonew;

public class Event {
    private String eventName;
    private String eventDescription;
    private String eventDate;

    public Event() {
        // Default constructor required for Firebase
    }

    public Event(String eventName, String eventDescription, String eventDate) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }
}
