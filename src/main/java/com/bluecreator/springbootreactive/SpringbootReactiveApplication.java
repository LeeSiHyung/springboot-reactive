package com.bluecreator.springbootreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class SpringbootReactiveApplication {

    public static void main(String[] args) {
        // BlockHound.install();
        BlockHound.builder()
                .allowBlockingCallsInside(
                        TemplateEngine.class.getCanonicalName(), "initialize"
                )
                .install();
        SpringApplication.run(SpringbootReactiveApplication.class, args);
    }

}
