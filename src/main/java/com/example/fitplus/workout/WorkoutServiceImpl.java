package com.example.fitplus.workout;

import com.example.fitplus.WorkOutStatus;
import com.example.fitplus.exceptions.FitPlusException;
import com.example.fitplus.users.User;
import com.example.fitplus.users.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService{

    private final WorkoutRepository workoutRepository;
    private final UserService userService;

    public  WorkoutServiceImpl(WorkoutRepository workoutRepository, UserService userService){
        this.workoutRepository = workoutRepository;
        this.userService = userService;
    }

    public WorkoutRepository getWorkoutRepository() {
        return this.workoutRepository;
    }

    @Override
    public void createWorkout(WorkoutRequestDTO workoutRequestDTO) {
        Long userId = workoutRequestDTO.userId();

        // Getting user based on ID from request and validating
        Optional<User> userOptl = this.userService.findByID(userId);
        if(userOptl.isEmpty()){
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
            throw new FitPlusException("Workout Not Found");
        }

        return workoutOptional.get();
    }
    @Override
    public void deleteWorkout(long id) {
        Workout workout = getWorkout(id);
        getWorkoutRepository().delete(workout);
    }

}
