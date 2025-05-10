package com.example.fitplus.workout;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record WorkoutRequestDTO(@NotEmpty @NotNull String workoutName,@NotEmpty @NotNull Long userId) {
}
