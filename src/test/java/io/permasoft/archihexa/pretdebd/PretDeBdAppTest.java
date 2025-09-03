package io.permasoft.archihexa.pretdebd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class PretDeBdAppTest {
    @Test
    void shouldPass() {
        new PretDeBdApp();
        assertThat(true).isTrue();
    }

    @Test
    void shouldFail() {
        assertThat(false).isTrue();
    }

    @Test
    void shouldRun() {
        assertThatCode(() -> new PretDeBdApp().run("Hello", "World")).doesNotThrowAnyException();
    }
}
