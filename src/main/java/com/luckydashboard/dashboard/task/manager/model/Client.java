package com.luckydashboard.dashboard.task.manager.model;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Clients")
@Data
public class Client {
    @Id
    private String clientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private List<String> loans = new ArrayList<>();
}
