package com.raza.healthtracker.health.graphql;

import com.raza.healthtracker.health.dto.SaveHealthLogInput;
import com.raza.healthtracker.health.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class HealthGraphQLController {

    private final HealthService service;

    private Long getUserId() {
        return 1L; // replace with JWT later
    }

    @MutationMapping
    public String saveHealthLog(@Argument SaveHealthLogInput input) {
        service.save(getUserId(), input);
        return "Saved";
    }

    @QueryMapping
    public String analyze(@Argument Long patientId) {
        return service.analyze(getUserId(), patientId);
    }
}