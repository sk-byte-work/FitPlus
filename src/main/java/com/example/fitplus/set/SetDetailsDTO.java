package com.example.fitplus.set;

import com.example.fitplus.WorkOutStatus;

import java.util.ArrayList;
import java.util.List;

public record SetDetailsDTO(long id, int reps, int weight, WorkOutStatus status) {

    public static List<SetDetailsDTO> transferSetDetails(List<ExerciseSet> sets)
    {
        List<SetDetailsDTO> setDetailsDTOS = new ArrayList<>();
        for(ExerciseSet exerciseSet : sets)
        {
            SetDetailsDTO setDetailsDTO = new SetDetailsDTO(exerciseSet.getSetId(), exerciseSet.getReps(), exerciseSet.getWeight(), exerciseSet.getStatus());
            setDetailsDTOS.add(setDetailsDTO);
        }

        return setDetailsDTOS;
    }
}
