package io.nology.todolistspringbackend.tasks;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateTaskDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  @Min(1)
  @Max(5)
  private int priority;

  // @NotNull
  private LocalDateTime dueDate;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getPriority() {
    return priority;
  }

  public LocalDateTime getDueDate() {
    return dueDate;
  }
}
