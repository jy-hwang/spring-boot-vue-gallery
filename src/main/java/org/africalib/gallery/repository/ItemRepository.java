package org.africalib.gallery.repository;

import org.africalib.gallery.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
  List<Item> findByIdIn(List<Integer> ids);
}
