package com.nphase.entity.enums;

import java.math.BigDecimal;

public class Discount {
    private Category category;
    private int quantity;
    private BigDecimal discount;

    public Discount(Category category, int quantity, BigDecimal discount) {
        this.category = category;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
