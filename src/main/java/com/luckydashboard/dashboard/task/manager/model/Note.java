package com.luckydashboard.dashboard.task.manager.model;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class Note {
    private String noteId; // No @Id annotation here

    private Date timeCreated;
    private String noteText;
    private String createdBy;

    // Constructor to generate random UUID for noteId
    public Note() {
        this.noteId = UUID.randomUUID().toString();
    }
}
