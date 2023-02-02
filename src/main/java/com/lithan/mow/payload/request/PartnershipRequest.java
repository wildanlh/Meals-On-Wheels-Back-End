package com.lithan.mow.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PartnershipRequest {
    
    @NotNull
    private String companyName;

    @Email
    private String email;
    private String reason;
}
