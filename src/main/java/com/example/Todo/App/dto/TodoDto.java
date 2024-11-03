package com.example.Todo.App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoDto {

    private Long taskId;
    private String task;
    private boolean completed;
    private Long userId;
}
