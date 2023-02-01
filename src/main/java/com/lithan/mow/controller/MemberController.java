package com.lithan.mow.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MemberController {

   @PostMapping("/order")
   public MessageResponse postOrder() {

      return new MessageResponse("your order have send");
   }

}
