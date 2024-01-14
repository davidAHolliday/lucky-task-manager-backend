package com.luckydashboard.dashboard.task.manager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "Loans")
public class Loan {
    @Id
    private String loanId;
    private String clientId;
    private Date dateCreated = new Date();
    private boolean active = true;
    private double originalAmount;
    private double interestRate;
    private int term;
    private List<TransactionModal> transactions = new ArrayList<>();

    public void addTransaction(TransactionModal transaction){
        transactions.add(transaction);
    }
}

