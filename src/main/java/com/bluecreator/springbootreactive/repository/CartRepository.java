package com.bluecreator.springbootreactive.repository;

import com.bluecreator.springbootreactive.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {
}
