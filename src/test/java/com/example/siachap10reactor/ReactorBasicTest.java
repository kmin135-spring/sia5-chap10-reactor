package com.example.siachap10reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class ReactorBasicTest {
    @Test
    public void createAFluxJust() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

        fruitFlux.subscribe(f -> System.out.println("fruit : " + f));

        StepVerifier.create(fruitFlux)
                .expectNext("Apple", "Orange", "Grape", "Banana", "Strawberry")
                .verifyComplete();
    }

    @Test
    public void createAFluxFromArray() {
        String[] fruits = {"Apple", "Orange", "Grape", "Banana", "Strawberry"};

        Flux<String> fruitFlux = Flux.fromArray(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple", "Orange", "Grape", "Banana", "Strawberry")
                .verifyComplete();
    }

    @Test
    public void createAFluxRange() {
        Flux<Integer> rangeFlux = Flux.range(1, 5);

        StepVerifier.create(rangeFlux)
                .expectNext(1,2,3,4,5)
                .verifyComplete();
    }

    @Test
    public void createAFluxInterval() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1L))
                .take(3);

        StepVerifier.create(intervalFlux)
                .expectNext(0L,1L,2L)
                .verifyComplete();
    }

    @Test
    public void zipFluxes() {
        Flux<String> characterFlux = Flux.just("c1", "c2", "c3");
        Flux<String> foodFlux = Flux.just("f1", "f2", "f3");

        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

        StepVerifier.create(zippedFlux)
                .expectNextMatches(p -> p.getT1().equals("c1") && p.getT2().equals("f1"))
                .expectNextMatches(p -> p.getT1().equals("c2") && p.getT2().equals("f2"))
                .expectNextMatches(p -> p.getT1().equals("c3") && p.getT2().equals("f3"))
                .verifyComplete();
    }

    @Test
    public void filter() {
        Flux<String> flux = Flux.just("a b", "123", "zz")
                .filter(p -> !p.contains(" "));

        StepVerifier.create(flux)
                .expectNext("123", "zz")
                .verifyComplete();
    }

    @Test
    public void distinct() {
        Flux<String> flux = Flux.just("d1", "d2", "d1", "d3", "d3")
                .distinct();

        StepVerifier.create(flux)
                .expectNext("d1", "d2", "d3")
                .verifyComplete();
    }
}
