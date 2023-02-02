package com.lithan.mow.payload.response;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.constraint.EGender;
import com.lithan.mow.model.constraint.EStatus;

import lombok.Data;

@Data
public class CustomerResponse {
   private Long id;
   private String email;
   private String address;
   private EGender gander;
   private EStatus status;
   private String imageUrl;

   public CustomerResponse(Customer user) {
      this.id = user.getId();
      this.email = user.getEmail();
      this.address = user.getAddress();
      this.gander = user.getGender();
      this.status = user.getStatus();
      this.imageUrl = user.getImageUrl();
   }
}
