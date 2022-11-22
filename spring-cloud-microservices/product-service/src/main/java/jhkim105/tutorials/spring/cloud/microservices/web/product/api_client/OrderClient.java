package jhkim105.tutorials.spring.cloud.microservices.web.product.api_client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "order-service", url = "http://localhost:8082", configuration = FeignConfig.class)
public interface OrderClient {

    @RequestMapping(value = "/orders", method = {RequestMethod.GET})
    List<Order> getAll();

}
