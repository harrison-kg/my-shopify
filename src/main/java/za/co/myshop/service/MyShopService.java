package za.co.myshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.myshop.enums.Response;
import za.co.myshop.dto.PurchaseRequest;
import za.co.myshop.entity.Customer;
import za.co.myshop.entity.Product;
import za.co.myshop.repository.CustomerRepository;
import za.co.myshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MyShopService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepo;

    public List<Product> getAllProducts() {
        final List<Product> productList = (List<Product>) productRepository.findAll();
        return productList;
    }

    public ResponseEntity purchaseProducts(PurchaseRequest request) {
        int cost = 0;

            if (request.getId() != null) {
                final Optional<Customer> customer = customerRepo.findById(request.getId());
                if (customer.isPresent()) {
                    if (request.getPrdCode() != null && !request.getPrdCode().isEmpty()) {
                        for (String code : request.getPrdCode()) {
                            final Optional<Product> byCode = productRepository.findByCode(code);
                            if (byCode.isPresent()) {
                                cost += byCode.get().getPointCost();
                            } else {
                                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.UNKNOWN_PRODUCT_ID.getStatus() + code + ")");
                            }
                        }
                        if (customer.get().getActiveDays() >= cost) {
                            int netActive= customer.get().getActiveDays() - cost;
                            customerRepo.updateActiveDaysByCustomerId(customer.get().getId(), netActive);
                            return ResponseEntity.status(HttpStatus.OK).build();

                        }
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.INSUFFICIENT_POINTS.getStatus());
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NULL_PRODUCTS.getStatus());
                    }
                }
            }
             return  ResponseEntity.status(HttpStatus.NOT_FOUND).body( Response.UNKNOWN_CUSTOMER_ID.getStatus());
    }
}


