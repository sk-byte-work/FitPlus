package com.example.fitplus.set;

public interface SetService
{
    void addNewSetToExercise(long workoutId, long exerciseId, SetRequestDTO setRequestDTO) throws Exception;
}
