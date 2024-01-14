package com.luckydashboard.dashboard.task.manager.service;


import com.luckydashboard.dashboard.task.manager.data.ClientRepository;
import com.luckydashboard.dashboard.task.manager.data.LoanRepository;
import com.luckydashboard.dashboard.task.manager.data.TaskRepository;
import com.luckydashboard.dashboard.task.manager.data.TransactionModelRepository;
import com.luckydashboard.dashboard.task.manager.model.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LoanService {

    private  final ClientRepository clientRepository;

    private final TransactionModelRepository transactionModelRepository;

    private final LoanRepository loanRepository;

    public LoanService(TaskRepository taskRepository, ClientRepository clientRepository, TransactionModelRepository transactionModelRepository, LoanRepository loanRepository){
        this.clientRepository = clientRepository;
        this.transactionModelRepository = transactionModelRepository;
        this.loanRepository = loanRepository;
    }


    //Create Transactions
    public List<TransactionModal> getAllTransactions(){
        return transactionModelRepository.findAll();

    }
    public TransactionModal createNewTransaction(TransactionModal transaction) {
        Optional<Loan> optionalLoan = loanRepository.findById(transaction.getLoanId());
        if(optionalLoan.isPresent()){
            Loan loan = optionalLoan.get();
            List<TransactionModal> transactions = loan.getTransactions();
            transactions.add(transaction);
            loan.setTransactions(transactions);
            loanRepository.save(loan);
        }else {
            throw new IllegalArgumentException("Loan with ID " + transaction.getLoanId() + " not found");
        }

        transaction.setCreateDate(new Date()); // Set current date and time
        return transactionModelRepository.save(transaction);
    }

    //Create Clients
    public List<Client> getAllClients(){
        return clientRepository.findAll();

    }
    public Client createNewClient(Client client) {
        return clientRepository.save(client);
    }

    //Create Loans
    public List<Loan> getAllLoans(){
        return loanRepository.findAll();

    }
    public Loan createNewLoan(Loan loan) {
Optional<Client> clientOptional = clientRepository.findById(loan.getClientId());

if(clientOptional.isPresent()){
    Client client = clientOptional.get();
    List<String> loanList = client.getLoans();
    Loan newLoan = loanRepository.save(loan);
    loanList.add(newLoan.getLoanId());
    client.setLoans(loanList);
    clientRepository.save(client);

    return loan;
}
else{
    throw new IllegalArgumentException("Loan with ID " + loan.getClientId() + " not found");

}

    }


    public DOALoanSummary getLoanSummary(String loanId) {
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            String clientId = loan.getClientId();
            Optional<Client> optionalClientObj = clientRepository.findById(clientId);
            if (optionalClientObj.isPresent()) {
                Client clientObj = optionalClientObj.get();
                DOALoanSummary summary = new DOALoanSummary();
                summary.setClient(clientObj);
                summary.setLoan(loan);
                double paymentAmount = loan.getTransactions().stream()
                        .mapToDouble(TransactionModal::getAmount)
                        .sum();

                double balance = loan.getOriginalAmount() - paymentAmount;
                summary.setBalance(balance);

                return summary;


            } else {
                throw new IllegalArgumentException("Loan Id with ID " + loanId + " is not found");
            }

        } else {
            throw new IllegalArgumentException("Loan Id with ID " + loanId + " is not found");

        }
    }
}
