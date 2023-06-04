package com.api.bigu.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Email Request")
public class EmailRequest {

    @NonNull
    @Pattern(regexp = "[A-Za-z0-9]+\\.[A-Za-z0-9]+@[A-Za-z0-9]+\\.ufcg\\.edu\\.br", message = "email not valid")
    @Schema(example = "aluno@ccc.ufcg.edu.br")
    private String email;

}
