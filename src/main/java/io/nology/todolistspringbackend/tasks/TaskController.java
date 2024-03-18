package io.nology.todolistspringbackend.tasks;

import io.nology.todolistspringbackend.exceptions.ResourceNotFoundException;
import io.nology.todolistspringbackend.exceptions.ValidationException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @GetMapping
  public ResponseEntity<List<Task>> getAllTasks() {
    try {
      List<Task> allTasks = this.taskService.getAll();
      return new ResponseEntity<>(allTasks, HttpStatus.OK);
    } catch (ResourceNotFoundException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getTaskById(@PathVariable Long id) {
    try {
      Optional<Task> maybeTask = this.taskService.findById(id);
      if (maybeTask.isPresent()) {
        Task foundTask = maybeTask.get();
        return new ResponseEntity<>(foundTask, HttpStatus.FOUND);
      } // } else {
      //   throw new NotFoundException();
      // }
      return new ResponseEntity<>("dfgdfg", HttpStatus.NOT_FOUND);
    } catch (ResourceNotFoundException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<Object> createTask(
    @Valid @RequestBody CreateTaskDTO data
  ) throws ValidationException {
    try {
      Task createdTask = this.taskService.createTask(data);
      return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    } catch (MethodArgumentNotValidException ex) { //!!!
      return new ResponseEntity<>("fgdfg", HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Task> updateTaskById(
    @Valid @RequestBody UpdateTaskDTO data,
    @PathVariable Long id
  ) throws Exception {
    Optional<Task> maybeUpdatedTask = this.taskService.updateById(data, id);
    if (maybeUpdatedTask.isPresent()) {
      Task foundUpdatedTask = maybeUpdatedTask.get();
      return new ResponseEntity<>(foundUpdatedTask, HttpStatus.OK);
    } else {
      throw new NotFoundException();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Task> deleteTaskById(@PathVariable Long id)
    throws NotFoundException {
    boolean deleted = this.taskService.deleteById(id);
    if (!deleted) {
      throw new NotFoundException();
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
