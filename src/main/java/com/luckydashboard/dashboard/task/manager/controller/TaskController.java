package com.luckydashboard.dashboard.task.manager.controller;


import com.luckydashboard.dashboard.task.manager.model.CheckListItem;
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

    @DeleteMapping("/checklist/{taskId}/{index}")
    public ResponseEntity<Task> deleteChecklistItemById (@PathVariable String taskId, @PathVariable int index){
        Task response = taskService.deleteCheckListByTaskIdAndIndex(taskId, index);
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

    @PutMapping("/checklist/{taskId}")
    public ResponseEntity<Task> addChecklistItem (@PathVariable String taskId, @RequestBody CheckListItem listItem){
        Task response = taskService.createCustomList(taskId,listItem);
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("/checklist/{taskId}/{index}/{bool}")
    public ResponseEntity<Task> updateBoolChecklist (@PathVariable String taskId, @PathVariable int index, @PathVariable boolean bool){
        Task response = taskService.updateChecklistByIndexAndTaskId(taskId,index,bool);
        return ResponseEntity.accepted().body(response);
    }



    @GetMapping("/task/{taskId}")
    public  ResponseEntity<Task> getTaskById (@PathVariable String taskId){
        Task response = taskService.getTaskById(taskId);
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("/task/update/{taskId}")
    public  ResponseEntity<Task> updateTaskById (@PathVariable String taskId, @RequestBody Task task){
        Task response = taskService.updateTaskById(taskId, task);
        return ResponseEntity.accepted().body(response);
    }


    @PutMapping("/tags/{taskId}/")
    public ResponseEntity<Task> addTags (@PathVariable String taskId, @RequestBody List<String> tags){
        Task updatedTask = taskService.updateTags(taskId, tags); // Assuming you have a method like this in TaskService
        return ResponseEntity.accepted().body(updatedTask);
    }




}
