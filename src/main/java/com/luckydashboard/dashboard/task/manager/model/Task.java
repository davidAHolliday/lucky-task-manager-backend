package com.luckydashboard.dashboard.task.manager.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Document(collection = "Task")
public class Task {
@Id
private String taskId;
    private String taskName;
    private String taskDescription;
    private String assignedTo;
    private Date timeCreated;
    private List<String> tags;
    private List<Note> notes;
    private String status;
    private Date dueDate;
    private Date timeClosed;
    private ArrayList<CheckListItem> checkListItems;


}


