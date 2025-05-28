package org.africalib.gallery.controller;

import org.africalib.gallery.entity.Item;
import org.africalib.gallery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

  @Autowired
  ItemRepository itemRepository;

  @GetMapping("/api/items")
  public List<Item> getItems() {

    return itemRepository.findAll();
  }
}
