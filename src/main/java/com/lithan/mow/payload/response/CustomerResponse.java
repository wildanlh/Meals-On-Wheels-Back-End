package com.lithan.mow.payload.response;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.constraint.EGender;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.model.constraint.EStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerResponse {
   private Long id;
   private String name;
   private String email;
   private String address;
   private EGender gander;
   private EStatus status;
   private String imageUrl;
   private ERole role;

   public CustomerResponse() {
   }
   public CustomerResponse(Customer user) {
      this.id = user.getId();
      this.name = user.getName();
      this.email = user.getEmail();
      this.address = user.getAddress();
      this.gander = user.getGender();
      this.status = user.getStatus();
      this.imageUrl = user.getImageUrl();
      this.role = user.getRole();
   }

}
