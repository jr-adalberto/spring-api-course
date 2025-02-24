package com.spring.course.dto;

import com.spring.course.domain.Request;
import com.spring.course.domain.RequestStage;
import com.spring.course.domain.User;
import com.spring.course.enums.RequestState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestUpdatedto {

    @NotBlank(message = "Subject required")
    private String subject;

    private String description;

    @NotNull(message = "State required")
    private RequestState state;

    @NotNull(message = "Owner required")
    private User owner;

    private List<RequestStage> stages = new ArrayList<RequestStage>();

    public Request transformToRequest() {
        Request request = new Request(null, this.subject, this.description, null, this.state, this.owner, stages, null);
        return request;
    }
}
