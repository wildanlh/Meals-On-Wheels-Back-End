package com.lithan.mow.payload.request;

import lombok.Data;

@Data
public class DonateRequest {
    
    private long amount;
    private long cardNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String expirationDate; // idk what is the type
    private String cw;
    private String message;
}
