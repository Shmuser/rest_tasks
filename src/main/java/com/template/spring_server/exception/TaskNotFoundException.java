package com.template.spring_server.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Long id) {
        super("Could not find task with id:" + id);
    }
}