package com.example.fitplus.exercise;

import com.example.fitplus.ApplicationUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<Map> createExercise(@RequestBody ExerciseDTO exerciseRequestDTO) throws Exception{
        try{
            this.exerciseService.createExercise(exerciseRequestDTO);
            return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Exercise created successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception occurred during Exercise Creation: {}", e);
            return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.BAD_REQUEST.value(), "Error occurred while creating Exercise :("), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExercise(@Valid @PathVariable Long id, @Valid @RequestBody ExerciseDTO exerciseRequestDTO) throws Exception{
        try{
            exerciseService.updateExercise(id, exerciseRequestDTO);
            return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Exercise updated successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception occurred during Exercise Update: {}", e);
            return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@Valid @PathVariable Long id) throws Exception{
        try{
            exerciseService.deleteExercise(id);
            return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Exercise Deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception occurred during Exercise Deletion: {}", e);
            return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllExercises() throws Exception {
        List<ExerciseDTO> exercises = this.exerciseService.getAllExercises();
        if(exercises.isEmpty()){
          return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.NO_CONTENT.value(), "There are no exercises present for the User"), HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExerciseById(@Valid @PathVariable Long id) throws Exception{
        try{
            ExerciseDTO exerciseDTO = exerciseService.getExerciseById(id);
            return new ResponseEntity<>(exerciseDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception occurred during Exercise details fetch: {}", e);
            return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.NOT_FOUND.value(), "Resource Not Found"), HttpStatus.NOT_FOUND);
        }
    }
}
