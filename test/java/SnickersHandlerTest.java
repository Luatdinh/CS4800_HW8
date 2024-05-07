import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class SnickersHandlerTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;
    private SnickersHandler snickersHandler;
    private IdleState idleState;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Snickers", new Snack("Snickers", 1.50, 10));
        snacks.put("KitKat", new Snack("KitKat", 1.20, 10));
        idleState = new IdleState();
        snickersHandler = new SnickersHandler(null);
        machine = new VendingMachine(snacks, snickersHandler);
    }

    @Test
    public void testHandleRequestWithSnickersSufficientFunds() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.50);
        snickersHandler.handleRequest(machine, "Snickers", 1.50);
        assertEquals("Machine should not have any inserted money left", 0, machine.getInsertedMoney(), 0.0);
        assertEquals("Machine state should be IdleState after dispensing", IdleState.class, machine.getState().getClass());
        assertEquals("Snickers quantity should decrease by 1", 9, snacks.get("Snickers").getQuantity());
    }

    @Test
    public void testHandleRequestWithSnickersInsufficientFunds() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.00);
        snickersHandler.handleRequest(machine, "Snickers", 1.00);
        assertTrue("Machine state should remain unchanged when funds are insufficient", machine.getState() instanceof WaitingForMoneyState);
        assertEquals("Insufficient funds should not alter money inserted", 1.00, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestWithSnickersOutOfStock() {
        snacks.put("Snickers", new Snack("Snickers", 1.50, 0));
        machine.selectSnack("Snickers");
        machine.insertMoney(1.50);
        snickersHandler.handleRequest(machine, "Snickers", 1.50);
        assertEquals("Machine state should be IdleState when Snickers is out of stock", IdleState.class, machine.getState().getClass());
        assertEquals("Machine should return money when Snickers are out of stock", 0, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestNonSnickersSnack() {
        machine.selectSnack("KitKat");
        machine.insertMoney(1.20);
        snickersHandler.handleRequest(machine, "KitKat", 1.20);
        assertNotNull("Next handler should be called when the snack is not Snickers", machine.getSelectedSnack());
        assertEquals("Snack remains as KitKat", "KitKat", machine.getSelectedSnack().getName());
    }
}
