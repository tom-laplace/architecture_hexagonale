package io.permasoft.archihexa.pretdebd;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class PretDeBdApp {
    public void run(String... args) {
        System.out.println(Arrays.stream(args).collect(joining(" ")));
    }
}
