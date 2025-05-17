package com.example.yatraniwas.startegy;
import com.example.yatraniwas.entity.Inventory;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class OccupancyStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal currPrice = wrapped.calculatePrice(inventory);
        double occupancyRate = (double) inventory.getBookCount()/inventory.getTotalCount();
        if(occupancyRate>0.8){ // >80%
            currPrice = currPrice.multiply(BigDecimal.valueOf(1.2));
        }
        return currPrice;
    }
}
