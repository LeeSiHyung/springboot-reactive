package com.bluecreator.springbootreactive.config;

import com.bluecreator.springbootreactive.domain.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class TemplateDatabaseLoader {
    @Bean
    CommandLineRunner initialize(MongoOperations mongoOperations){
        return args -> {
            mongoOperations.save(new Item("Alf alrm clock", 19.99));
            mongoOperations.save(new Item("Smurf Tv tray", 24.99));
        };
    }
}
