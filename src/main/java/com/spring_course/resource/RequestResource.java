package com.spring_course.resource;

import com.spring_course.domain.Request;
import com.spring_course.domain.RequestStage;
import com.spring_course.model.PageModel;
import com.spring_course.model.PageRequestModel;
import com.spring_course.service.RequestService;
import com.spring_course.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestStageService stageService;


    @PostMapping
    public ResponseEntity<Request> save(@RequestBody Request request) {
        Request createdRequest = requestService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> update(@PathVariable(name = "id") Long id, @RequestBody Request request) {
        request.setId(id);
        Request updateRequest = requestService.update(request);
        return ResponseEntity.ok(updateRequest);
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
    public ResponseEntity<PageModel<RequestStage>> listAllStagesById(@PathVariable(name = "id") Long id,
                                                                     @RequestParam(value = "page") int page,
                                                                     @RequestParam(value = "size") int size) {
        PageRequestModel pr = new PageRequestModel(page, size);
        PageModel<RequestStage> pm = stageService.listAllByRequestIdOnLazyModel(id, pr);
        return ResponseEntity.ok(pm);
    }

}
