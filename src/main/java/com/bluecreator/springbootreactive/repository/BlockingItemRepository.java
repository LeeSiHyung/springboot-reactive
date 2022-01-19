package com.bluecreator.springbootreactive.repository;

import com.bluecreator.springbootreactive.domain.Item;
import org.springframework.data.repository.CrudRepository;

public interface BlockingItemRepository extends CrudRepository<Item, String> {
}
