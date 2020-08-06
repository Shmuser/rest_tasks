package com.template.spring_server.rest;

import com.template.spring_server.exception.TaskNotFoundException;
import com.template.spring_server.model.task.Task;
import com.template.spring_server.model.task.TaskData;
import com.template.spring_server.model.task.TaskRepository;
import com.template.spring_server.model.user.User;
import com.template.spring_server.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {

    @GetMapping("/ping")
    public @ResponseBody
    ResponseEntity<String> ping() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Task> tasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/my")
    public List<Task> myTasks(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
        return taskRepository.findByCreatorId(currentUser.getId());
    }


    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) throws TaskNotFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @PostMapping
    public Task createTask(@RequestBody Task newTask, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
        newTask.setCreatorId(currentUser.getId());
        System.out.println(newTask.toString());
        return taskRepository.save(newTask);
    }

    @PutMapping("/{id}")
    public Task updateTask(@RequestBody TaskData newTaskData, @PathVariable Long id) throws TaskNotFoundException {
        Task oldTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        oldTask.setText(newTaskData.getText());
        oldTask.setTitle(newTaskData.getTitle());
        return taskRepository.save(oldTask);
    }


    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
