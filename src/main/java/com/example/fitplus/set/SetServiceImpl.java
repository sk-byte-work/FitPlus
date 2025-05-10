package com.example.fitplus.set;

import com.example.fitplus.workoutdetails.WorkoutDetails;
import com.example.fitplus.workoutdetails.WorkoutDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SetServiceImpl implements SetService{

    private final WorkoutDetailsService workoutDetailsService;
    private final SetRepository setRepository;

    public SetServiceImpl(WorkoutDetailsService workoutDetailsService, SetRepository setRepository)
    {
        this.workoutDetailsService = workoutDetailsService;
        this.setRepository = setRepository;
    }

    private WorkoutDetailsService getWorkoutDetailsService()
    {
        return this.workoutDetailsService;
    }

    private SetRepository getSetRepository()
    {
        return this.setRepository;
    }

    @Override
    public void addNewSetToExercise(long workoutId, long exerciseId, SetRequestDTO setRequestDTO) throws Exception
    {
        WorkoutDetails workoutDetails = getWorkoutDetailsService().getWorkoutDetails(workoutId, exerciseId);
        ExerciseSet set = ExerciseSet.newSet(workoutDetails, setRequestDTO.weight(), setRequestDTO.reps());

        getSetRepository().save(set);
    }
}
