package com.example.yatraniwas.startegy;
import com.example.yatraniwas.entity.Inventory;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isTodayHoliday = true; // call an API or check with LocalDate
        if(isTodayHoliday){
            price = price.multiply(BigDecimal.valueOf(2.0));
        }
        return price;
    }
}
