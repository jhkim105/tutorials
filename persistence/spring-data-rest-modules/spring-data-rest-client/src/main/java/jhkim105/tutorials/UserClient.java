package jhkim105.tutorials;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users", url = "http://localhost:8080/data")
public interface UserClient {

    @GetMapping("/users")
    CollectionModel<User> getAll();

    @GetMapping("/users/{id}")
    User getOne(@PathVariable String id);

}
