package com.example.fitplus.exercise;

import com.example.fitplus.users.User;
import com.example.fitplus.users.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final UserService userService;
    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(UserService userService, ExerciseRepository exerciseRepository){
        this.userService = userService;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void createExercise(ExerciseDTO exerciseRequestDTO) throws Exception {

        Long userID = exerciseRequestDTO.userID();
        User user = validateAndGetUser(userID);
        Exercise exercise = Exercise.createExercise(user, exerciseRequestDTO);
        this.exerciseRepository.save(exercise);
    }

    @Override
    public void updateExercise(Long exerciseId, ExerciseDTO exerciseRequestDTO) throws Exception {
        Optional<Exercise> existingExerciseOptl = exerciseRepository.findById(exerciseId);
        if(existingExerciseOptl.isEmpty()){
            throw new RuntimeException("Resource Not Found");
        }

        Exercise existingExercise = existingExerciseOptl.get();
        validateUserAssociativity(exerciseRequestDTO, existingExercise);

        if(exerciseRequestDTO.exerciseName() != null){
            existingExercise.setExerciseName(exerciseRequestDTO.exerciseName());
        }
        if(exerciseRequestDTO.category() != null){
            existingExercise.setCategory(exerciseRequestDTO.category());
        }
        if(exerciseRequestDTO.description() != null){
            existingExercise.setDescription(exerciseRequestDTO.description());
        }

        this.exerciseRepository.save(existingExercise);
    }

    @Override
    public void deleteExercise(Long id) {
        if(exerciseRepository.existsById(id)){
            exerciseRepository.deleteById(id);
            return;
        }

        throw new RuntimeException("Resource Not Found");
    }

    private static void validateUserAssociativity(ExerciseDTO exerciseRequestDTO, Exercise existingExercise) {
        if(exerciseRequestDTO.exerciseID() != null && !existingExercise.getId().equals(exerciseRequestDTO.exerciseID())){
            throw new RuntimeException("Cannot modify Exercise Id");
        }

        if(exerciseRequestDTO.userID() != null && !existingExercise.getUser().getId().equals(exerciseRequestDTO.userID())){
            throw new RuntimeException("Cannot modify User Id");
        }
    }

    @Override
    public List<ExerciseDTO> getAllExercises(Long userID) {
        List<Exercise> exercises = exerciseRepository.findAllByUserId(userID);
        return ExerciseDTO.transferExercises(exercises);
    }

    @Override
    public ExerciseDTO getExerciseById(Long id) throws Exception{
        Optional<Exercise> exerciseOptl = exerciseRepository.findById(id);
        if(exerciseOptl.isEmpty()){
            throw new RuntimeException("Resource Not Found");
        }

        return ExerciseDTO.transferExercise(exerciseOptl.get());
    }

    private User validateAndGetUser(Long userID) throws Exception {
        Optional<User> userOptional = userService.findByID(userID);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return userOptional.get();
    }
}
