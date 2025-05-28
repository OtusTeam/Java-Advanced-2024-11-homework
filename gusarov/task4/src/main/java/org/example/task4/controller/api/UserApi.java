package org.example.task4.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.task4.models.ResultWithId;
import org.example.task4.models.UserReq;
import org.example.task4.models.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/users")
public interface UserApi {

    @PostMapping
    @Operation(description = "Добавление пользователя",
            responses = {
                    @ApiResponse(description = "Created",responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultWithId.class))),
                    @ApiResponse(description = "Bad Request",responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
                    @ApiResponse(description = "Unprocessable Entity",responseCode = "422", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    ResponseEntity<ResultWithId> create(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody() UserReq req);
}
