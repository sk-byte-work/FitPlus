package com.example.fitplus.set;

import com.example.fitplus.UserScopedEntity;
import com.example.fitplus.WorkOutStatus;
import com.example.fitplus.workoutdetails.WorkoutDetails;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

@Entity
public class ExerciseSet extends UserScopedEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Long setId;

    @ManyToOne
    @JoinColumn(name = "workout_details_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WorkoutDetails workoutDetails;

    private Integer reps;

    private Integer weight;

    private WorkOutStatus status;

    public ExerciseSet(){

    }

    public ExerciseSet(WorkoutDetails workoutDetails, Integer reps, Integer weight, WorkOutStatus status){
        this.workoutDetails = workoutDetails;
        this.reps = reps;
        this.weight = weight;
        this.status = status;
    }

    public WorkoutDetails getWorkoutDetails() {
        return workoutDetails;
    }

    public void setWorkoutDetails(WorkoutDetails workoutDetails) {
        this.workoutDetails = workoutDetails;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public static ExerciseSet newSet(WorkoutDetails workoutDetails, Integer weight, Integer reps, WorkOutStatus status)
    {
        return new ExerciseSet(workoutDetails, reps, weight, status);
    }

    public WorkOutStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOutStatus status) {
        this.status = status;
    }

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }
}
