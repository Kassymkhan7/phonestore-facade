package com.Kassymkhan7.phonestorefacade;

// A specific, meaningful exception type instead of a generic RuntimeException —
// lets the Facade catch payment failures distinctly from other subsystem errors.
public class PaymentDeclinedException extends RuntimeException {
    public PaymentDeclinedException(String message) {
        super(message);
    }
}