package com.lithan.mow.payload.response;

import com.lithan.mow.model.Customer;

import lombok.Data;

@Data
public class CustomerResponse {
   private Long id;
   private String email;
   private String address;

   public CustomerResponse(Customer user) {
      this.id = user.getId();
      this.email = user.getEmail();
      this.address = user.getAddress();
   }
}
