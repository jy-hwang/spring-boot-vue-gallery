package org.africalib.gallery.controller;

import org.africalib.gallery.entity.Cart;
import org.africalib.gallery.entity.Item;
import org.africalib.gallery.repository.CartRepository;
import org.africalib.gallery.repository.ItemRepository;
import org.africalib.gallery.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CartController {

  @Autowired
  JwtService jwtService;

  @Autowired
  CartRepository cartRepository;

  @Autowired
  ItemRepository itemRepository;

  @GetMapping("/api/cart/items")
  public ResponseEntity getCartItems(@CookieValue(value = "token", required = false) String token) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");// 401
    }
    int memberId = jwtService.getId(token);
    List<Cart> carts = cartRepository.findByMemberId(memberId);
    List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList();
    List<Item> items = itemRepository.findByIdIn(itemIds);

    return new ResponseEntity<>(items, HttpStatus.OK);

  }

  @PostMapping("/api/cart/items/{itemId}")
  public ResponseEntity pushCartItem(@PathVariable int itemId, @CookieValue(value = "token", required = false) String token) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");// 401
    }

    int memberId = jwtService.getId(token);
    Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

    if (cart == null) {
      Cart newCart = new Cart();
      newCart.setMemberId(memberId);
      newCart.setItemId(itemId);
      cartRepository.save(newCart);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/api/cart/items/{itemId}")
  public ResponseEntity removeCartItem(@PathVariable int itemId, @CookieValue(value = "token", required = false) String token) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");// 401
    }
    int memberId = jwtService.getId(token);
    Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

    cartRepository.delete(cart);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
