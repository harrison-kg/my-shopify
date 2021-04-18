package za.co.myshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.myshop.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    public Optional<Product> findByCode(String code);
}
