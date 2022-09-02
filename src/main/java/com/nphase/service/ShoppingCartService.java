package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShoppingCartService {
    public static final int SCALE = 2;
    private static final int QUANTITY_TO_DISCOUNT = 3;
    private static final BigDecimal REGULAR_COEFFICIENT = BigDecimal.ONE;
    private static final BigDecimal DISCOUNT = BigDecimal.ONE.scaleByPowerOfTen(-1);

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit()
                        .multiply(calculateDiscount(product))
                        .multiply(BigDecimal.valueOf(product.getQuantity()))
                )
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateDiscount(Product product) {
        return product.getQuantity() > QUANTITY_TO_DISCOUNT ? REGULAR_COEFFICIENT.subtract(DISCOUNT) : REGULAR_COEFFICIENT;
    }
}
