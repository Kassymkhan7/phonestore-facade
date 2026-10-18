package com.Kassymkhan7.phonestorefacade;

import java.util.HashMap;
import java.util.Map;

// Subsystem class: knows nothing about the Facade, fully usable on its own.
public class InventoryService {

    private final Map<String, Integer> stockByModel = new HashMap<>();

    public InventoryService() {
        // Seed some starting stock so the demo has data to work with.
        stockByModel.put("Galaxy S20 Ultra 5G", 5);
        stockByModel.put("Galaxy A50", 12);
    }

    public boolean isInStock(String model) {
        return stockByModel.getOrDefault(model, 0) > 0;
    }

    public void reserveUnit(String model) {
        if (!isInStock(model)) {
            throw new IllegalStateException("Cannot reserve " + model + ": out of stock.");
        }
        stockByModel.put(model, stockByModel.get(model) - 1);
    }

    public void releaseUnit(String model) {
        stockByModel.put(model, stockByModel.getOrDefault(model, 0) + 1);
    }
}