package com.example.yatraniwas.startegy;

import com.example.yatraniwas.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
