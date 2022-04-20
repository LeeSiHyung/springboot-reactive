package com.bluecreator.springbootreactive;

import com.bluecreator.springbootreactive.domain.Item;
import com.bluecreator.springbootreactive.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

//@DataMongoTest
public class MongoDbSliceTest {

    // @Autowired
    ItemRepository repository;

    // @Test
    void itemRepositorySavesItems(){
        Item sampleItem = new Item("name", "description", 1.99);

        repository.save(sampleItem)
                .as(StepVerifier::create)
                .expectNextMatches(item -> {
                    assertThat(item.getId()).isNotNull();
                    assertThat(item.getName()).isEqualTo("name");
                    assertThat(item.getDescription()).isEqualTo("description");
                    assertThat(item.getPrice()).isEqualTo(1.99);
                    return  true;
                })
                .verifyComplete();
    }

}
