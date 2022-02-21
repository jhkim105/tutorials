package jhkim105.tutorials.spring.cloud.openfeign.client;


import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "json-place-holder-client", url = "https://jsonplaceholder.typicode.com/")
public interface JsonPlaceHolderClient {

  @RequestMapping(method = RequestMethod.GET, value = "/posts")
  List<Post> getPosts();

  @RequestMapping(method = RequestMethod.GET, value = "/posts/{id}")
  Post getPost(@PathVariable String id);
}
