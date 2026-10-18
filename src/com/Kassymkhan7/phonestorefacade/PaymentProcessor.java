package com.Kassymkhan7.phonestorefacade;

import java.util.Map;

// Subsystem class: knows nothing about InventoryService, ShippingService, or any Facade.
public class PaymentProcessor {

    private static final Map<String, Double> PRICE_BY_MODEL = Map.of(
            "Galaxy S20 Ultra 5G", 950.00,
            "Galaxy A50", 280.00
    );

    public double getPrice(String model) {
        Double price = PRICE_BY_MODEL.get(model);
        if (price == null) {
            throw new IllegalArgumentException("No price configured for model: " + model);
        }
        return price;
    }

    // Returns a transaction ID on success; throws if the card is declined.
    public String charge(String cardNumber, double amount) {
        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("Card number must be provided.");
        }
        if (cardNumber.startsWith("0000")) {
            // Simulates a declined card, so we have a real failure path to demonstrate.
            throw new PaymentDeclinedException("Card ending in " + lastFour(cardNumber) + " was declined.");
        }
        return "TXN-" + System.currentTimeMillis();
    }

    private String lastFour(String cardNumber) {
        return cardNumber.length() >= 4 ? cardNumber.substring(cardNumber.length() - 4) : cardNumber;
    }
}