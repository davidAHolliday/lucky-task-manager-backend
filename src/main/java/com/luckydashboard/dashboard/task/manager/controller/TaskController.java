package com.luckydashboard.dashboard.task.manager.controller;


import com.luckydashboard.dashboard.task.manager.model.Note;
import com.luckydashboard.dashboard.task.manager.model.Task;
import com.luckydashboard.dashboard.task.manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/task/v1")
public class TaskController {
    final private TaskService taskService;

    TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTask (){
        List<Task> response = taskService.getAllTask();
        return ResponseEntity.accepted().body(response);
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<Task> deleteTaskById (@PathVariable String taskId){
        Task response = taskService.deleteTaskById(taskId);
        return ResponseEntity.accepted().body(response);
    }

    @DeleteMapping("/note/{taskId}/{noteId}")
    public ResponseEntity<Task> deleteNoteById (@PathVariable String taskId, @PathVariable String noteId){
        Task response = taskService.deleteNoteById(taskId, noteId);
        return ResponseEntity.accepted().body(response);
    }


    @PostMapping("/")
    public ResponseEntity<Task> createNewTask (@RequestBody Task task){
        Task response = taskService.createNewTask(task);
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("/task/{taskId}/{status}")
    public ResponseEntity<Task> changeTaskStatus (@PathVariable String taskId, @PathVariable String status){
        Task response = taskService.updateStatus(taskId,status);
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("/notes/{taskId}/")
    public ResponseEntity<Task> addNote (@PathVariable String taskId, @RequestBody Note notes){
        Task response = taskService.updateNotes(taskId,notes);
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/task/{taskId}")
    public  ResponseEntity<Task> getTaskById (@PathVariable String taskId){
        Task response = taskService.getTaskById(taskId);
        return ResponseEntity.accepted().body(response);
    }


    @PutMapping("/tags/{taskId}/")
    public ResponseEntity<Task> addTags (@PathVariable String taskId, @RequestBody List<String> tags){
        Task updatedTask = taskService.updateTags(taskId, tags); // Assuming you have a method like this in TaskService
        return ResponseEntity.accepted().body(updatedTask);
    }




}
