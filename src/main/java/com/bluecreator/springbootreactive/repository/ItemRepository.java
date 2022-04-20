package com.bluecreator.springbootreactive.repository;

import com.bluecreator.springbootreactive.domain.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item, String>,
        ReactiveQueryByExampleExecutor<Item> {

    //@Query("{'name' : ?0, 'age' : ?1 }")
    //Flux<Item> findItemsForCustomerMonthlyReport(String name, int age);

    //@Query(sort = "{'age' : -1 }")
    //Flux<Item> findSortedStuffForWeeklyReport();
}
