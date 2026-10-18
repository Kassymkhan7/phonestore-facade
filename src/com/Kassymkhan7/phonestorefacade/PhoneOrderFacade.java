package com.Kassymkhan7.phonestorefacade;

// Facade: exposes a small, simplified API over 4 subsystem classes.
// The client only ever talks to this class — never to the subsystems directly.
public class PhoneOrderFacade {

    private final InventoryService inventoryService;
    private final PaymentProcessor paymentProcessor;
    private final ShippingService shippingService;
    private final NotificationService notificationService;

    public PhoneOrderFacade() {
        this.inventoryService = new InventoryService();
        this.paymentProcessor = new PaymentProcessor();
        this.shippingService = new ShippingService();
        this.notificationService = new NotificationService();
    }

    // The one method a Client needs. Internally it coordinates 4 collaborating
    // classes in the correct order, with proper error handling between steps.
    public void placeOrder(String model, String cardNumber, String shippingAddress) {
        if (!inventoryService.isInStock(model)) {
            notificationService.sendOrderFailure(model, "out of stock.");
            return;
        }

        inventoryService.reserveUnit(model);

        String transactionId;
        try {
            double price = paymentProcessor.getPrice(model);
            transactionId = paymentProcessor.charge(cardNumber, price);
        } catch (PaymentDeclinedException e) {
            // Coordination failure: undo the inventory hold so the unit isn't stuck reserved.
            inventoryService.releaseUnit(model);
            notificationService.sendOrderFailure(model, e.getMessage());
            return;
        }

        String trackingId = shippingService.scheduleDelivery(model, shippingAddress);
        notificationService.sendOrderConfirmation(model, transactionId, trackingId);
    }
}