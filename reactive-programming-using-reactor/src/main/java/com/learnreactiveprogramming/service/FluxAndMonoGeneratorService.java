package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("kabil", "kl", "j"));
    }

    public Mono<String> nameMono() {
        return Mono.just("kabil");
    }

    public Flux<String> namesFlux_map(int stringLength) {
        return Flux.fromIterable(List.of("kabil", "kl", "j")).map(String::toUpperCase).filter(s -> s.length() > stringLength)
                //.map(s -> s.length() + "-" + s)
                .flatMap(s -> splitString(s)).log();
    }

    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        return Flux.fromIterable(List.of("kabil", "kl", "j")).map(String::toUpperCase).filter(s -> s.length() > stringLength).flatMap(s -> splitString_withDelay(s)).log();
    }

    public Flux<String> namesFlux_concatmap(int stringLength) {
        return Flux.fromIterable(List.of("kabil", "kl", "j")).map(String::toUpperCase).filter(s -> s.length() > stringLength).concatMap(s -> splitString_withDelay(s)).log();
    }

    public Mono<String> nameMono_map_filter(int stringLength) {
        return Mono.just("kabil").map(String::toUpperCase).filter(s -> s.length() > stringLength).log();
    }

    public Mono<List<String>> nameMono_flatmap(int stringLength) {
        return Mono.just("kabil").map(String::toUpperCase).filter(s -> s.length() > stringLength).flatMap(this::splitStringMono).log();
    }

    public Flux<String> nameMono_flatmapmany(int stringLength) {
        return Mono.just("kabil").map(String::toUpperCase).filter(s -> s.length() > stringLength).flatMapMany(this::splitString).log();
    }

    public Flux<String> namesFlux_transform(int stringLength) {
        Function<Flux<String>, Flux<String>> filtermap = name -> name.map(String::toUpperCase).filter(s -> s.length() > stringLength);

        return Flux.fromIterable(List.of("kabil", "kl", "j")).transform(filtermap).flatMap(s -> splitString(s)).defaultIfEmpty("default").log();
    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength) {
        Function<Flux<String>, Flux<String>> filtermap = name -> name.map(String::toUpperCase).filter(s -> s.length() > stringLength).flatMap(s -> splitString(s));

        var defaultFlux = Flux.just("default").transform(filtermap);

        return Flux.fromIterable(List.of("kabil", "kl", "j")).transform(filtermap).switchIfEmpty(defaultFlux).log();
    }

    public Flux<String> explore_concat() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> explore_concatwith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.concatWith(defFlux).log();
    }

    public Flux<String> explore_zip() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.zip(abcFlux,defFlux,(first,second) -> first + second).log();
    }

    public Flux<String> explore_merge() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));;
        return Flux.merge(abcFlux, defFlux).log();
    }

    private Mono<List<String>> splitStringMono(String name) {
        var charArray = name.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitString_withDelay(String name) {
        var charArray = name.split("");
        //var delay = new Random().nextInt(1000);
        var delay = 1000;
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(delay));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux().subscribe(name -> {
            System.out.println("Flux Name is : " + name);
        });
        fluxAndMonoGeneratorService.nameMono().subscribe(name -> {
            System.out.println("Mono Name is : " + name);
        });
    }
}
