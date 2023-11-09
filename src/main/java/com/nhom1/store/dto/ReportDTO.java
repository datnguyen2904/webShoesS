package com.nhom1.store.dto;

import java.math.BigDecimal;

public class ReportDTO {
    public String productName;
    public int qty;
    public BigDecimal orderTotal;

    public ReportDTO() {

    }

    public ReportDTO(String productName, int qty, BigDecimal orderTotal) {
        this.productName = productName;
        this.qty = qty;
        this.orderTotal = orderTotal;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

}
