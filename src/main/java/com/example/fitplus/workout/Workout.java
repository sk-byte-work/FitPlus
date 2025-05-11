package com.example.fitplus.workout;

import com.example.fitplus.WorkOutStatus;
import com.example.fitplus.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long id;

    @Column( nullable = false)
    @NotNull
    @NotEmpty
    private String workoutName;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private WorkOutStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long createdTime;

    public Workout(){

    }

    public Workout(String workoutName, User createdBy){
        this.workoutName = workoutName;
        this.user = createdBy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public WorkOutStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOutStatus status) {
        this.status = status;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
