package com.spring_course.resource;

import com.spring_course.domain.RequestStage;
import com.spring_course.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "request-stages")
public class RequestStageResource {

    @Autowired
    private RequestStageService stageService;

    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody RequestStage requestStage){
        RequestStage createdStage = stageService.save(requestStage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStage);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<RequestStage> getById(@PathVariable(name = "id")Long id){
        RequestStage stage = stageService.getById(id);
        return ResponseEntity.ok(stage);
    }
}
