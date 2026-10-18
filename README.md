# Phone Store Facade

Facade structural pattern in Java: a phone-store checkout where `PhoneOrderFacade`
hides four collaborating subsystems behind one simple call.

## Structure

- **Subsystem classes (4):** `InventoryService`, `PaymentProcessor`, `ShippingService`,
  `NotificationService` (plus `PaymentDeclinedException`).
- **Facade:** `PhoneOrderFacade` exposes `placeOrder(model, cardNumber, shippingAddress)`
  and coordinates the subsystems in the correct order.
- **Client:** `Main` uses only the Facade. A second method deliberately calls
  `InventoryService` directly to show that subsystems stay usable without the Facade
  and never reference it.

## Clean Code principles applied

**1. Single Responsibility: the Facade coordinates, it does not reimplement**
Before (client doing the orchestration itself):
```java
inventory.reserveUnit(model);
String txn = payment.charge(card, payment.getPrice(model));
String tracking = shipping.scheduleDelivery(model, address);
notifications.sendOrderConfirmation(model, txn, tracking);
```
After:
```java
orderFacade.placeOrder(model, card, address);
```
Pricing, stock and shipping logic still live in their own classes. The Facade only
decides the order and handles failures.

**2. Proper error handling for coordination failures**
```java
try {
    transactionId = paymentProcessor.charge(cardNumber, price);
} catch (PaymentDeclinedException e) {
    inventoryService.releaseUnit(model);   // roll back the reservation
    notificationService.sendOrderFailure(model, e.getMessage());
    return;
}
```
A declined card never leaves a unit stuck as reserved and never triggers shipping.

**3. Specific exception type instead of a generic one**
Before: `throw new RuntimeException("declined")`
After: `throw new PaymentDeclinedException(...)`
The Facade catches only payment failures, so unrelated bugs are not swallowed.

**4. Small, focused subsystem classes**
`ShippingService` has one job: schedule a delivery and return a tracking ID. Each of the
four classes owns one concern and has no knowledge of the others or of the Facade.

**5. No leaking of subsystem details through the public interface**
`placeOrder` takes and returns only simple values (strings). Callers never see
`InventoryService`, `PaymentProcessor` or their internals, so they can change
without breaking clients.

**6. Meaningful names / no magic values**
`placeOrder`, `reserveUnit`, `releaseUnit`, `sendOrderFailure` describe intent, and
prices live in a named constant, `PRICE_BY_MODEL`, instead of being scattered through code.
