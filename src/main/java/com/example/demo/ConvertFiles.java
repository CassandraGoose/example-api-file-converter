package com.example.demo;

import com.devetry.converter.converter_library.Converter;
import com.devetry.converter.converter_library.InputFormat;
import com.devetry.converter.converter_library.OutputFormat;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;


@RestController
public class ConvertFiles {
  ConvertFiles() {}
  // Hit localhost:8080/convert with a body of form-data, key: 'file' value: uploaded file
  @RequestMapping(value = "/convert", method = RequestMethod.POST)
  public ResponseEntity<?> convertFile(@RequestParam("file") MultipartFile file) throws IOException, Exception {
    Converter converter = new Converter();
    byte[] bytes = file.getBytes();
    // choose whatever input/output formats you'd like below
    byte[] convertedBytes = converter.convertFileFromByteArray(bytes, InputFormat.PDF, OutputFormat.TIF);
    // choose wherever you'd like below
    Files.write(Paths.get("/Users/cassandratorske/Workspace/saved.tif"), convertedBytes);
    return new ResponseEntity<String>("Please check that this worked.", HttpStatus.OK);
  }


  // Hit localhost:8080/convert-with-params with a body for form-data
  // key: "file", value: "filepath"
  // key: "inputFormat", value: "DOCX" (or whatever format you are using)
  // key: "outputFormat", value: "TIF" (or whatever format you are usihng)
  @RequestMapping(value = "/convert-with-params", method = RequestMethod.POST)
  public ResponseEntity<?> convertFile(@RequestParam("inputFormat") InputFormat input, @RequestParam("outputFormat") OutputFormat output, @RequestParam("file") MultipartFile file) throws IOException, Exception {
    Converter converter = new Converter();
    byte[] bytes = file.getBytes();
    byte[] convertedBytes = converter.convertFileFromByteArray(bytes, input, output);
    // choose where to save and name your file below
    Files.write(Paths.get("./saved.tif"), convertedBytes);
    return new ResponseEntity<String>("Please check that this worked", HttpStatus.OK);
  }

  @RequestMapping(value = "/convert-from-path", method = RequestMethod.POST) 
  public ResponseEntity<?> convertFile(@RequestParam("inputFormat") InputFormat input, @RequestParam("outputFormat") OutputFormat output, @RequestParam("path") String path) throws IOException, Exception {
    Converter converter = new Converter();
    byte[] convertedBytes = converter.convertFileFromPath(path, input, output);
    Files.write(Paths.get("./saved.tif"), convertedBytes);
    return new ResponseEntity<String>("Please check that this worked", HttpStatus.OK);
  }
}