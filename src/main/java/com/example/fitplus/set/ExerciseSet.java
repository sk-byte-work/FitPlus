package com.example.fitplus.set;

import com.example.fitplus.workoutdetails.WorkoutDetails;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class ExerciseSet {

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

    public ExerciseSet(){

    }

    public ExerciseSet(WorkoutDetails workoutDetails, Integer reps, Integer weight){
        this.workoutDetails = workoutDetails;
        this.reps = reps;
        this.weight = weight;
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

    public static ExerciseSet newSet(WorkoutDetails workoutDetails, Integer weight, Integer reps)
    {
        return new ExerciseSet(workoutDetails, reps, weight);
    }
}
