package za.co.myshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import za.co.myshop.entity.Customer;
import za.co.myshop.entity.Product;
import za.co.myshop.repository.CustomerRepository;
import za.co.myshop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MyshopApplication implements CommandLineRunner {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(MyshopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List productList = new ArrayList();
        productList.add(new Product("LPTP","HP laptop", 10));
        productList.add(new Product("SNKR","Nike sneakers", 8));
        productList.add(new Product("WAT","Fitbit", 4));

        productList.forEach(n -> productRepository.save((Product) n));

        List customerList = new ArrayList();
        customerList.add(new Customer("2c91808278","Simba", 20));
        customerList.add(new Customer("2c91808288","Jake", 12));
        customerList.add(new Customer("2c91808270","John", 5));

        customerList.forEach(n -> customerRepository.save((Customer) n));

    }
}
