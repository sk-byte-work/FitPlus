package com.example.fitplus.exercise;

import com.example.fitplus.AppThreadLocals;
import com.example.fitplus.exceptions.FitPlusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository){
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void createExercise(ExerciseDTO exerciseRequestDTO) throws Exception
    {
        Exercise exercise = Exercise.createExercise(exerciseRequestDTO);
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
        if(exerciseRepository.existsByIdAndUserId(id, AppThreadLocals.getCurrentUserId())){
            exerciseRepository.deleteById(id);
            return;
        }

        logger.warn("Exercise Not Found");
        throw new FitPlusException("Exercise Not Found");
    }

    private static void validateUserAssociativity(ExerciseDTO exerciseRequestDTO, Exercise existingExercise)
    {
        if(exerciseRequestDTO.exerciseID() != null && !existingExercise.getId().equals(exerciseRequestDTO.exerciseID())){
            logger.error("You cannot modify the Exercise PK. Existing: {} Received: {}", existingExercise.getId(), exerciseRequestDTO.exerciseID());
            throw new FitPlusException("Cannot modify Exercise Id");
        }
    }

    @Override
    public List<ExerciseDTO> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAllByUserId(AppThreadLocals.getCurrentUserId());
        return ExerciseDTO.transferExercises(exercises);
    }

    @Override
    public ExerciseDTO getExerciseById(Long id) throws Exception{
        Exercise exercise = getExercise(id);
        return ExerciseDTO.transferExercise(exercise);
    }

    @Override
    public Exercise getExercise(long id) {
        Optional<Exercise> exerciseOptl = exerciseRepository.findByIdAndUserId(id, AppThreadLocals.getCurrentUserId());
        if(exerciseOptl.isEmpty()){
            logger.info("Exercise Not Found. Exercise Id: {}", id);
            throw new FitPlusException("Exercise Not Found");
        }

        return  exerciseOptl.get();
    }
}
