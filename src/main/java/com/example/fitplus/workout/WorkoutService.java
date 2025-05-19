package com.example.fitplus.workout;

import java.util.List;

public interface WorkoutService {
    void createWorkout(WorkoutRequestDTO workoutRequestDTO);

    void updateWorkout(Long id, String workoutName);

    Workout getWorkout(Long id);

    void deleteWorkout(long id);

    void markWorkoutAsCompleted(long workoutId);

    boolean isWorkoutCompleted(long workoutId);

    void markWorkoutAsPending(long workoutId);

    List<WorkoutResponseDTO> getAllWorkouts();

    WorkoutResponseDTO getWorkoutDetails(long workoutId);
}

