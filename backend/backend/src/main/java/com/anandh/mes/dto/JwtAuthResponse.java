package com.anandh.mes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthResponse {

    private String accessToken;

    private String tokenType;

    private String username;

    private String role;

}