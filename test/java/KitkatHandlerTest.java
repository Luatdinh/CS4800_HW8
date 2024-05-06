import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class KitkatHandlerTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;
    private KitkatHandler kitkatHandler;
    private IdleState idleState;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Kitkat", new Snack("Kitkat", 1.00, 10));
        snacks.put("Snickers", new Snack("Snickers", 1.20, 10));
        idleState = new IdleState();
        kitkatHandler = new KitkatHandler(null);
        machine = new VendingMachine(snacks, kitkatHandler);
    }

    @Test
    public void testHandleRequestWithKitkatSufficientFunds() {
        machine.selectSnack("Kitkat");
        machine.insertMoney(1.00);
        kitkatHandler.handleRequest(machine, "Kitkat", 1.00);
        assertEquals("Machine should not have any inserted money left", 0, machine.getInsertedMoney(), 0.0);
        assertEquals("Machine state should be IdleState after dispensing", IdleState.class, machine.getState().getClass());
        assertEquals("Kitkat quantity should decrease by 1", 9, snacks.get("Kitkat").getQuantity());
    }

    @Test
    public void testHandleRequestWithKitkatInsufficientFunds() {
        machine.selectSnack("Kitkat");
        machine.insertMoney(0.75);
        kitkatHandler.handleRequest(machine, "Kitkat", 0.75);
        assertTrue("Machine state should remain unchanged when funds are insufficient", machine.getState() instanceof WaitingForMoneyState);
        assertEquals("Insufficient funds should not alter money inserted", 0.75, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestWithKitkatOutOfStock() {
        snacks.put("Kitkat", new Snack("Kitkat", 1.00, 0)); // Kitkat is out of stock
        machine.selectSnack("Kitkat");
        machine.insertMoney(1.00);
        kitkatHandler.handleRequest(machine, "Kitkat", 1.00);
        assertEquals("Machine state should be IdleState when Kitkat is out of stock", IdleState.class, machine.getState().getClass());
        assertEquals("Machine should return money when Kitkat is out of stock", 0, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestNonKitkatSnack() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.20);
        kitkatHandler.handleRequest(machine, "Snickers", 1.20);
        assertNotNull("Next handler should be called when the snack is not Kitkat", machine.getSelectedSnack());
        assertEquals("Snack remains as Snickers", "Snickers", machine.getSelectedSnack().getName());
    }
}
