package com.example.fitplus.set;

import com.example.fitplus.exceptions.FitPlusException;
import com.example.fitplus.workoutdetails.WorkoutDetails;
import com.example.fitplus.workoutdetails.WorkoutDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public void updateSetDetails(long workoutId, long exerciseId, long setId, SetRequestDTO setRequestDTO) throws Exception
    {
        ExerciseSet exerciseSet = validateAndExerciseSet(workoutId, exerciseId, setId);
        exerciseSet.setWeight(setRequestDTO.weight());
        exerciseSet.setReps(setRequestDTO.reps());

        getSetRepository().save(exerciseSet);
    }

    private ExerciseSet validateAndExerciseSet(long workoutId, long exerciseId, long setId) throws Exception
    {
        WorkoutDetails workoutDetails = getWorkoutDetailsService().getWorkoutDetails(workoutId, exerciseId);

        Optional<ExerciseSet> setOptl = getSetRepository().findByWorkoutDetailsIdAndSetId(workoutDetails.getWorkoutDetailsId(), setId);
        if(setOptl.isEmpty())
        {
            throw new FitPlusException("Set details Not Found");
        }

        return setOptl.get();
    }

    @Override
    public void deleteSetDetails(long workoutId, long exerciseId, long setId) throws Exception 
    {
        ExerciseSet exerciseSet = validateAndExerciseSet(workoutId, exerciseId, setId);
        getSetRepository().delete(exerciseSet);
    }
}
