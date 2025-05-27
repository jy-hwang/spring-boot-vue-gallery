package org.africalib.gallery.controller;

import org.africalib.gallery.dto.OrderDto;
import org.africalib.gallery.entity.Order;
import org.africalib.gallery.repository.OrderRepository;
import org.africalib.gallery.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

  @Autowired
  JwtService jwtService;

  @Autowired
  OrderRepository orderRepository;

  @PostMapping("/api/orders")
  public ResponseEntity pushOrder(
          @RequestBody OrderDto dto,
          @CookieValue(value = "token", required = false) String token) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");// 401
    }

    Order newOrder = new Order();
    newOrder.setMemberId(jwtService.getId(token));
    newOrder.setName(dto.getName());
    newOrder.setAddress(dto.getAddress());
    newOrder.setPayment(dto.getPayment());
    newOrder.setCardNumber(dto.getCardNumber());
    newOrder.setItems(dto.getItems());

    orderRepository.save(newOrder);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/api/orders")
  public ResponseEntity getOrder(@CookieValue(value = "token", required = false) String token) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");// 401
    }

    List<Order> orders = orderRepository.findAll();


    return new ResponseEntity<>(orders, HttpStatus.OK);
  }
}
