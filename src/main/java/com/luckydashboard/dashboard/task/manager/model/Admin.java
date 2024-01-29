package com.luckydashboard.dashboard.task.manager.model;


import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Admin")
@Data
public class Admin {
    @Id
    private String id;
    private double initalInvestment;
    private double dailyCollection;
    private  int numberOfActiveLoans;
  }

