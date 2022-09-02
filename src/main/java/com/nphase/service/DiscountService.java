package com.nphase.service;

import com.nphase.entity.enums.Category;
import com.nphase.entity.enums.Discount;

public interface DiscountService {

    Discount getDiscountByCategory(Category category);
}
