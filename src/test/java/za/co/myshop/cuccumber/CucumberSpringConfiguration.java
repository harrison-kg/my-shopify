package za.co.myshop.cuccumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import za.co.myshop.dto.PurchaseRequest;
import za.co.myshop.entity.Customer;
import za.co.myshop.entity.Product;
import za.co.myshop.repository.CustomerRepository;
import za.co.myshop.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberSpringConfiguration {
    @Autowired
    ProductRepository repository;
    @Autowired
    CustomerRepository customerRepo;

    protected RestTemplate restTemplate = new RestTemplate();

    Customer customer = null;
    protected final String DEFAULT_URL = "http://localhost:8084/";
    HttpEntity<Product> entity = new HttpEntity<Product>(null, new HttpHeaders());

    List<Product> getAllProducts(String uri) {
        ResponseEntity response = restTemplate.exchange(
                createURLWithPort(uri), HttpMethod.GET, entity, List.class);
        return (List<Product>) response.getBody();
    }


    Product getProductByCode(String code) {
        final Optional<Product> response = repository.findByCode(code);
        return response.get();
    }

    private String createURLWithPort(String uri) {
        return DEFAULT_URL + uri;
    }

    Customer getCustomer(int index) {
        final Iterable<Customer> all = customerRepo.findAll();
        final List<Customer> result = StreamSupport.stream(all.spliterator(), false)
                .collect(Collectors.toList());
        final Customer customer1 = result.get(index);

        return customer1;
    }


    ResponseEntity purchaseProducts(List<String> products,Customer customer) {
        PurchaseRequest request = new PurchaseRequest();
        request.setId(customer.getId());
        request.setPrdCode(products);
        HttpEntity<String> entity = new HttpEntity(request, new HttpHeaders());


        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/myshopify/checkout"), HttpMethod.POST, entity, String.class);

        return response;
    }
}
