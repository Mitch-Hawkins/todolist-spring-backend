package io.nology.todolistspringbackend.tasks;

import io.nology.todolistspringbackend.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
@Transactional
public class TaskService {

  @Autowired
  private TaskRepository repo;

  @Autowired
  private ModelMapper mapper;

  public List<Task> getAll() throws Exception {
    try {
      return this.repo.findAll();
    } catch (DataAccessException dataAccessException) {
      throw new ResourceNotFoundException(
        "Tasks",
        "An error occured while fetching tasks"
      );
    } catch (Exception genericException) {
      throw new Exception();
    }
  }

  public Optional<Task> findById(Long id) throws Exception {
    try {
      return this.repo.findById(id);
    } catch (DataAccessException dataAccessException) {
      throw new ResourceNotFoundException("Tasks", id);
    } catch (Exception genericException) {
      throw new Exception();
    }
  }

  public Task createTask(@Valid CreateTaskDTO data)
    throws MethodArgumentNotValidException {
    Task newTask = mapper.map(data, Task.class);
    return this.repo.save(newTask);
  }

  public Optional<Task> updateById(@Valid UpdateTaskDTO data, Long id)
    throws Exception {
    Optional<Task> maybeTask = this.findById(id);
    if (maybeTask.isEmpty()) {
      return maybeTask;
    }
    Task foundTask = maybeTask.get();
    if (data.getDueDate() != null) { //Need to add this to modal
      foundTask.setDueDate(data.getDueDate());
    }
    mapper.map(data, foundTask);
    Task updated = this.repo.save(foundTask);
    return Optional.of(updated);
  }

  public boolean deleteById(Long id) {
    Optional<Task> maybeTask = this.repo.findById(id);
    if (maybeTask.isEmpty()) {
      return false;
    }
    this.repo.delete(maybeTask.get());
    return true;
  }
}
