package com.example.fitplus.set;

import jakarta.validation.Valid;

public interface SetService
{
    void addNewSetToExercise(long workoutId, long exerciseId, SetRequestDTO setRequestDTO) throws Exception;

    void updateSetDetails(long workoutId, long exerciseId, long setId, @Valid SetRequestDTO setRequestDTO) throws Exception;

    void deleteSetDetails(long workoutId, long exerciseId, long setId) throws Exception;
}
