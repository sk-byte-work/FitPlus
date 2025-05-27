package com.example.fitplus.workout;

import com.example.fitplus.ApplicationUtil;
import com.example.fitplus.set.SetRequestDTO;
import com.example.fitplus.set.SetService;
import com.example.fitplus.workoutdetails.WorkoutDetailsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private static final Logger logger = LoggerFactory.getLogger(WorkoutController.class);

    private final WorkoutService workoutService;
    private final WorkoutDetailsService workoutDetailsService;
    private final SetService setService;

    private WorkoutService getWorkoutService()
    {
        return this.workoutService;
    }

    private WorkoutDetailsService getWorkoutDetailsService()
    {
        return this.workoutDetailsService;
    }

    private SetService getSetService()
    {
        return this.setService;
    }

    public WorkoutController(WorkoutService workoutService, WorkoutDetailsService workoutDetailsService, SetService setService){
        this.workoutService = workoutService;
        this.workoutDetailsService = workoutDetailsService;
        this.setService = setService;
    }

    @PostMapping
    public ResponseEntity<Map> createWorkout(@RequestBody WorkoutRequestDTO workoutRequestDTO) throws Exception {
        workoutService.createWorkout(workoutRequestDTO);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Workout created successfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map> updateWorkout(@PathVariable Long id, @RequestBody Map<String, String> payload) throws Exception{
        String workoutName = payload.get("workoutName");
        workoutService.updateWorkout(id, workoutName);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Workout update successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> deleteWorkout(@PathVariable @Valid long id) throws Exception
    {
        getWorkoutService().deleteWorkout(id);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Workout deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/{id}/exercises")
    public ResponseEntity<Map> associateExercise(@PathVariable long id, @RequestParam @Valid long exerciseId) throws Exception
    {
        getWorkoutDetailsService().associateExerciseToWorkout(id, exerciseId);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Exercise associated to Workout successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/exercises/{exerciseId}")
    public ResponseEntity<Map> disassociateExercise(@PathVariable long id, @PathVariable long exerciseId) throws Exception
    {
        getWorkoutDetailsService().disAssociateExerciseFromWorkout(id, exerciseId);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Exercise disassociated from Workout successfully"), HttpStatus.OK);
    }

    @PostMapping("/{id}/exercises/{exerciseId}/sets")
    public ResponseEntity<Map> addSets(@PathVariable long id, @PathVariable long exerciseId, @RequestBody SetRequestDTO setRequestDTO) throws Exception
    {
        getSetService().addNewSetToExercise(id, exerciseId, setRequestDTO);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Set was added to the Workout :)"), HttpStatus.OK);
    }

    @PutMapping("/{id}/exercises/{exerciseId}/sets/{setId}")
    public ResponseEntity<?> updateSetDetails(
            @PathVariable @Valid long id,
            @PathVariable @Valid long exerciseId,
            @PathVariable @Valid long setId,
            @RequestBody @Valid SetRequestDTO setRequestDTO
    ) throws Exception
    {
        getSetService().updateSetDetails(id, exerciseId, setId, setRequestDTO);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Set was updated Successfully :)"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/exercises/{exerciseId}/sets/{setId}")
    public ResponseEntity<?> deleteSetDetails(
            @PathVariable @Valid long id,
            @PathVariable @Valid long exerciseId,
            @PathVariable @Valid long setId
    ) throws Exception
    {
        getSetService().deleteSetDetails(id, exerciseId, setId);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Set was deleted Successfully :)"), HttpStatus.OK);
    }

    @PostMapping("/{id}/finish")
    public ResponseEntity<Map> markWorkoutAsFinished(@PathVariable long id) throws Exception
    {
        getWorkoutService().markWorkoutAsCompleted(id);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Workout Marked as completed :)"), HttpStatus.OK);
    }

    @PostMapping("/{id}/pending")
    public ResponseEntity<Map> markWorkoutAsPending(@PathVariable long id) throws Exception
    {
        getWorkoutService().markWorkoutAsPending(id);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Workout Marked as Pending :)"), HttpStatus.OK);
    }

    @PostMapping("/{id}/exercises/{exerciseId}/sets/{setId}/finish")
    public ResponseEntity<Map> markExerciseSetAsCompleted(@PathVariable long id, @PathVariable long exerciseId, @PathVariable long setId) throws Exception
    {
        getSetService().markSetAsCompleted(id, exerciseId, setId);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Set Marked as completed :)"), HttpStatus.OK);
    }

    @PostMapping("/{id}/exercises/{exerciseId}/sets/{setId}/pending")
    public ResponseEntity<Map> markExerciseSetAsPending(@PathVariable long id, @PathVariable long exerciseId, @PathVariable long setId) throws Exception
    {
        getSetService().markSetAsPending(id, exerciseId, setId);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "Set Marked as pending :)"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getWorkouts() throws Exception
    {
        List<WorkoutResponseDTO> workouts = getWorkoutService().getAllWorkouts();
        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutDetails(@PathVariable long id)
    {
        WorkoutResponseDTO workoutResponseDTO = getWorkoutService().getWorkoutDetails(id);
        return ResponseEntity.ok(workoutResponseDTO);
    }

}
