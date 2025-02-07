package com.spring.course.dto;

import com.spring.course.domain.Request;
import com.spring.course.domain.RequestStage;
import com.spring.course.domain.User;
import com.spring.course.enums.RequestState;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestStageSavedto {

    private String description;

    @NotNull(message = "State required")
    private RequestState state;

    @NotNull(message = "Request required")
    private Request request;

    @NotNull(message = "Owner required")
    private User owner;

    @NotNull(message = "Realization date required")
    private Date realizationDate;

    public RequestStage transformToRequestStage() {
        return new RequestStage(null, description, realizationDate, state, request, owner);
    }
}
