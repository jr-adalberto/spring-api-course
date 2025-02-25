package com.spring.course.resource;

import com.spring.course.domain.Request;
import com.spring.course.domain.RequestStage;
import com.spring.course.dto.RequestSavedto;
import com.spring.course.dto.RequestUpdatedto;
import com.spring.course.model.PageModel;
import com.spring.course.model.PageRequestModel;
import com.spring.course.security.AccessManager;
import com.spring.course.service.RequestService;
import com.spring.course.service.RequestStageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "requests")
public class RequestResource {

    private final RequestService requestService;
    private final RequestStageService stageService;
    private final AccessManager accessManager;

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody @Valid RequestSavedto requestdto) {
        Request request = requestdto.transformToRequest();
        Request createdRequest = requestService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }


    @PreAuthorize("@accessManager.isRequestOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Request> update(@PathVariable(name = "id") Long id, @RequestBody @Valid RequestUpdatedto requestdto) {
        Request request = requestdto.transformToRequest();
        request.setId(id);

        Request updatedRequest = requestService.update(request);
        return ResponseEntity.ok(updatedRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getById(@PathVariable(name = "id") Long id) {
        Request request = requestService.getById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping
    public ResponseEntity<PageModel<Request>> listAll(
            @RequestParam Map<String, String> params) {

        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Request> pm = requestService.listAllOnLazyMode(pr);

        return ResponseEntity.ok(pm);
    }

    @GetMapping("/{id}/request-stages")
    public ResponseEntity<PageModel<RequestStage>> listAllStagesById(
            @PathVariable(name = "id") Long id,
            @RequestParam Map<String, String> params) {
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<RequestStage> pm = stageService.listAllByRequestIdOnLazyModel(id, pr);
        return ResponseEntity.ok(pm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        requestService.delete(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Request deleted successfully");
        response.put("id", id.toString());

        return ResponseEntity.ok(response);
    }


}
