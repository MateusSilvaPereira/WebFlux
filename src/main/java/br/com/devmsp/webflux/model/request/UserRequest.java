package br.com.devmsp.webflux.model.request;

import br.com.devmsp.webflux.validator.TrimString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(


        @TrimString // my_Annotation
        @NotBlank(message = "must not be null or empty")
        @Size(min = 3, max = 50, message = "must be between 3 and 50 characters")
        String name,

        @TrimString
        @Email(message = "Invalid email")
        @NotBlank(message = "must not be null or empty")
        String email,

        @TrimString
        @Size(min = 3, max = 20, message = "must be between 3 and 20 characters")
        @NotBlank(message = "must not be null or empty")
        String password
) {}
