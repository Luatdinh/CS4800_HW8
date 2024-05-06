import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class PepsiHandlerTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;
    private PepsiHandler pepsiHandler;
    private IdleState idleState;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Pepsi", new Snack("Pepsi", 1.25, 10));
        snacks.put("Coke", new Snack("Coke", 1.25, 10));
        idleState = new IdleState();
        pepsiHandler = new PepsiHandler(null);
        machine = new VendingMachine(snacks, pepsiHandler);
    }

    @Test
    public void testHandleRequestWithPepsiSufficientFunds() {
        machine.selectSnack("Pepsi");
        machine.insertMoney(1.25);
        pepsiHandler.handleRequest(machine, "Pepsi", 1.25);
        assertEquals("Machine should not have any inserted money left", 0, machine.getInsertedMoney(), 0.0);
        assertEquals("Machine state should be IdleState after dispensing", IdleState.class, machine.getState().getClass());
        assertEquals("Pepsi quantity should decrease by 1", 9, snacks.get("Pepsi").getQuantity());
    }

    @Test
    public void testHandleRequestWithPepsiInsufficientFunds() {
        machine.selectSnack("Pepsi");
        machine.insertMoney(1.00);
        pepsiHandler.handleRequest(machine, "Pepsi", 1.00);
        assertTrue("Machine state should remain unchanged when funds are insufficient", machine.getState() instanceof WaitingForMoneyState);
        assertEquals("Insufficient funds should not alter money inserted", 1.00, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestWithPepsiOutOfStock() {
        snacks.put("Pepsi", new Snack("Pepsi", 1.25, 0));
        machine.selectSnack("Pepsi");
        machine.insertMoney(1.25);
        pepsiHandler.handleRequest(machine, "Pepsi", 1.25);
        assertEquals("Machine state should be IdleState when Pepsi is out of stock", IdleState.class, machine.getState().getClass());
        assertEquals("Machine should return money when Pepsi is out of stock", 0, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestNonPepsiSnack() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.25);
        pepsiHandler.handleRequest(machine, "Coke", 1.25);
        assertNotNull("Next handler should be called when the snack is not Pepsi", machine.getSelectedSnack());
        assertEquals("Snack remains as Coke", "Coke", machine.getSelectedSnack().getName());
    }
}
