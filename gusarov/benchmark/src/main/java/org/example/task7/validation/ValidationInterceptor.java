package org.example.task7.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ValidationInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean success = true;
        if (handler instanceof HandlerMethod handlerMethod && request.getDispatcherType() != DispatcherType.ERROR) {

            Optional<MethodParameter> requestBody = Arrays.stream(handlerMethod.getMethodParameters())
                    .filter(e -> Arrays.stream(e.getParameterAnnotations())
                            .anyMatch(e1 -> e1.annotationType()
                                    .isAssignableFrom(RequestBody.class)))
                    .findAny();
            if (requestBody.isPresent()) {

                MethodParameter methodParameter = requestBody.get();
                String simpleName = methodParameter.getParameterType()
                        .getSimpleName();
                try {
                    String schemaName = String.format("classpath:///schema/%s.json", simpleName);
                    JsonSchemaFactory instance = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
                    JsonSchema schema = instance.getSchema(ResourceUtils.toURI(schemaName));
                    Set<ValidationMessage> validate = schema.validate(objectMapper.readTree(request.getInputStream()));
                    if (!validate.isEmpty()) {
                        success = false;
                        writeError(validate.stream()
                                .map(ValidationMessage::getMessage)
                                .collect(Collectors.joining("; ")));
                    }

                } catch (Exception ex) {
                    success = false;
                    writeError(ex.getMessage());
                }
            }
        }
        if (success) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        return false;
    }


    private void writeError(String errorMessage) {
        throw new JsonValidationException(errorMessage);
    }

}