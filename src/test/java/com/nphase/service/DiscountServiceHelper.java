package com.nphase.service;

import com.nphase.entity.enums.Category;
import com.nphase.entity.enums.Discount;

import java.math.BigDecimal;

public class DiscountServiceHelper implements DiscountService{
    @Override
    public Discount getDiscountByCategory(Category category) {
        return switch (category) {
            case FOOD -> new Discount(Category.FOOD, 4, BigDecimal.valueOf(1));
            case DRINKS -> new Discount(Category.DRINKS, 10, BigDecimal.valueOf(0.1));
        };
    }
}
