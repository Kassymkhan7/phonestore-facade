package com.Kassymkhan7.phonestorefacade;

// Subsystem class: handles shipping logistics only, nothing else.
public class ShippingService {

    public String scheduleDelivery(String model, String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Shipping address must be provided.");
        }
        String trackingId = "SHIP-" + model.replace(" ", "").toUpperCase().hashCode();
        System.out.println("Shipping " + model + " to " + address + " (tracking: " + trackingId + ")");
        return trackingId;
    }
}