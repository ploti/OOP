package ru.nsu.fit.oop.arturploter.task_2_1_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

class JSONReader {

  Employees readParameters(File parameters) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(parameters, Employees.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
