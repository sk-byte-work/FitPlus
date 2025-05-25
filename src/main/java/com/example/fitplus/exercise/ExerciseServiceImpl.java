package com.example.fitplus.exercise;

import com.example.fitplus.exceptions.FitPlusException;
import com.example.fitplus.security.AuthUtil;
import com.example.fitplus.users.User;
import com.example.fitplus.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    private final UserService userService;
    private final ExerciseRepository exerciseRepository;
    private final AuthUtil authUtil;

    public ExerciseServiceImpl(UserService userService, ExerciseRepository exerciseRepository, AuthUtil authUtil){
        this.userService = userService;
        this.exerciseRepository = exerciseRepository;
        this.authUtil = authUtil;
    }

    @Override
    public void createExercise(ExerciseDTO exerciseRequestDTO) throws Exception
    {
        Long userID = getAuthUtil().getUserId();
        User user = validateAndGetUser(userID);
        Exercise exercise = Exercise.createExercise(user, exerciseRequestDTO);
        this.exerciseRepository.save(exercise);
    }

    @Override
    public void updateExercise(Long exerciseId, ExerciseDTO exerciseRequestDTO) throws Exception {
        Optional<Exercise> existingExerciseOptl = exerciseRepository.findById(exerciseId);
        if(existingExerciseOptl.isEmpty()){
            logger.info("Exercise Not Found");
            throw new FitPlusException("Resource Not Found");
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

        logger.warn("Exercise Not Found");
        throw new FitPlusException("Exercise Not Found");
    }

    private static void validateUserAssociativity(ExerciseDTO exerciseRequestDTO, Exercise existingExercise) {
        if(exerciseRequestDTO.exerciseID() != null && !existingExercise.getId().equals(exerciseRequestDTO.exerciseID())){
            logger.error("You cannot modify the Exercise PK. Existing: {} Received: {}", existingExercise.getId(), exerciseRequestDTO.exerciseID());
            throw new FitPlusException("Cannot modify Exercise Id");
        }

        Long userId = existingExercise.getUser().getId();
        if(exerciseRequestDTO.userID() != null && !userId.equals(exerciseRequestDTO.userID())){
            logger.error("You cannot modify the User Id, Existing: {}, Received: {}", userId, exerciseRequestDTO.userID());
            throw new FitPlusException("Cannot modify User Id");
        }
    }

    @Override
    public List<ExerciseDTO> getAllExercises() {
        Long userID = getAuthUtil().getUserId();
        List<Exercise> exercises = exerciseRepository.findAllByUserId(userID);
        return ExerciseDTO.transferExercises(exercises);
    }

    @Override
    public ExerciseDTO getExerciseById(Long id) throws Exception{
        Exercise exercise = getExercise(id);
        return ExerciseDTO.transferExercise(exercise);
    }

    @Override
    public Exercise getExercise(long id) {
        Optional<Exercise> exerciseOptl = exerciseRepository.findById(id);
        if(exerciseOptl.isEmpty()){
            logger.info("Exercise Not Found. Exercise Id: {}", id);
            throw new FitPlusException("Exercise Not Found");
        }

        return  exerciseOptl.get();
    }

    private User validateAndGetUser(Long userID) throws Exception {
        Optional<User> userOptional = userService.findByID(userID);
        if(userOptional.isEmpty()){
            logger.info("User not found. User id: {}", userID);
            throw new FitPlusException("User not found");
        }
        return userOptional.get();
    }

    public AuthUtil getAuthUtil()
    {
        return authUtil;
    }
}
