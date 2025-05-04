package com.example.fitplus.exercise;

import jakarta.validation.Valid;

import java.util.List;

public interface ExerciseService {
    void createExercise(ExerciseDTO exerciseRequestDTO) throws Exception;

    List<ExerciseDTO> getAllExercises(Long userID);

    ExerciseDTO getExerciseById(@Valid Long id) throws Exception;

    void updateExercise(Long id, ExerciseDTO exerciseRequestDTO) throws Exception;

    void deleteExercise(@Valid Long id);
}
