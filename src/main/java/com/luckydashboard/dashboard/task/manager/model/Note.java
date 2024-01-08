package com.luckydashboard.dashboard.task.manager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Note {
@Id
    private String noteId;
    private Date timeCreated;
    private String noteText;
    private String createdBy;
}

