package com.luckydashboard.dashboard.task.manager.model;

import lombok.Data;

@Data
public class CheckListItem {
    private String itemText;
    private int index;
    private boolean doneStatus;
    private Photo photo;
}

