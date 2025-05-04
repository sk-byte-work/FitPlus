package com.example.fitplus.exercise;

import com.example.fitplus.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Exercise {

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Exercise(){

    }

    public Exercise(Long id, String exerciseName, String category, String description, User user){
        this.id = id;
        this.exerciseName = exerciseName;
        this.category = category;
        this.description = description;
        this.user = user;
    }

    public Exercise(String exerciseName, String category, String description, User user){
        this.exerciseName = exerciseName;
        this.category = category;
        this.description = description;
        this.user = user;
    }

    public static Exercise createExercise(User user, ExerciseDTO exerciseRequestDTO) {
        return new Exercise(exerciseRequestDTO.exerciseName(), exerciseRequestDTO.category(), exerciseRequestDTO.description(), user);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
