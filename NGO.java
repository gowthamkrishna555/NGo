package com.example.ngonew;

public class NGO {
    private String ngoName;
    private String description;
    private String eventsDescription;

    public NGO() {
        // Default constructor required for Firebase Realtime Database
    }

    public NGO(String ngoName, String description, String eventsDescription) {
        this.ngoName = ngoName;
        this.description = description;
        this.eventsDescription = eventsDescription;
    }

    // Getters and setters for the NGO class
    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventsDescription() {
        return eventsDescription;
    }

    public void setEventsDescription(String eventsDescription) {
        this.eventsDescription = eventsDescription;
    }
}
