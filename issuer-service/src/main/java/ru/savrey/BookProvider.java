package ru.savrey;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.savrey.api.Book;

import java.util.UUID;

@Service
public class BookProvider {

    // HttpClient - java.net
    // RestTemplate - spring.web
    // WebClient - spring.reactive

    private final WebClient webClient;
//  private final EurekaClient eurekaClient;


    public BookProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
        // this.eurekaClient = eurekaClient;
    }
    public Book getRandomBook() {
        return webClient.get()
                .uri("http://book-service/api/book/random")
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }

    // round robbin
//  private String getBookServiceIp() {
//    Application application = eurekaClient.getApplication("BOOK-SERVICE");
//    List<InstanceInfo> instances = application.getInstances();
//
//    int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
//    InstanceInfo randomInstance = instances.get(randomIndex);
//    return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
//  }
}
