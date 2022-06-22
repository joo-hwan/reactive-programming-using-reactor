package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {

        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(namesFlux)
                //.expectNext("alex", "ben", "chloe")
                //.expectNextCount(3)
                .expectNext("alex")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
        var nameFluxImmutability = fluxAndMonoGeneratorService.namesFlux_immutability();

        StepVerifier.create(nameFluxImmutability)
                //.expectNext("ALEX", "BEN", "CHLOE")
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(3);
        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();

    }

    @Test
    void namesFlux_flatmap_async() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(3);
        StepVerifier.create(namesFlux)
                //.expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .expectNextCount(9)
                .verifyComplete();

    }

    @Test
    void namesFlux_concatmap() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(3);
        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                //.expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesMono_flatMap() {
        var value = fluxAndMonoGeneratorService.namesMono_flatMap(3);

        StepVerifier.create(value)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void namesMono_flatMapMany() {
        var value = fluxAndMonoGeneratorService.namesMono_flatMapMany(3);

        StepVerifier.create(value)
                .expectNext("A", "L", "E", "X")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(3);
        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_1() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(6);
        StepVerifier.create(namesFlux)
                //.expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_switchifEmpty() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform_switchifEmpty(6);
        StepVerifier.create(namesFlux)
                //.expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }
}
