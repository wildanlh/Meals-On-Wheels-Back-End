package com.lithan.mow.payload.response;

import java.util.Comparator;

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
   private String fileUrl;
   private boolean activate;

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
      this.fileUrl = user.getFileUrl();
      this.activate = user.isActive();
   }

   public static final Comparator<CustomerResponse> comparatorByIdDesc = new Comparator<CustomerResponse>() {

      public int compare(CustomerResponse s1, CustomerResponse s2) {
         Long customer1 = s1.getId();
         Long customer2 = s2.getId();

         return customer2.compareTo(customer1);

      }
   };

}
