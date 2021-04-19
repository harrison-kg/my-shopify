package za.co.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.myshop.entity.Customer;

import javax.transaction.Transactional;
/**
 * updates db table Customer after purchase request has been validated and require processing
 * @param cusID a unique customer identifier
 * @Param updatedDays  days needed to process
 * @see Customer
 * **/
@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer,String> {
    @Modifying
    @Query("update Customer cus set cus.activeDays = :updatedDays where cus.id= :cusID")
    void updateActiveDaysByCustomerId(@Param("cusID") String cusID,@Param("updatedDays") int updatedDays);
}
