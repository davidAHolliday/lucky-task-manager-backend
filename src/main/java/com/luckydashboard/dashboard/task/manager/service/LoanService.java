package com.luckydashboard.dashboard.task.manager.service;


import com.luckydashboard.dashboard.task.manager.data.*;
import com.luckydashboard.dashboard.task.manager.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoanService {

    private  final ClientRepository clientRepository;

    private final TransactionModelRepository transactionModelRepository;

    private final AdminRepository   adminRepository;

    private final LoanRepository loanRepository;

    public LoanService(TaskRepository taskRepository, ClientRepository clientRepository, TransactionModelRepository transactionModelRepository, AdminRepository adminRepository, LoanRepository loanRepository){
        this.clientRepository = clientRepository;
        this.transactionModelRepository = transactionModelRepository;
        this.adminRepository = adminRepository;
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
                double totalPayments = loan.getTransactions().stream()
                        .filter(transaction -> "Payment".equals(transaction.getType()))
                        .mapToDouble(TransactionModal::getAmount)
                        .sum();

                //Add interest to original payment for total amount due
                double totalAmountDue = loan.getOriginalAmount() * (1 +loan.getInterestRate());

                //new balance with interest added and skip payments
                double balance = totalAmountDue - totalPayments;
                summary.setBalance(balance);

                double paymentAmount = totalAmountDue/loan.getTerm();
summary.setPaymentAmount(paymentAmount);

                summary.setPaymentsleft(balance/paymentAmount);

                return summary;


            } else {
                throw new IllegalArgumentException("Loan Id with ID " + loanId + " is not found");
            }

        } else {
            throw new IllegalArgumentException("Loan Id with ID " + loanId + " is not found");

        }
    }

    public Admin getCollectionAmount() {
        List<Admin> info = adminRepository.findAll();
        return info.get(0);

    }

    public Admin updateCollectionAmount(double amount) {
        Optional<Admin> recordsOptional = adminRepository.findById("0");
        if(recordsOptional.isEmpty()){
            Admin newAdminObj = new Admin();
            newAdminObj.setId("0");
            newAdminObj.setDailyCollection(amount);
            adminRepository.save(newAdminObj);
        }else{
            Admin record = recordsOptional.get();
            record.setDailyCollection(record.getDailyCollection()+amount);
            return adminRepository.save(record);
        }
return null;

    }

    public Admin resetCollectionAmount() {
        Optional<Admin> record = adminRepository.findById("0");
        if(record.isPresent()){
           Admin rec = record.get();
            rec.setDailyCollection(0);
            return adminRepository.save(rec);
        }
        return null;
    }
}
