package io.github.maykelrodrigs.pharmacy.controller.swagger;

import io.github.maykelrodrigs.pharmacy.controller.exception.ApiError;
import io.github.maykelrodrigs.pharmacy.domain.Establishment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface EstablishmentApi {

    @Operation(summary = "create Establishment", description = "Creates an Establishment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "item created",
                    content = @Content(schema = @Schema(implementation = Establishment.class))),
            @ApiResponse(responseCode = "400", description = "invalid request",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Establishment> create(@RequestBody @Valid Establishment establishment);
    
}
