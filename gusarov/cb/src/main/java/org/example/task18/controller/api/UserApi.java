package org.example.task18.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.task18.models.ResultWithId;
import org.example.task18.models.UserReq;
import org.example.task18.models.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping(value = "/{id}/year")
    @Operation(description = "Получения возраста пользователя по его id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
            }
    )
    ResponseEntity<Integer> year(@PathVariable UUID id);


}
