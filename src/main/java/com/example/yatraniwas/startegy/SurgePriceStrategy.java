package com.example.yatraniwas.startegy;
import com.example.yatraniwas.entity.Inventory;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePriceStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
       BigDecimal price = wrapped.calculatePrice(inventory);
       return price.multiply(inventory.getSurgeFactor());
    }
}
