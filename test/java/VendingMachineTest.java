import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class VendingMachineTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Coke", new Snack("Coke", 1.25, 10));
        snacks.put("Pepsi", new Snack("Pepsi", 1.25, 10));
        snacks.put("Cheetos", new Snack("Cheetos", 1.50, 10));
        snacks.put("Doritos", new Snack("Doritos", 1.50, 10));

        SnackDispenseHandler doritosHandler = new DoritosHandler(null);
        machine = new VendingMachine(snacks, doritosHandler);
    }

    @Test
    public void testSelectSnack() {
        assertNull("Initially no snack should be selected", machine.getSelectedSnack());
        machine.selectSnack("Coke");
        assertNotNull("Snack should be selected", machine.getSelectedSnack());
        assertEquals("Selected snack should be Coke", "Coke", machine.getSelectedSnack().getName());
    }

    @Test
    public void testInsertMoney() {
        machine.selectSnack("Coke");
        machine.insertMoney(0.50);
        assertEquals("Inserted money should be 0.50", 0.50, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testDispenseSnack() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.25);
        machine.dispenseSnack();
        assertEquals("Should be in DispensingSnackState after dispensing", DispensingSnackState.class, machine.getState().getClass());
        assertEquals("No change should be remaining if the exact amount is inserted", 1.25, machine.getInsertedMoney(), 0.01);
    }


    @Test
    public void testReturnMoney() {
        machine.insertMoney(1.00);
        machine.returnMoney();
        assertEquals("All inserted money should be returned", 0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testOutOfStockScenario() {
        Snack coke = snacks.get("Coke");
        coke.setQuantity(0);
        machine.selectSnack("Coke");
        machine.insertMoney(1.25);
        machine.dispenseSnack();
        assertEquals("Check state after trying to dispense out-of-stock item", IdleState.class, machine.getState().getClass());
        assertEquals("Money should be returned if snack is out of stock", 0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testInsufficientFunds() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.00);
        machine.dispenseSnack();
        assertNotEquals("Machine should not dispense snack on insufficient funds", 0, machine.getInsertedMoney());
        assertEquals("Machine should remain in a waiting state for more money", WaitingForMoneyState.class, machine.getState().getClass());
    }
}
