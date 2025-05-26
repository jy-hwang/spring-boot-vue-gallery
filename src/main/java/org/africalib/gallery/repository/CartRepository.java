package org.africalib.gallery.repository;

import org.africalib.gallery.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

  List<Cart> findByMemberId(int memberId);

  Cart findByMemberIdAndItemId(int memberId, int itemId);

}
