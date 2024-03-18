package io.nology.todolistspringbackend.tasks;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateTaskDTO {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
    "yyyy-MM-dd'T'HH:mm:ss"
  );

  @Pattern(regexp = "^(?=\\S).*$", message = "Task name Cannot be empty")
  private String name;

  @Pattern(regexp = "^(?=\\S).*$", message = "Task description Cannot be empty")
  private String description;

  @Min(1)
  @Max(5)
  private int priority;

  @Pattern(
    regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
    // regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$",
    message = "Task due date must be formatted correctly"
  )
  private String dueDate;

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
    return dueDate != null ? LocalDateTime.parse(dueDate, formatter) : null;
  }

  public void setDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate != null ? dueDate.format(formatter) : null;
  }
}
