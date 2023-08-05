package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void nameFlux() {
        var nameFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(nameFlux)
                .expectNext("kabil")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        int stringLength = 3;
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);

        StepVerifier.create(nameFlux)
                .expectNext("5-KABIL")
                //.expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testNamesFlux_map() {
        int stringLength = 3;
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);

        StepVerifier.create(nameFlux)
                .expectNext("K","A","B","I","L")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        int stringLength = 3;
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength);

        StepVerifier.create(nameFlux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        int stringLength = 3;
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);

        StepVerifier.create(nameFlux)
                .expectNext("K","A","B","I","L")
                .verifyComplete();
    }

    @Test
    void namesMono_flatmap() {
        int stringLength = 3;
        var nameMono = fluxAndMonoGeneratorService.nameMono_flatmap(stringLength);

        StepVerifier.create(nameMono)
                .expectNext(List.of("K","A","B","I","L"))
                .verifyComplete();
    }

    @Test
    void nameMono_flatmapmany() {
        int stringLength = 3;
        var nameMono = fluxAndMonoGeneratorService.nameMono_flatmapmany(stringLength);

        StepVerifier.create(nameMono)
                .expectNext("K","A","B","I","L")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        int stringLength = 3;
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        StepVerifier.create(nameFlux)
                .expectNext("K","A","B","I","L")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_switchIfEmpty() {
        int stringLength = 6;
        var nameFlux = fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(stringLength);

        StepVerifier.create(nameFlux)
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        var nameFlux = fluxAndMonoGeneratorService.explore_concatwith();

        StepVerifier.create(nameFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        var nameFlux = fluxAndMonoGeneratorService.explore_merge();

        StepVerifier.create(nameFlux)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }

    @Test
    void explore_zip() {
        var nameFlux = fluxAndMonoGeneratorService.explore_zip();

        StepVerifier.create(nameFlux)
                .expectNext("AD","BE","CF")
                .verifyComplete();
    }
}
