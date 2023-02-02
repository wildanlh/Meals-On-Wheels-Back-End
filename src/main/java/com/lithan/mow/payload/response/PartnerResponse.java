package com.lithan.mow.payload.response;

import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.EStatus;

import lombok.Data;

@Data
public class PartnerResponse {

  private Long id;
  private String name;
  private String address;
  private String email;
  private EStatus status;
  private String imageUrl;

  public PartnerResponse(Partner partner) {
    this.id = partner.getId();
    this.name = partner.getName();
    this.address = partner.getAddress();
    this.email = partner.getEmail();
    this.status = partner.getStatus();
    this.imageUrl = partner.getImageUrl();
  }

}
