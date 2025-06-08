package com.example.fitplus.exercise;

import com.example.fitplus.UserScopedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Filter;

@Entity
public class Exercise extends UserScopedEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    @NotNull @NotEmpty
    private String exerciseName;

    @Column(nullable = false)
    @NotNull @NotEmpty
    private String category;

    private String description;

    public Exercise(){

    }

    public Exercise(Long id, String exerciseName, String category, String description){
        this.id = id;
        this.exerciseName = exerciseName;
        this.category = category;
        this.description = description;
    }

    public Exercise(String exerciseName, String category, String description){
        this.exerciseName = exerciseName;
        this.category = category;
        this.description = description;
    }

    public static Exercise createExercise(ExerciseDTO exerciseRequestDTO) {
        return new Exercise(exerciseRequestDTO.exerciseName(), exerciseRequestDTO.category(), exerciseRequestDTO.description());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
