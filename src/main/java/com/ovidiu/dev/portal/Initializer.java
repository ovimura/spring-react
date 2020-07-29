package com.ovidiu.dev.portal;

import com.ovidiu.dev.portal.model.Meeting;
import com.ovidiu.dev.portal.model.Provider;
import com.ovidiu.dev.portal.model.ProviderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final ProviderRepository repository;

    public Initializer(ProviderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Thomas Dudrey, MD", "Dan Rodgers, MD", "Amelia McConaghy, DO",
                "Laura Sage, MD").forEach(name ->
                repository.save(new Provider(name))
        );

        Provider djug = repository.findByName("Thomas Dudrey, MD");
        Meeting e = Meeting.builder().title("Initial Visit")
                .description("General Examination")
                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
                .build();
        djug.setMeetings(Collections.singleton(e));
        repository.save(djug);

        repository.findAll().forEach(System.out::println);
    }
}
