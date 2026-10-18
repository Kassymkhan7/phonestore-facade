package com.Kassymkhan7.phonestorefacade;

public class Main {

    public static void main(String[] args) {
        demonstrateFacadeClient();
        System.out.println("---");
        demonstrateAdvancedClientBypassingFacade();
    }

    // Ordinary client: only ever touches PhoneOrderFacade.
    private static void demonstrateFacadeClient() {
        PhoneOrderFacade orderFacade = new PhoneOrderFacade();

        // Successful order
        orderFacade.placeOrder("Galaxy S20 Ultra 5G", "4111111111111111", "123 Dostyk Ave, Astana");

        // Declined card — demonstrates the rollback path (inventory released, failure email sent)
        orderFacade.placeOrder("Galaxy A50", "0000111122223333", "45 Abay St, Almaty");
    }

    // Advanced client: bypasses the Facade entirely, using a subsystem class directly.
    // This is explicitly allowed by the pattern — subsystem classes stay independently usable.
    private static void demonstrateAdvancedClientBypassingFacade() {
        InventoryService inventoryService = new InventoryService();
        System.out.println("Direct subsystem check — Galaxy A50 in stock: " + inventoryService.isInStock("Galaxy A50"));
    }
}