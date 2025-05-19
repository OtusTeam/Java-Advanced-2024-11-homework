package org.example.task12.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.task12.models.ResultWithId;
import org.example.task12.models.UserReq;
import org.example.task12.models.Error;
import org.example.task12.persistence.entity.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("/api/v1/users")
public interface UserApi {

    @PostMapping
    @Operation(description = "Добавление пользователя",
            responses = {
                    @ApiResponse(description = "Ok",responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultWithId.class))),
                    @ApiResponse(description = "Bad Request",responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
                    @ApiResponse(description = "Unprocessable Entity",responseCode = "422", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    Mono<User> create(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody() UserReq req);


    @GetMapping()
    @Operation(description = "Список пользователей",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
            }
    )
    Flux<User> all();

    @GetMapping(value = "/names")
    @Operation(description = "Список всех имён пользователей",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
            }
    )
    Mono<List<String>> names();

    @GetMapping(value = "/emails")
    @Operation(description = "Список email не пустых",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
            }
    )
    Flux<String> emails();
}
