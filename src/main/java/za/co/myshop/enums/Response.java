package za.co.myshop.enums;

public enum Response {
    UNKNOWN_CUSTOMER_ID("The customer’s ID does not exist in the store."),
    UNKNOWN_PRODUCT_ID("The customer chose a non-existent product code ("),
    NULL_PRODUCTS("The customer did not provide any products to purchase."),
    INSUFFICIENT_POINTS("The customer does not have enough points.");

    String status;

    Response(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
