package com.example.fitplus.set;

import com.example.fitplus.WorkOutStatus;
import com.example.fitplus.exceptions.FitPlusException;
import com.example.fitplus.workout.WorkoutService;
import com.example.fitplus.workoutdetails.WorkoutDetails;
import com.example.fitplus.workoutdetails.WorkoutDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SetServiceImpl implements SetService
{

    private static final Logger logger = LoggerFactory.getLogger(SetServiceImpl.class);

    private final WorkoutDetailsService workoutDetailsService;
    private final SetRepository setRepository;
    private final WorkoutService workoutService;

    public SetServiceImpl(WorkoutDetailsService workoutDetailsService, SetRepository setRepository, WorkoutService workoutService)
    {
        this.workoutDetailsService = workoutDetailsService;
        this.setRepository = setRepository;
        this.workoutService = workoutService;
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
        ExerciseSet set = ExerciseSet.newSet(workoutDetails, setRequestDTO.weight(), setRequestDTO.reps(), WorkOutStatus.PENDING);

        getSetRepository().save(set);
    }

    @Override
    public void updateSetDetails(long workoutId, long exerciseId, long setId, SetRequestDTO setRequestDTO) throws Exception
    {
        ExerciseSet exerciseSet = validateAndGetExerciseSet(workoutId, exerciseId, setId);
        exerciseSet.setWeight(setRequestDTO.weight());
        exerciseSet.setReps(setRequestDTO.reps());

        getSetRepository().save(exerciseSet);
    }

    private ExerciseSet validateAndGetExerciseSet(long workoutId, long exerciseId, long setId) throws Exception
    {
        WorkoutDetails workoutDetails = getWorkoutDetailsService().getWorkoutDetails(workoutId, exerciseId);

        Optional<ExerciseSet> setOptl = getSetRepository().findByWorkoutDetailsIdAndSetId(workoutDetails.getWorkoutDetailsId(), setId);
        if(setOptl.isEmpty())
        {
            logger.info("Set details not found. WorkoutId: {}, ExerciseId: {}, SetId: {}", workoutId, exerciseId, setId);
            throw new FitPlusException("Set details Not Found");
        }

        return setOptl.get();
    }

    @Override
    public void deleteSetDetails(long workoutId, long exerciseId, long setId) throws Exception 
    {
        ExerciseSet exerciseSet = validateAndGetExerciseSet(workoutId, exerciseId, setId);
        getSetRepository().delete(exerciseSet);
    }

    @Override
    public void markSetAsCompleted(long workoutId, long exerciseId, long setId) throws Exception {
        validateWorkoutStatus(workoutId);

        ExerciseSet set = validateAndGetExerciseSet(workoutId, exerciseId, setId);
        if(set.getStatus() == WorkOutStatus.COMPLETED)
        {
            logger.info("Set is already marked as completed. WorkoutId: {}, ExerciseId: {}, SetId: {}", workoutId, exerciseId, setId);
            throw new FitPlusException("Set is already marked as completed. Hence cannot mark as completed");
        }

        changeExerciseSetStatus(set, WorkOutStatus.COMPLETED);
    }

    private void changeExerciseSetStatus(ExerciseSet set, WorkOutStatus status) {
        set.setStatus(status);
        getSetRepository().save(set);
    }

    private void validateWorkoutStatus(long workoutId)
    {
        boolean isWorkoutCompleted = getWorkoutService().isWorkoutCompleted(workoutId);
        if(isWorkoutCompleted)
        {
            logger.info("Workout is already completed. WorkoutId: {}", workoutId);
            throw new FitPlusException("Workout is Completed. Hence cannot mark Set as completed");
        }
    }

    @Override
    public void markSetAsPending(long workoutId, long exerciseId, long setId) throws Exception {
        validateWorkoutStatus(workoutId);

        ExerciseSet set = validateAndGetExerciseSet(workoutId, exerciseId, setId);
        if(set.getStatus() == WorkOutStatus.PENDING)
        {
            logger.info("Set is already in Pending status. WorkoutId: {}, ExerciseId: {}, SetId: {}", workoutId, exerciseId, setId);
            throw new FitPlusException("Set is already in Pending state. Hence cannot mark as pending");
        }

        changeExerciseSetStatus(set, WorkOutStatus.PENDING);
    }

    public WorkoutService getWorkoutService() {
        return workoutService;
    }
}
