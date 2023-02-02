package com.lithan.mow.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lithan.mow.model.constraint.EStatus;

import lombok.Data;

@Data
public class PartnershipRequest {

    @NotNull
    private String name;

    private String address;

    @Email
    private String email;

    @Size(min = 6)
    private String password;

    private EStatus status;

    private String imageUrl;

}
