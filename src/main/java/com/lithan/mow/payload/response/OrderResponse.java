package com.lithan.mow.payload.response;

import java.util.Date;
import java.util.Optional;

import com.lithan.mow.model.Order;
import com.lithan.mow.model.constraint.EStatus;

import lombok.Data;

@Data
public class OrderResponse {
   private Long id;
   private CustomerResponse orderBy;
   private CustomerResponse preparedBy;
   private CustomerResponse deliveredBy;
   private Date orderOn;
   private EStatus orderStatus;

   
   public OrderResponse(Order order) {
      if (Optional.ofNullable(order.getPreparedBy()).isPresent()) {
         this.preparedBy = new CustomerResponse(order.getPreparedBy());
         this.deliveredBy =new CustomerResponse(order.getDeliveredBy());
      }
      this.id = order.getId();
      this.orderBy = new CustomerResponse(order.getOrderdBy());
      this.orderOn = order.getOrderdOn();
      this.orderStatus = order.getStatus();
   }
}
