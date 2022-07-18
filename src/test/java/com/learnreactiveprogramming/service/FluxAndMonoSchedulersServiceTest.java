package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoSchedulersServiceTest {

    FluxAndMonoSchedulersService fluxAndMonoSchedulersService = new FluxAndMonoSchedulersService();

    @Test
    void explore_publishOn() {
        var flux = fluxAndMonoSchedulersService.explore_publishOn();

        StepVerifier.create(flux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void explore_subscribeOn() {
        var flux = fluxAndMonoSchedulersService.explore_subscribeOn();

        StepVerifier.create(flux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void explore_parallel() {

        var flux = fluxAndMonoSchedulersService.explore_parallel();

        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void explore_parallel_usingFlatMap() {
        var flux = fluxAndMonoSchedulersService.explore_parallel_usingFlatMap();

        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void explore_parallel_usingFlatMap_1() {
        var flux = fluxAndMonoSchedulersService.explore_parallel_usingFlatMap_1();

        StepVerifier.create(flux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void explore_parallel_usingFlatMapSequenctial() {
        var flux = fluxAndMonoSchedulersService.explore_parallel_usingFlatMapSequenctial();

        StepVerifier.create(flux)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }
}