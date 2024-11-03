package com.example.Todo.App.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;
    private String task;
    private boolean completed;

    public Long getTaskId() {
        return task_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private user user;

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }
}
