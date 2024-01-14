package com.luckydashboard.dashboard.task.manager.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "Transactions")
public class TransactionModal {
    @Id
    private String transactionId;
    private String loanId;
    private Date createDate = new Date();
    private String type;
    private double amount;
    private String note;
}
