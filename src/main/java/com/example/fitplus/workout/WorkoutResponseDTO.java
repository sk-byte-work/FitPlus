package com.example.fitplus.workout;

import com.example.fitplus.WorkOutStatus;
import com.example.fitplus.workoutdetails.WorkoutDetailsDTO;

import java.util.ArrayList;
import java.util.List;

public record WorkoutResponseDTO(long id, String workoutName, WorkOutStatus workoutStatus, List<WorkoutDetailsDTO> exercises)
{
    public static List<WorkoutResponseDTO> transferWorkouts(List<Workout> workouts)
    {
        List<WorkoutResponseDTO> workoutResponseDTOS = new ArrayList<>();
        for (Workout workout : workouts)
        {
            WorkoutResponseDTO responseDTO = new WorkoutResponseDTO(workout.getId(), workout.getWorkoutName(), workout.getStatus(), null);
            workoutResponseDTOS.add(responseDTO);
        }

        return workoutResponseDTOS;
    }
}
