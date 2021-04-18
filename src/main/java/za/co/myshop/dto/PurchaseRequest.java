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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PurchaseRequest{");
        sb.append("id='").append(id).append('\'');
        sb.append(", prdCode=").append(prdCode);
        sb.append('}');
        return sb.toString();
    }
}
