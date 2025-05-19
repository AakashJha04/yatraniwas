package com.example.yatraniwas.service;

import com.example.yatraniwas.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);

}
