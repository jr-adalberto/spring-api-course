package com.spring.course.dto;

import com.spring.course.domain.Request;
import com.spring.course.domain.RequestStage;
import com.spring.course.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestSavedto {

    @NotBlank(message = "Subject required")
    private String subject;
    private String description;

    @NotNull(message = "Owner required")
    private User owner;
    private List<RequestStage> stages = new ArrayList<RequestStage>();

    public Request transformToRequest() {
        Request request = new Request(null, this.subject, this.description, null, null, this.owner, stages);
        return request;
    }
}
