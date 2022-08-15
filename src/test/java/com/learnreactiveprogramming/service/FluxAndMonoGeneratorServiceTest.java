package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.exception.ReactorException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;
import reactor.tools.agent.ReactorDebugAgent;

import java.time.Duration;
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
    void namesFlux_concatmap_virtualTimer() {
        VirtualTimeScheduler.getOrSet();

        var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(3);
        StepVerifier.withVirtualTime(() -> namesFlux)
                .thenAwait(Duration.ofSeconds(10))
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

    @Test
    void explore_concat() {
        var concatFllux = fluxAndMonoGeneratorService.explore_concat();
        StepVerifier.create(concatFllux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_concatWith() {
        var concatFllux = fluxAndMonoGeneratorService.explore_concatWith();
        StepVerifier.create(concatFllux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_concatWith_mono() {
        var concatFllux = fluxAndMonoGeneratorService.explore_concatWith_mono();
        StepVerifier.create(concatFllux)
                .expectNext("A","B")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        var concatFllux = fluxAndMonoGeneratorService.explore_merge();
        StepVerifier.create(concatFllux)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }

    @Test
    void explore_mergeWith() {
        var concatFllux = fluxAndMonoGeneratorService.explore_mergeWith();
        StepVerifier.create(concatFllux)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }

    @Test
    void explore_mergeWith_mono() {
        var concatFllux = fluxAndMonoGeneratorService.explore_mergeWith_mono();
        StepVerifier.create(concatFllux)
                .expectNext("A","B")
                .verifyComplete();
    }

    @Test
    void explore_mergeSequential() {
        var concatFllux = fluxAndMonoGeneratorService.explore_mergeSequential();
        StepVerifier.create(concatFllux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_zip() {
        var zipFlux = fluxAndMonoGeneratorService.explore_zip();
        StepVerifier.create(zipFlux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_zip_1() {
        var zipFlux = fluxAndMonoGeneratorService.explore_zip_1();
        StepVerifier.create(zipFlux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void explore_zipWith() {
        var zipFlux = fluxAndMonoGeneratorService.explore_zipWith();
        StepVerifier.create(zipFlux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_zipWith_mono() {
        var zipFlux = fluxAndMonoGeneratorService.explore_zipWith_mono();
        StepVerifier.create(zipFlux)
                .expectNext("AB")
                .verifyComplete();
    }

    @Test
    void exception_flux() {
        var value = fluxAndMonoGeneratorService.exception_flux();

        StepVerifier.create(value)
                .expectNext("A","B","C")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void exception_flux_1() {
        var value = fluxAndMonoGeneratorService.exception_flux();

        StepVerifier.create(value)
                .expectNext("A","B","C")
                .expectError()
                .verify();
    }

    @Test
    void exception_flux_2() {
        var value = fluxAndMonoGeneratorService.exception_flux();

        StepVerifier.create(value)
                .expectNext("A","B","C")
                .expectErrorMessage("Exception occurred")
                .verify();
    }

    @Test
    void explore_OnErrorReturn() {

        var value = fluxAndMonoGeneratorService.explore_OnErrorReturn();

        StepVerifier.create(value)
                .expectNext("A","B","C","D")
                .verifyComplete();
    }

    @Test
    void explore_OnErrorResume() {
        var e = new IllegalStateException("Not a valid State");

        var value = fluxAndMonoGeneratorService.explore_OnErrorResume(e);

        StepVerifier.create(value)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_OnErrorResume_1() {
        var e = new RuntimeException("Not a valid State");

        var value = fluxAndMonoGeneratorService.explore_OnErrorResume(e);

        StepVerifier.create(value)
                .expectNext("A","B","C")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void explore_OnErrorContinue() {
        var value = fluxAndMonoGeneratorService.explore_OnErrorContinue();

        StepVerifier.create(value)
                .expectNext("A", "C", "D")
                .verifyComplete();
    }

    @Test
    void explore_OnErrorMap() {
        var e = new RuntimeException("Not a valid state");
        var value = fluxAndMonoGeneratorService.explore_OnErrorMap(e);

        StepVerifier.create(value)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }

    @Test
    void explore_OnErrorMap_onOperatorDebug() {
        //Hooks.onOperatorDebug();

        var e = new RuntimeException("Not a valid state");
        var value = fluxAndMonoGeneratorService.explore_OnErrorMap(e);

        StepVerifier.create(value)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }

    @Test
    void explore_OnErrorMap_reactorDebugAgent() {
        //Hooks.onOperatorDebug();

        ReactorDebugAgent.init();
        ReactorDebugAgent.processExistingClasses();
        var e = new RuntimeException("Not a valid state");
        var value = fluxAndMonoGeneratorService.explore_OnErrorMap(e);

        StepVerifier.create(value)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }

    @Test
    void explore_doOnError() {
        var value = fluxAndMonoGeneratorService.explore_doOnError();
        StepVerifier.create(value)
                .expectNext("A","B","C")
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void explore_Mono_doErrorReturn() {
        var value = fluxAndMonoGeneratorService.explore_Mono_doErrorReturn();
        StepVerifier.create(value)
                .expectNext("abc")
                .verifyComplete();
    }

    @Test
    void explore_generate() {
        var flux = fluxAndMonoGeneratorService.explore_generate().log();

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void explore_create() {
        var flux = fluxAndMonoGeneratorService.explore_create().log();

        StepVerifier.create(flux)
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void explore_create_mono() {
        var mono = fluxAndMonoGeneratorService.explore_create_mono().log();
        StepVerifier.create(mono)
                .expectNext("alex")
                .verifyComplete();
    }

    @Test
    void explore_handle() {
        var flux = fluxAndMonoGeneratorService.explore_handle().log();

        StepVerifier.create(flux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesMono_map_filter() {
        var mono = fluxAndMonoGeneratorService.namesMono_map_filter(3);

        StepVerifier.create(mono)
                .expectNext("ALEX")
                .verifyComplete();
    }
}
