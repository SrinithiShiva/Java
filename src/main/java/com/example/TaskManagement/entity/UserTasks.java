package com.example.TaskManagement.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class UserTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTaskId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Tasks tasks;
    @OneToMany(mappedBy = "userTask",cascade = jakarta.persistence.CascadeType.ALL)
    private List<Feedback> feedbacks;
    @OneToMany(mappedBy = "userTask", cascade = jakarta.persistence.CascadeType.ALL)
    private List<TaskAttachment> attachments;

    public Long getUserTaskId() {
        return userTaskId;
    }

    public void setUserTaskId(Long userTaskId) {
        this.userTaskId = userTaskId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<TaskAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TaskAttachment> attachments) {
        this.attachments = attachments;
    }
}
