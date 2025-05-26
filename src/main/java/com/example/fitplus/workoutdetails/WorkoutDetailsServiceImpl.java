package com.example.fitplus.workoutdetails;

import com.example.fitplus.exceptions.FitPlusException;
import com.example.fitplus.exercise.Exercise;
import com.example.fitplus.exercise.ExerciseService;
import com.example.fitplus.workout.Workout;
import com.example.fitplus.workout.WorkoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkoutDetailsServiceImpl implements WorkoutDetailsService{

    private static final Logger logger = LoggerFactory.getLogger(WorkoutDetailsServiceImpl.class);

    private final WorkoutDetailsRepository workoutDetailsRepository;
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;

    public WorkoutDetailsServiceImpl(WorkoutDetailsRepository workoutDetailsRepository, WorkoutService workoutService, ExerciseService exerciseService)
    {
        this.workoutDetailsRepository = workoutDetailsRepository;
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
    }

    private WorkoutDetailsRepository getWorkoutDetailsRepository()
    {
        return this.workoutDetailsRepository;
    }

    private WorkoutService getWorkoutService()
    {
        return this.workoutService;
    }

    private ExerciseService getExerciseService()
    {
        return this.exerciseService;
    }

    @Override
    public void associateExerciseToWorkout(long workoutId, long exerciseId) throws Exception
    {
        validateExerciseAssociation(workoutId, exerciseId);

        Workout workout = getWorkoutService().getWorkout(workoutId);
        Exercise exercise = getExerciseService().getExercise(exerciseId);
        WorkoutDetails workoutDetails = WorkoutDetails.newWorkoutDetails(workout, exercise);

        getWorkoutDetailsRepository().save(workoutDetails);
    }

    private void validateExerciseAssociation(long workoutId, long exerciseId) {
        boolean isExerciseAlreadyAssociated = isExerciseAssociated(workoutId, exerciseId);
        if(isExerciseAlreadyAssociated)
        {
            logger.info("The Exercise already associated to the workout. WorkoutId: {}, ExerciseId: {}", workoutId, exerciseId);
            throw new FitPlusException("This Exercise already associated to the workout.");
        }

        validateAgainstWorkoutStatus(workoutId);
    }

    private void validateAgainstWorkoutStatus(long workoutId) {
        boolean isWorkoutCompleted = getWorkoutService().isWorkoutCompleted(workoutId);
        if(isWorkoutCompleted)
        {
            logger.info("Workout is already marked as completed. WorkoutId: {}", workoutId);
            throw new FitPlusException("Workout is already marked as completed. Hence cannot do this operation");
        }
    }

    private boolean isExerciseAssociated(long workoutId, long exerciseId) {
        return getWorkoutDetailsRepository().existsByWorkoutIdAndExerciseId(workoutId, exerciseId);
    }

    @Override
    public void disAssociateExerciseFromWorkout(long workoutId, long exerciseId) throws Exception {
        boolean isExerciseAssociated = isExerciseAssociated(workoutId, exerciseId);
        if(!isExerciseAssociated)
        {
            logger.info("Exercise is not associated with Workout. WorkoutId: {}, ExerciseId: {}", workoutId, exerciseId);
            throw new FitPlusException("Exercise is not associated with Workout. Hence cannot delete Exercise from workout");
        }

        validateAgainstWorkoutStatus(workoutId);

        WorkoutDetails workoutDetails = getWorkoutDetails(workoutId, exerciseId);
        getWorkoutDetailsRepository().delete(workoutDetails);
    }

    @Override
    public WorkoutDetails getWorkoutDetails(long workoutId, long exerciseId) throws Exception {
        Optional<WorkoutDetails> workoutDetailsOptl = getWorkoutDetailsRepository().findByWorkoutIdAndExerciseId(workoutId, exerciseId);
        if(workoutDetailsOptl.isEmpty())
        {
            logger.info("Workout details are not found. WorkoutId: {}, ExerciseId: {}", workoutId, exerciseId);
            throw new FitPlusException("Workout details are not found");
        }

        return workoutDetailsOptl.get();
    }
}
