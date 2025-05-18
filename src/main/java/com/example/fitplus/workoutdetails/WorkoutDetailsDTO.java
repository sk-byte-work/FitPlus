package com.example.fitplus.workoutdetails;

import com.example.fitplus.set.SetDetailsDTO;

import java.util.List;

public record WorkoutDetailsDTO(long id, String exerciseName, String category, String description, List<SetDetailsDTO> setDetails) {
}
