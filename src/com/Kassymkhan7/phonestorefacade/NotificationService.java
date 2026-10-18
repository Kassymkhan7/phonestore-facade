package com.Kassymkhan7.phonestorefacade;

// Subsystem class: sends customer-facing messages only.
public class NotificationService {

    public void sendOrderConfirmation(String model, String transactionId, String trackingId) {
        System.out.println("Email sent: Your order for " + model +
                " is confirmed (txn " + transactionId + "), tracking " + trackingId + ".");
    }

    public void sendOrderFailure(String model, String reason) {
        System.out.println("Email sent: Your order for " + model + " could not be completed — " + reason);
    }
}