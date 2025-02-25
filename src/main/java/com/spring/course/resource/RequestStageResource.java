package com.spring.course.resource;

import com.spring.course.domain.Request;
import com.spring.course.domain.RequestStage;
import com.spring.course.domain.User;
import com.spring.course.dto.RequestStageSavedto;
import com.spring.course.repository.RequestRepository;
import com.spring.course.repository.UserRepository;
import com.spring.course.service.RequestStageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "request-stages")
public class RequestStageResource {

    private final RequestStageService stageService;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody @Valid RequestStageSavedto requestStagedto) {
        User owner = userRepository.findById(requestStagedto.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Owner cannot find this id: " + requestStagedto.getOwner().getId()));

        Request request = requestRepository.findById(requestStagedto.getRequest().getId())
                .orElseThrow(() -> new RuntimeException("Request cannot find this id: " + requestStagedto.getRequest().getId()));

        RequestStage requestStage = requestStagedto.transformToRequestStage();
        requestStage.setOwner(owner);
        requestStage.setRequest(request);

        RequestStage createdStage = stageService.save(requestStage);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdStage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RequestStage> getById(@PathVariable(name = "id") Long id) {
        RequestStage stage = stageService.getById(id);
        return ResponseEntity.ok(stage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        stageService.delete(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "RequestStage deleted successfully");
        response.put("id", id.toString());

        return ResponseEntity.ok(response);
    }

}
