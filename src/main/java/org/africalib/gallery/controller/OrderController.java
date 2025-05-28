package org.africalib.gallery.controller;

import org.africalib.gallery.dto.OrderDto;
import org.africalib.gallery.entity.Order;
import org.africalib.gallery.repository.CartRepository;
import org.africalib.gallery.repository.OrderRepository;
import org.africalib.gallery.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class OrderController {

  @Autowired
  JwtService jwtService;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  CartRepository cartRepository;

  @Transactional
  @PostMapping("/api/orders")
  public ResponseEntity pushOrder(
      @RequestBody OrderDto dto,
      @CookieValue(value = "token", required = false) String token) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");// 401
    }
    int memberId = jwtService.getId(token);

    Order newOrder = new Order();
    newOrder.setMemberId(memberId);
    newOrder.setName(dto.getName());
    newOrder.setAddress(dto.getAddress());
    newOrder.setPayment(dto.getPayment());
    newOrder.setCardNumber(dto.getCardNumber());
    newOrder.setItems(dto.getItems());

    orderRepository.save(newOrder);

    cartRepository.deleteByMemberId(memberId);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/api/orders")
  public ResponseEntity getOrder(@CookieValue(value = "token", required = false) String token) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");// 401
    }
    int memberId = jwtService.getId(token);

    List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);

    return new ResponseEntity<>(orders, HttpStatus.OK);
  }
}
