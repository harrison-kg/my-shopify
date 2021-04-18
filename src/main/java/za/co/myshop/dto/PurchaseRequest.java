package za.co.myshop.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
public class PurchaseRequest {
    String id;
    List<String> prdCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(List<String> prdCode) {
        this.prdCode = prdCode;
    }
}
