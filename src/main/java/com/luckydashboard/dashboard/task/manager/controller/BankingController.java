package com.luckydashboard.dashboard.task.manager.controller;


import com.luckydashboard.dashboard.task.manager.model.*;
import com.luckydashboard.dashboard.task.manager.service.LoanService;
import com.luckydashboard.dashboard.task.manager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/banking/v1")
public class BankingController {
    final private LoanService loanService;

    BankingController( LoanService loanService){
        this.loanService = loanService;
    }


    @GetMapping("/test")
    public ResponseEntity<String> returnString (){
        String response = "It works";
        return ResponseEntity.accepted().body(response);
    }
    @GetMapping("/admin")
    public ResponseEntity<Admin> getCollectionAmount (){
        Admin response = loanService.getCollectionAmount();
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("/admin/collections/{amount}")
    public ResponseEntity<Admin> updateCollectionAmount (@PathVariable double amount){
        Admin response = loanService.updateCollectionAmount(amount);
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("/admin/collections/reset")
    public ResponseEntity<Admin> resetCollectionAmount (){
        Admin response = loanService.resetCollectionAmount();
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getAllLoans (){
        List<Loan> response = loanService.getAllLoans();
        return ResponseEntity.accepted().body(response);
    }
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClient (){
        List<Client> response = loanService.getAllClients();
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionModal>> getAllTransactions (){
        List<TransactionModal> response = loanService.getAllTransactions();
        return ResponseEntity.accepted().body(response);
    }



    @PostMapping("/clients")
    public ResponseEntity<Client> createNewClient (@RequestBody Client client){
        Client response = loanService.createNewClient(client);
        return ResponseEntity.accepted().body(response);
    }

    @PostMapping("/loans")
    public ResponseEntity<Loan> createNewClient (@RequestBody Loan loans){
        Loan response = loanService.createNewLoan(loans);
        return ResponseEntity.accepted().body(response);
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionModal> createNewClient (@RequestBody TransactionModal transactionModal){
        TransactionModal response = loanService.createNewTransaction(transactionModal);
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/summary/{loanId}")
    public ResponseEntity<DOALoanSummary> getLoanSummary (@PathVariable String loanId){
        DOALoanSummary response = loanService.getLoanSummary(loanId);
        return ResponseEntity.accepted().body(response);
    }

//    @PutMapping("/task/{taskId}/{status}")
//    public ResponseEntity<Task> changeTaskStatus (@PathVariable String taskId, @PathVariable String status){
//        Task response = taskService.updateStatus(taskId,status);
//        return ResponseEntity.accepted().body(response);
//    }
//
//    @PutMapping("/notes/{taskId}/")
//    public ResponseEntity<Task> addNote (@PathVariable String taskId, @RequestBody Note notes){
//        Task response = taskService.updateNotes(taskId,notes);
//        return ResponseEntity.accepted().body(response);
//    }
//
//    @PutMapping("/checklist/{taskId}")
//    public ResponseEntity<Task> addChecklistItem (@PathVariable String taskId, @RequestBody CheckListItem listItem){
//        Task response = taskService.createCustomList(taskId,listItem);
//        return ResponseEntity.accepted().body(response);
//    }
//
//    @PutMapping("/checklist/{taskId}/{index}/{bool}")
//    public ResponseEntity<Task> updateBoolChecklist (@PathVariable String taskId, @PathVariable int index, @PathVariable boolean bool){
//        Task response = taskService.updateChecklistByIndexAndTaskId(taskId,index,bool);
//        return ResponseEntity.accepted().body(response);
//    }
//


//    @GetMapping("/task/{taskId}")
//    public  ResponseEntity<Task> getTaskById (@PathVariable String taskId){
//        Task response = taskService.getTaskById(taskId);
//        return ResponseEntity.accepted().body(response);
//    }
//
//    @PutMapping("/task/update/{taskId}")
//    public  ResponseEntity<Task> updateTaskById (@PathVariable String taskId, @RequestBody Task task){
//        Task response = taskService.updateTaskById(taskId, task);
//        return ResponseEntity.accepted().body(response);
//    }
//
//
//    @PutMapping("/tags/{taskId}/")
//    public ResponseEntity<Task> addTags (@PathVariable String taskId, @RequestBody List<String> tags){
//        Task updatedTask = taskService.updateTags(taskId, tags); // Assuming you have a method like this in TaskService
//        return ResponseEntity.accepted().body(updatedTask);
//    }




}
