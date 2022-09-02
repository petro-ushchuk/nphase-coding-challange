package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.entity.enums.Discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ShoppingCartService {

    private final DiscountService discountService;

    public static final int SCALE = 2;
    private static final int QUANTITY_TO_DISCOUNT = 3;
    private static final BigDecimal REGULAR_COEFFICIENT = BigDecimal.ONE;

    public ShoppingCartService(DiscountService discountService) {
        this.discountService = discountService;
    }

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return
                shoppingCart.getProducts()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory))
                        .entrySet()
                        .stream()
                        .map(categoryListEntry -> Map.entry(discountService.getDiscountByCategory(categoryListEntry.getKey()), categoryListEntry.getValue()))
                        .flatMap(discountListEntry -> discountListEntry.getValue()
                                .stream().map(product -> product.getPricePerUnit()
                                .multiply(calculateDiscount(getTotalQuantity(discountListEntry.getValue()), discountListEntry.getKey())
                                .multiply(BigDecimal.valueOf(product.getQuantity()))
                        )))
                        .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

    private int getTotalQuantity(List<Product> products) {
        return products.stream().mapToInt(Product::getQuantity).sum();
    }

    public BigDecimal calculateDiscount(int quantity, Discount discount) {
        return quantity > discount.getQuantity() ? REGULAR_COEFFICIENT.subtract(discount.getDiscount()) : REGULAR_COEFFICIENT;
    }
}
