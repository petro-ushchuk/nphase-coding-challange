package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.entity.enums.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, Category.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, Category.DRINKS),
                new Product("Cheese", BigDecimal.valueOf(8), 2, Category.FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(
                BigDecimal.valueOf(31.84).setScale(ShoppingCartService.SCALE, RoundingMode.HALF_UP),
                result
        );
    }

}