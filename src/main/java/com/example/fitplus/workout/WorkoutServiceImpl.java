package com.example.fitplus.workout;

import com.example.fitplus.WorkOutStatus;
import com.example.fitplus.exceptions.FitPlusException;
import com.example.fitplus.exercise.Exercise;
import com.example.fitplus.security.AuthUtil;
import com.example.fitplus.set.ExerciseSet;
import com.example.fitplus.set.SetDetailsDTO;
import com.example.fitplus.set.SetRepository;
import com.example.fitplus.users.User;
import com.example.fitplus.users.UserService;
import com.example.fitplus.workoutdetails.WorkoutDetails;
import com.example.fitplus.workoutdetails.WorkoutDetailsDTO;
import com.example.fitplus.workoutdetails.WorkoutDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService{

    private static final Logger logger = LoggerFactory.getLogger(WorkoutServiceImpl.class);

    private final WorkoutRepository workoutRepository;
    private final UserService userService;
    private final SetRepository setRepository;
    private final WorkoutDetailsRepository workoutDetailsRepository;
    private final AuthUtil authUtil;

    public  WorkoutServiceImpl(WorkoutRepository workoutRepository, UserService userService, SetRepository setRepository, WorkoutDetailsRepository workoutDetailsRepository, AuthUtil authUtil){
        this.workoutRepository = workoutRepository;
        this.userService = userService;
        this.setRepository = setRepository;
        this.workoutDetailsRepository = workoutDetailsRepository;
        this.authUtil = authUtil;
    }

    public WorkoutRepository getWorkoutRepository() {
        return this.workoutRepository;
    }

    @Override
    public void createWorkout(WorkoutRequestDTO workoutRequestDTO) {
        Long userId = getAuthUtil().getUserId();

        // Getting user based on ID from request and validating
        Optional<User> userOptl = this.userService.findByID(userId);
        if(userOptl.isEmpty()){
            logger.info("User not found. UserId: {}", userId);
            throw new FitPlusException("User Not Found");
        }

        // Creating workout
        Workout workout = new Workout(workoutRequestDTO.workoutName(), userOptl.get());
        workout.setStatus(WorkOutStatus.PENDING);
        workout.setCreatedTime(Instant.now().toEpochMilli());

        this.workoutRepository.save(workout);
    }

    @Override
    public void updateWorkout(Long id, String workoutName) {
        Workout workout = getWorkout(id);
        workout.setWorkoutName(workoutName);
        getWorkoutRepository().save(workout);
    }

    @Override
    public Workout getWorkout(Long id) {
        Optional<Workout> workoutOptional = getWorkoutRepository().findById(id);
        if(workoutOptional.isEmpty()){
            logger.info("Workout not found. WorkoutId: {}", id);
            throw new FitPlusException("Workout Not Found");
        }

        return workoutOptional.get();
    }

    @Override
    public void deleteWorkout(long id) {
        Workout workout = getWorkout(id);
        getWorkoutRepository().delete(workout);
    }

    @Override
    public void markWorkoutAsCompleted(long workoutId)
    {
        Workout workout = getWorkout(workoutId);
        if(workout.getStatus() == WorkOutStatus.COMPLETED)
        {
            logger.info("Workout already marked as completed. WorkoutId: {}", workoutId);
            throw new FitPlusException("Workout already marked as completed");
        }

        changeWorkoutStatus(workout, WorkOutStatus.COMPLETED);
    }

    private void changeWorkoutStatus(Workout workout, WorkOutStatus status) {
        workout.setStatus(status);
        getWorkoutRepository().save(workout);
    }

    @Override
    public boolean isWorkoutCompleted(long workoutId) {
        return getWorkoutRepository().existsByStatus(WorkOutStatus.COMPLETED);
    }

    @Override
    public void markWorkoutAsPending(long workoutId) {
        Workout workout = getWorkout(workoutId);
        if(workout.getStatus() == WorkOutStatus.PENDING)
        {
            logger.info("Workout is already in pending status. WorkoutId: {}", workoutId);
            throw new FitPlusException("Workout is already in pending status. Cannot change status");
        }

        changeWorkoutStatus(workout, WorkOutStatus.PENDING);
    }

    @Override
    public List<WorkoutResponseDTO> getAllWorkouts() {
        Long userId = getAuthUtil().getUserId();;
        List<Workout> workouts = getWorkoutRepository().findByUserId(userId);
        return WorkoutResponseDTO.transferWorkouts(workouts);
    }

    @Override
    public WorkoutResponseDTO getWorkoutDetails(long workoutId) {
        Workout workout = getWorkout(workoutId);
        if(workout == null)
        {
            logger.info("Workout not found. WorkoutId: {}", workoutId);
            throw new FitPlusException("Workout Not Found");
        }

        List<WorkoutDetailsDTO> workoutDetailsDTOS = new ArrayList<>();

        List<WorkoutDetails> workoutDetails = getWorkoutDetailsRepository().findAllByWorkoutId(workoutId);
        for(WorkoutDetails wkDetails : workoutDetails)
        {
            long workoutDetailsId = wkDetails.getWorkoutDetailsId();
            Exercise exercise = wkDetails.getExercise();
            List<SetDetailsDTO> setDetailsDTOS = getSetDetailsDTO(workoutDetailsId);
            WorkoutDetailsDTO workoutDetailsDTO = new WorkoutDetailsDTO(exercise.getId(), exercise.getExerciseName(), exercise.getCategory(), exercise.getDescription(), setDetailsDTOS);
            workoutDetailsDTOS.add(workoutDetailsDTO);
        }

        WorkoutResponseDTO workoutResponseDTO = new WorkoutResponseDTO(workoutId, workout.getWorkoutName(), workout.getStatus(), workoutDetailsDTOS);
        return workoutResponseDTO;
    }

    private List<SetDetailsDTO> getSetDetailsDTO(long workoutDetailsId) {
        List<SetDetailsDTO> setDetailsDTOS = new ArrayList<>();
        List<ExerciseSet> sets = getSetRepository().findAllByWorkoutDetailsWorkoutDetailsId(workoutDetailsId);
        for (ExerciseSet exerciseSet : sets)
        {
            SetDetailsDTO setDetailsDTO = new SetDetailsDTO(exerciseSet.getSetId(), exerciseSet.getReps(), exerciseSet.getWeight(), exerciseSet.getStatus());
            setDetailsDTOS.add(setDetailsDTO);
        }
        return setDetailsDTOS;
    }

    public SetRepository getSetRepository() {
        return setRepository;
    }

    public WorkoutDetailsRepository getWorkoutDetailsRepository() {
        return workoutDetailsRepository;
    }

    public AuthUtil getAuthUtil()
    {
        return authUtil;
    }
}
