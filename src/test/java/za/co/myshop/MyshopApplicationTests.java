package za.co.myshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import za.co.myshop.entity.Customer;
import za.co.myshop.entity.Product;
import za.co.myshop.repository.CustomerRepository;
import za.co.myshop.repository.ProductRepository;
import za.co.myshop.service.MyShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
class MyshopApplicationTests {

	@Autowired
	MyShopService service;

	@MockBean
	ProductRepository productRepository;
	@MockBean
	CustomerRepository customerRepository;

	@Test
	@DisplayName("Test findAll products")
	public void testGetAllProducts() {
		List productList = new ArrayList<>();
		Product product = new Product(101L,"LPTP","Acer laptop",12);
		Product product2 = new Product(102L,"FRDG","Fridge LG",7);
		Product product3 = new Product(103L,"SHDS","ray ban",5);
		productList.add(product);
		productList.add(product2);
		productList.add(product3);

		doReturn(productList).when(productRepository).findAll();
		List<Product> allProducts = service.getAllProducts();
		Assertions.assertEquals(3,allProducts.size(),"findAll should return 3 products");
	}


	@Test
	@DisplayName("Test findProduct by code")
	public void testGetProductByCode() {
		List productList = new ArrayList<>();
		Product product = new Product(101L,"LPTP","Acer laptop",12);
		Product product2 = new Product(102L,"FRDG","Fridge LG",7);
		Product product3 = new Product(103L,"SHDS","Ray ban",5);
		productList.add(product);
		productList.add(product2);
		productList.add(product3);

		Optional<Product> prod = Optional.of(product3);
		doReturn(prod).when(productRepository).findByCode("SHDS");

		Product response = productRepository.findByCode("SHDS").get();
		Assertions.assertEquals(response.getName(),"Ray ban","response note the same as mocked object");
		Assertions.assertSame(product3,response,"the product returned was not the same as the mock");
	}

	@Test
	@DisplayName("Test find customer by id")
	public void testFindCustomersById() {
		final Customer customer = new Customer("22", "Simba", 20);
		when(customerRepository.findById("22")).thenReturn(Optional.of(customer));

		final Optional<Customer> customerResponse = customerRepository.findById("22");

		Assertions.assertTrue(customerResponse.isPresent(),"customer not found");

	}

	@Test
	@DisplayName("Test if customer points greater than total selected products points")
	public void testCustomerPointsGreaterThanProductPoints() {
		final Customer customer = new Customer("22", "Simba", 20);
		Product product = new Product(101L,"LPTP","Acer laptop",12);
		Product product2 = new Product(102L,"FRDG","Fridge LG",7);
		List<Product> products = new ArrayList<>();
		products.add(product);
		products.add(product2);
		final int sumOfProductPoints = products.stream().mapToInt(n -> n.getPointCost()).sum();

		Assertions.assertTrue(customer.getActiveDays()>sumOfProductPoints,"Customer does not have enough points");

	}









}
