package za.co.myshop.controller;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.myshop.dto.PurchaseRequest;
import za.co.myshop.entity.Product;
import za.co.myshop.service.MyShopService;

import java.util.List;

@RestController
@RequestMapping("myshopify")
@Api(value = "/myshopify", tags = "My shopify services", description = "Get product and customer info")
public class MyShopController {

    @Autowired
    MyShopService service;

    @Timed(
            value = "Shopify.products.getall.request",
            histogram = true,
            percentiles = {0.95,0.99},
            extraTags = {"version","1.0"}
    )
    @GetMapping("/allproducts")
    public List getAllProducts() {
        final List<Product> allProducts = service.getAllProducts();
        return allProducts;
    }


    @Timed(
            value = "Shopify.checkout.request",
            histogram = true,
            percentiles = {0.95,0.99},
            extraTags = {"version","1.0"}
    )
    @PostMapping("/checkout")
    public ResponseEntity buyProduct(@RequestBody PurchaseRequest request) {
        try {

            final ResponseEntity responseEntity = service.purchaseProducts(request);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
