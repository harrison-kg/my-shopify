package za.co.myshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "active_days")
    private int activeDays;

    public Customer() {
    }

    public Customer(String name, int activeDays) {
        this.name = name;
        this.activeDays = activeDays;
    }

    public Customer(String id, String name, int activeDays) {
        this.id = id;
        this.name = name;
        this.activeDays = activeDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(int activeDays) {
        this.activeDays = activeDays;
    }
}
