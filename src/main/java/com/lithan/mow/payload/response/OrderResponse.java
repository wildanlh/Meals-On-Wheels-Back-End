package com.lithan.mow.payload.response;

import java.util.Date;

import com.lithan.mow.model.Order;

import lombok.Data;

@Data
public class OrderResponse {
   private Long id;
   private CustomerResponse orderBy;
   private CustomerResponse preparedBy;
   private CustomerResponse deliveredBy;
   private Date orderOn;

   public OrderResponse(Order order) {
      this.id = order.getId();
      this.orderBy = new CustomerResponse(order.getOrderdBy());
      this.preparedBy = new CustomerResponse(order.getPreparedBy());
      this.deliveredBy = new CustomerResponse(order.getDeliveredBy());
      this.orderOn = order.getOrderdOn();
   }
}
