import java.util.HashMap;
import java.util.Map;

public class Driver {
    public static void main(String[] args) {
        Map<String, Snack> snacks = new HashMap<>();
        snacks.put("Coke", new Snack("Coke", 1.25, 10));
        snacks.put("Pepsi", new Snack("Pepsi", 1.25, 10));
        snacks.put("Cheetos", new Snack("Cheetos", 1.50, 10));
        snacks.put("Doritos", new Snack("Doritos", 1.50, 10));
        snacks.put("KitKat", new Snack("KitKat", 1.00, 10));
        snacks.put("Snickers", new Snack("Snickers", 1.00, 1));
        SnackDispenseHandler snickersHandler = new SnickersHandler(null);
        SnackDispenseHandler kitKatHandler = new KitkatHandler(snickersHandler);
        SnackDispenseHandler doritosHandler = new DoritosHandler(kitKatHandler);
        SnackDispenseHandler cheetosHandler = new CheetosHandler(doritosHandler);
        SnackDispenseHandler pepsiHandler = new PepsiHandler(cheetosHandler);
        SnackDispenseHandler cokeHandler = new CokeHandler(pepsiHandler);
        VendingMachine vendingMachine = new VendingMachine(snacks, cokeHandler);
        System.out.println("\n--- Test 1: Purchase Snickers ---");
        vendingMachine.selectSnack("Coke");
        System.out.println("Insert $1.25 first");
        vendingMachine.insertMoney(1.25);
        vendingMachine.dispenseSnack();
        System.out.println("\n--- Test 2: Purchase Coke with Insufficient Funds Twice then Sufficient Funds ---");
        vendingMachine.selectSnack("Pepsi");
        System.out.println("Insert $1.05 first");
        vendingMachine.insertMoney(1.05);
        vendingMachine.dispenseSnack();
        System.out.println("Insert $0.10");
        vendingMachine.insertMoney(0.10);
        vendingMachine.dispenseSnack();
        System.out.println("Insert another $0.10");
        vendingMachine.insertMoney(0.10);
        vendingMachine.dispenseSnack();
        System.out.println("\n--- Test 3: Attempt to Purchase Snickers ---");
        vendingMachine.selectSnack("Snickers");
        System.out.println("Insert $1.00 first");
        vendingMachine.insertMoney(1.00);
        vendingMachine.dispenseSnack();
        System.out.println("Since There Was Only One Snickers In The Vending Machine At The Beginning, Now Vending Machine No Longer Has Snickers");
        System.out.println("\n--- Test 4: Attempt to Purchase Out-of-Stock Snickers ---");
        vendingMachine.selectSnack("Snickers");
        System.out.println("Insert $1.00 first");
        vendingMachine.insertMoney(1.00);
        vendingMachine.dispenseSnack();
    }
}
