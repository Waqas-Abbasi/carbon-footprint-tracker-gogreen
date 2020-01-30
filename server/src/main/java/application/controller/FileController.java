package application.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

public class FileController {
  @RequestMapping(value = "download", method = RequestMethod.GET)
  public InputStreamResource FileSystemResource (HttpServletResponse response) throws IOException {
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=\"gui-0.1-SNAPSHOT-1.0.pkg\"");
    InputStreamResource resource = new InputStreamResource(new FileInputStream("/Users/kalinkostadinov/template/server/src/main/resources/uploads/gui-0.1-SNAPSHOT-1.0.pkg"));
    return resource;
  }
}
