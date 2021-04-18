package za.co.myshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private int pointCost;

    public Product(String code, String name, int pointCost) {
        this.code = code;
        this.name = name;
        this.pointCost = pointCost;
    }

    public Product(String name, int pointCost) {
        this.name = name;
        this.pointCost = pointCost;
    }

    public Product() {
    }

    public Product(Long id, String code, String name, int pointCost) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.pointCost = pointCost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPointCost() {
        return pointCost;
    }

    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }
}
