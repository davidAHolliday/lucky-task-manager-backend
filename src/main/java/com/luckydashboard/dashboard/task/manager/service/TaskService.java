package com.luckydashboard.dashboard.task.manager.service;


import com.luckydashboard.dashboard.task.manager.data.TaskRepository;
import com.luckydashboard.dashboard.task.manager.model.Note;
import com.luckydashboard.dashboard.task.manager.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private  final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTask(){
        return taskRepository.findAll();

    }


    public Task createNewTask(Task task) {
        task.setTimeCreated(new Date()); // Set current date and time
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateStatus(String taskId, String status) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(status);

            if ("closed".equals(status)) {
                task.setTimeClosed(new Date()); // Assuming Task has a setTimeClosed method
            }

            if("open".equals(status)){
                task.setTimeClosed(null);
            }
            return taskRepository.save(task);
        } else {
            // Handle the case where the task with the given ID does not exist
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
    }

    public Task updateNotes(String taskId, Note notes) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            List<Note> notesList = task.getNotes();
            Note newNote = new Note();
            newNote.setNoteText(notes.getNoteText());
            newNote.setCreatedBy(notes.getCreatedBy());
            newNote.setTimeCreated(new Date());
            notesList.add(newNote);
            task.setNotes(notesList);
            return taskRepository.save(task);

        }
        else{
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");

        }


    }

    public Task updateTags(String taskId, List<String> tags) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTags(tags); // Assuming you have a setter method in the Task class for tags
            return taskRepository.save(task);
        } else {
            // Handle the case where the task with the given ID does not exist
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
    }

    public Task getTaskById(String taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if( optionalTask.isPresent()){
            Task task = optionalTask.get();
            return task;
        }else{
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");

        }
    }
}
