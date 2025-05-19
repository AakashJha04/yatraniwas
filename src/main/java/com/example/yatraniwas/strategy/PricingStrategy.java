package com.example.yatraniwas.strategy;

import com.example.yatraniwas.entity.Inventory;

import java.math.BigDecimal;
public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
