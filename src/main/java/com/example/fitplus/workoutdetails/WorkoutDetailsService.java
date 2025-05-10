package com.example.fitplus.workoutdetails;

public interface WorkoutDetailsService {
    void associateExerciseToWorkout(long workoutId, long exerciseId) throws Exception;

    void disAssociateExerciseFromWorkout(long workoutId, long exerciseId) throws Exception;

    WorkoutDetails getWorkoutDetails(long workoutId, long exerciseId) throws Exception;
}
