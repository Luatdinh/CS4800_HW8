import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class WaitingForMoneyStateTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Coke", new Snack("Coke", 1.25, 10));
        snacks.put("Doritos", new Snack("Doritos", 1.50, 5));
        machine = new VendingMachine(snacks, null);
    }

    @Test
    public void testInsertInsufficientMoney() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.00);
        assertEquals("Money should be recorded", 1.00, machine.getInsertedMoney(), 0.01);
        assertTrue("State should not change when insufficient money is inserted", machine.getState() instanceof WaitingForMoneyState);
    }

    @Test
    public void testInsertExactAmount() {
        machine.selectSnack("Coke");
        machine.setState(new WaitingForMoneyState());
        machine.insertMoney(1.25);
        assertEquals("Machine should record the exact amount", 1.25, machine.getInsertedMoney(), 0.01);
        assertTrue("Machine should transition to DispensingSnackState", machine.getState() instanceof DispensingSnackState);
    }

    @Test
    public void testInsertMoneyWhenSnackOutOfStock() {
        machine.selectSnack("Doritos");
        snacks.get("Doritos").setQuantity(0);
        machine.insertMoney(2.00);
        assertEquals("All money should be returned if the snack is out of stock", 0, machine.getInsertedMoney(), 0.01);
        assertTrue("Machine should return to IdleState due to out of stock", machine.getState() instanceof IdleState);
    }
}
