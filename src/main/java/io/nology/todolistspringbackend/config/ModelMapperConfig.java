package io.nology.todolistspringbackend.config;

import io.nology.todolistspringbackend.tasks.CreateTaskDTO;
import io.nology.todolistspringbackend.tasks.Task;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();

    mapper
      .typeMap(String.class, String.class)
      .setConverter(new StringTrimConverter());

    mapper.getConfiguration().setSkipNullEnabled(true);

    mapper
      .typeMap(CreateTaskDTO.class, Task.class)
      .addMappings(m ->
        m
          .using(new PriorityConverter())
          .map(CreateTaskDTO::getPriority, Task::setPriority)
      );

    return mapper;
  }

  private class PriorityConverter extends AbstractConverter<Integer, Integer> {

    @Override
    public Integer convert(Integer context) {
      if (context == null) {
        return null;
      }
      if (context >= 1 && context <= 5) {
        return context;
      } else {
        throw new IllegalArgumentException("Priority must be Between 1 and 5");
      }
    }
  }

  private class StringTrimConverter extends AbstractConverter<String, String> {

    @Override
    public String convert(String context) {
      if (context == null) {
        return null;
      }
      return context.trim();
    }
  }
}
