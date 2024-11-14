package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class URLUtilsTest {

    @Test
    void test() {
        assertThat(URLUtils.getBaseUrl("https://abc.com/a/b/c?a==bc")).isEqualTo("https://abc.com");
    }

}