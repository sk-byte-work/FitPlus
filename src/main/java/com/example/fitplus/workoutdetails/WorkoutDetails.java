package com.example.fitplus.workoutdetails;

import com.example.fitplus.UserScopedEntity;
import com.example.fitplus.exercise.Exercise;
import com.example.fitplus.workout.Workout;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

@Entity
public class WorkoutDetails extends UserScopedEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_details_id")
    private Long workoutDetailsId;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Workout workout;

    @OneToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    public WorkoutDetails(){

    }

    public WorkoutDetails(Workout workout, Exercise exercise){
        this.workout = workout;
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public static WorkoutDetails newWorkoutDetails(Workout workout, Exercise exercise)
    {
        return new WorkoutDetails(workout, exercise);
    }

    public Long getWorkoutDetailsId() {
        return workoutDetailsId;
    }

    public void setWorkoutDetailsId(Long workoutDetailsId) {
        this.workoutDetailsId = workoutDetailsId;
    }

}
