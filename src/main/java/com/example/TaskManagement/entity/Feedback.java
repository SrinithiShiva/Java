package com.example.TaskManagement.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;
    @ManyToOne
    @JoinColumn(name = "user_task_id")
    private UserTasks userTask;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Tasks task;
    private String commentText;
    @ManyToOne
    @JoinColumn(name = "posted_user_id")
    private Users postedUser;
    private Date postTime;

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public UserTasks getUserTask() {
        return userTask;
    }

    public void setUserTask(UserTasks userTask) {
        this.userTask = userTask;
    }

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Users getPostedUser() {
        return postedUser;
    }

    public void setPostedUser(Users postedUser) {
        this.postedUser = postedUser;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}
