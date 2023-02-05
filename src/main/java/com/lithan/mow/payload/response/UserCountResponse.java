package com.lithan.mow.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCountResponse {
  private int totalUser;
  private int totalVolunteer;
  private int totalPartner;
  private int totalRider;

}
