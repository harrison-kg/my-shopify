package za.co.myshop.cuccumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;
import za.co.myshop.entity.Customer;
import za.co.myshop.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StepDefinition extends CucumberSpringConfiguration {

    List<Product> response = new ArrayList<>();
    Product product;
    ResponseEntity responseEntity = null;
    ResponseEntity responseEntity2= null;
    Customer customer = null;

    @When("client calls \\/myshopify\\/allproducts")
    public void getAllProducts() throws Throwable {
        response = getAllProducts("/myshopify/allproducts");
    }

    @Then("the client receives the response object size of {int}")
    public void the_client_receives_response_object_size_of(Integer size) throws Throwable {

        Assertions.assertEquals(size, response.size());
    }

    @When("client searches for a product using product code {word}")
    public void find_product_by_code(String code) {
        product = getProductByCode(code);
    }

    @Then("the service will return the product: {string}")
    public void service_will_return_product(String name) {
        Assertions.assertEquals(product.getName(), name);
    }

    @Given("customer 1 has 20 active days")
    public void getCustomer1with20days() {
        customer = getCustomer(0);
    }

    @When("customer request to purchase product item {word} and {word}")
    public void customer_request_to_purchase_product(String code1, String code2) {
        List products = new ArrayList();
        products.add(code1);
        products.add(code2);
        responseEntity = purchaseProducts(products, customer);
    }

    @Then("Client receives status {string}")
    public void service_will_return_200OK(String statusCode) {
        Assertions.assertEquals(responseEntity.getStatusCode().toString(), statusCode);
    }
}
