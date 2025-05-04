package com.example.fitplus.exercise;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public record ExerciseDTO(Long exerciseID, Long userID, @NotEmpty @NotNull String exerciseName, @NotNull @NotEmpty String category, String description) {

    public static List<ExerciseDTO> transferExercises(List<Exercise> exercises){
        List<ExerciseDTO> exerciseDTOS = new ArrayList<>();
        for(Exercise exercise : exercises){
            ExerciseDTO exerciseDTO = transferExercise(exercise);
            exerciseDTOS.add(exerciseDTO);
        }

        return exerciseDTOS;
    }

    public static ExerciseDTO transferExercise(Exercise exercise) {
        return new ExerciseDTO(
                exercise.getId(),
                exercise.getUser().getId(),
                exercise.getExerciseName(),
                exercise.getCategory(),
                exercise.getDescription());
    }
}
