package com.luckydashboard.dashboard.task.manager.service;


import com.luckydashboard.dashboard.task.manager.data.TaskRepository;
import com.luckydashboard.dashboard.task.manager.model.CheckListItem;
import com.luckydashboard.dashboard.task.manager.model.Note;
import com.luckydashboard.dashboard.task.manager.model.Photo;
import com.luckydashboard.dashboard.task.manager.model.Task;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    public Task createCustomList(String taskId, CheckListItem listItemPayload) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();

            ArrayList<CheckListItem> listItem = task.getCheckListItems();
            if(listItem == null){
                listItem = new ArrayList<>();
            }
            CheckListItem item = new CheckListItem();
            item.setItemText(listItemPayload.getItemText());
            item.setIndex(listItemPayload.getIndex());
            item.setDoneStatus(false);
           listItem.add(item);
           task.setCheckListItems(listItem);

            return taskRepository.save(task);

        }
        else{
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");

        }


    }

    public Task updateChecklistByIndexAndTaskId(String taskId, int index, boolean bool) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            List<CheckListItem> checkListItems = task.getCheckListItems();
            // Check if index is within the bounds of the list
            if (index >= 1 && index < checkListItems.size()+1) {
                // Get the CheckListItem at the specified index
                Optional<CheckListItem> optionalItem = checkListItems.stream()
                        .filter(x -> x.getIndex() == index)
                        .findFirst();
                if (optionalItem.isPresent()) {
                    CheckListItem itemToUpdate = optionalItem.get();
                    itemToUpdate.setDoneStatus(bool);
                    return taskRepository.save(task);
                } else {
                    throw new IllegalArgumentException("CheckListItem with index " + index + " not found in Task with ID " + taskId);
                }
            } else {
                throw new IllegalArgumentException("Invalid index: " + index);
            }
        } else {
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

    public Task deleteTaskById(String taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            taskRepository.deleteById(taskId);
            return task;
        } else {
            throw new NoSuchElementException("Task not found with ID: " + taskId);
        }
    }

    public Task deleteNoteById(String taskId, String noteId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            List<Note> notes = task.getNotes(); // Assuming getNotes() returns a List<Note>

            // Remove the Note with the given noteId from the list
            notes.removeIf(note -> note.getNoteId().equals(noteId)); // Assuming Note has getId() method

            // Save the updated task with the removed note (if needed)
            taskRepository.save(task);

            return task;
        } else {
            throw new NoSuchElementException("Task not found with ID: " + taskId);
        }
    }

    public Task deleteCheckListByTaskIdAndIndex(String taskId, int index) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            List<CheckListItem> listItems = task.getCheckListItems(); // Assuming getNotes() returns a List<Note>

            // Remove the Note with the given noteId from the list
            listItems.removeIf(item -> item.getIndex() == index); // Assuming Note has getId() method

            // Save the updated task with the removed note (if needed)
            taskRepository.save(task);

            return task;
        } else {
            throw new NoSuchElementException("Task not found with ID: " + taskId);
        }
    }
    public Task updateTaskById(String taskId, Task taskPayload) {
        // Fetch the task by ID from the database
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()) {
            // Get the existing task object from the optional wrapper
            Task existingTask = optionalTask.get();

            // Update the task description and other fields with the values from the payload
           existingTask.setTaskName(taskPayload.getTaskName());
            existingTask.setTaskDescription(taskPayload.getTaskDescription());
            existingTask.setDueDate(taskPayload.getDueDate());
            existingTask.setAssignedTo(taskPayload.getAssignedTo());
            existingTask.setTags(taskPayload.getTags());
//            existingTask.setListItems(taskPayload.getListItems());

            // Save the updated task object to the database
            Task updatedTask = taskRepository.save(existingTask);

            // Return the updated task object
            return updatedTask;
        } else {
            // If the task is not found, throw a NoSuchElementException
            throw new NoSuchElementException("Task not found with ID: " + taskId);
        }
    }



    public String addPhoto(String taskId, int index, String title, MultipartFile file) throws IOException {
        Optional<Task> currentTaskOpt = taskRepository.findById(taskId);
        if (currentTaskOpt.isPresent()) {
            Task currentTask = currentTaskOpt.get();
            List<CheckListItem> checkListItems = currentTask.getCheckListItems();
            if (index >= 0 && index < checkListItems.size()) {
                CheckListItem currentCheckList = checkListItems.get(index);

                Photo photo = new Photo(title);
                photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

                currentCheckList.setPhoto(photo);
                taskRepository.save(currentTask);
                return "Photo added successfully";
            } else {
                return "Invalid index provided";
            }
        } else {
            return "Task not found";
        }
    }

    public Photo getPhoto(String id,int index) {
        Optional<Task> cTask = taskRepository.findById(id);
        if (cTask.isPresent()) {
            Task currentTask = cTask.get();
            CheckListItem currentItem = currentTask.getCheckListItems().get(index);
            return currentItem.getPhoto();
        }
        return null;
    }


}
