package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ShoppingCartService {

    public static final int SCALE = 2;
    private static final int QUANTITY_TO_DISCOUNT = 3;
    private static final BigDecimal REGULAR_COEFFICIENT = BigDecimal.ONE;
    private static final BigDecimal DISCOUNT = BigDecimal.ONE.scaleByPowerOfTen(-1);

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory, toList()))
                .values().parallelStream()
                .flatMap((categorizedProducts) -> categorizedProducts.stream().map(product -> product.getPricePerUnit()
                                .multiply(calculateDiscount(getTotalQuantity(categorizedProducts)))
                                .multiply(BigDecimal.valueOf(product.getQuantity()))
                        ))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

    private int getTotalQuantity(List<Product> products) {
        return products.stream().mapToInt(Product::getQuantity).sum();
    }

    public BigDecimal calculateDiscount(int quantity) {
        return quantity > QUANTITY_TO_DISCOUNT ? REGULAR_COEFFICIENT.subtract(DISCOUNT) : REGULAR_COEFFICIENT;
    }
}
