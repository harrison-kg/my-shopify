package za.co.myshop.integrationtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.co.myshop.dto.PurchaseRequest;
import za.co.myshop.entity.Customer;
import za.co.myshop.entity.Product;
import za.co.myshop.repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyShopifyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    CustomerRepository customerRepo;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();


    @Test
    public void testRetrieveCustomersSize() throws Exception {
        HttpEntity<Product> entity = new HttpEntity<Product>(null, headers);

        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/myshopify/allproducts"), HttpMethod.GET, entity, List.class);
        final List responseBody = (List<Product>) response.getBody();


        int expectedListNumber = 3;
        Assertions.assertEquals(expectedListNumber, responseBody.size());
    }

    @Test
    @DisplayName("Testing a customer with not enough points")
    public void testCustomerWithLessPointsThanProductsToPurchase() throws Exception {
        PurchaseRequest request = new PurchaseRequest();
        final Iterable<Customer> all = customerRepo.findAll();
        List<Customer> result = StreamSupport.stream(all.spliterator(), false)
                .collect(Collectors.toList());
        final Customer John = result.get(2);  //customer 3 with 5 points
        String productCodes[] = {"SNKR", "LPTP"};
        List codes = Arrays.asList(productCodes);
        request.setId(John.getId());
        request.setPrdCode(codes);
        HttpEntity<String> entity = new HttpEntity(request, headers);


        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/myshopify/checkout"), HttpMethod.POST, entity, String.class);
        final String responseBody = (String) response.getBody();


        int expectedListNumber = 3;
        Assertions.assertEquals("The customer does not have enough points.", responseBody);
    }


    @Test
    @DisplayName("Testing a customer with  enough points to purchase seleted products")
    public void testCustomerWithMorePointsThanProductsToPurchase() throws Exception {
        PurchaseRequest request = new PurchaseRequest();
        final Iterable<Customer> all = customerRepo.findAll();
        List<Customer> result = StreamSupport.stream(all.spliterator(), false)
                .collect(Collectors.toList());
        final Customer Simba = result.get(0);  //customer 1 with 20 points
        String productCodes[] = {"SNKR", "LPTP"};
        List codes = Arrays.asList(productCodes);
        request.setId(Simba.getId());
        request.setPrdCode(codes);
        HttpEntity<String> entity = new HttpEntity(request, headers);


        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/myshopify/checkout"), HttpMethod.POST, entity, String.class);
        final String responseBody = response.getStatusCode().toString();

        Assertions.assertEquals("200 OK", responseBody);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    @DisplayName("Testing a customer which does not exist in the database")
    public void testNoneExistingCustomer() throws Exception {
        PurchaseRequest request = new PurchaseRequest();
        String noneExistingCustomerId = "22h34h5h667h7h8";
        //customer 3 with 5 points
        String productCodes[] = {"SNKR", "LPTP"};
        List codes = Arrays.asList(productCodes);
        request.setId(noneExistingCustomerId);
        request.setPrdCode(codes);
        HttpEntity<String> entity = new HttpEntity(request, headers);


        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/myshopify/checkout"), HttpMethod.POST, entity, String.class);
        final String responseBody = response.getBody().toString();

        Assertions.assertEquals("The customer’s ID does not exist in the store.", responseBody);
    }

    @Test
    @DisplayName("Testing a none existing product among the selected products to purchase")
    public void testNoneExistingProductByCode() throws Exception {
        PurchaseRequest request = new PurchaseRequest();
        final Iterable<Customer> all = customerRepo.findAll();
        List<Customer> result = StreamSupport.stream(all.spliterator(), false)
                .collect(Collectors.toList());
        final Customer Simba = result.get(0);  //customer 1 with 20 points
        String productCodes[] = {"SNKR", "LOTTO"};  //LOTTO PRODUCT CODE DOES NOT EXIST
        List codes = Arrays.asList(productCodes);
        request.setId(Simba.getId());
        request.setPrdCode(codes);
        HttpEntity<String> entity = new HttpEntity(request, headers);


        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/myshopify/checkout"), HttpMethod.POST, entity, String.class);
        final String responseBody = response.getBody().toString();

        Assertions.assertEquals("The customer chose a non-existent product code (LOTTO)", responseBody);
    }


    @Test
    @DisplayName("Testing missing product codes")
    public void tesNullProducts() throws Exception {
        PurchaseRequest request = new PurchaseRequest();
        final Iterable<Customer> all = customerRepo.findAll();
        List<Customer> result = StreamSupport.stream(all.spliterator(), false)
                .collect(Collectors.toList());
        final Customer Simba = result.get(0);  //customer 3 with 5 points
        request.setId(Simba.getId());
        HttpEntity<String> entity = new HttpEntity(request, headers);

        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/myshopify/checkout"), HttpMethod.POST, entity, String.class);
        final String responseBody = response.getBody().toString();

        Assertions.assertEquals("The customer did not provide any products to purchase.", responseBody);
    }

}

