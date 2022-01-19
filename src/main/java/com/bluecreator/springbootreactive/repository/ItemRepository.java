package com.bluecreator.springbootreactive.repository;

import com.bluecreator.springbootreactive.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {
}
