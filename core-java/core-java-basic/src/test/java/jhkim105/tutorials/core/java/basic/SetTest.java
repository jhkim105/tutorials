package jhkim105.tutorials.core.java.basic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class SetTest {


    @Test
    void convertObjectSetToMap() {
        var set = Set.of(new User("id01", "name01"),
            new User("id02", "name02"));
        var map = set.stream().collect(Collectors.toMap(User::id, User::name));
        assertThat(map.toString()).isEqualTo("{id02=name02, id01=name01}");
    }

    record User(String id, String name) {
    }


    @Test
    void convertStringSetToMap() {
        var set = Set.of("id01", "id02");
        var map = set.stream().collect(Collectors.toMap(Function.identity(), String::length));
        assertThat(map.toString()).isEqualTo("{id02=4, id01=4}");
    }
}
