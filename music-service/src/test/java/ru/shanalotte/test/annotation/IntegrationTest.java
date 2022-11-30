package ru.shanalotte.test.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("it")
@Test
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
}
