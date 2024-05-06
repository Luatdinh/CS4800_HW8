import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class CokeHandlerTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;
    private CokeHandler cokeHandler;
    private IdleState idleState;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Coke", new Snack("Coke", 1.25, 10));
        snacks.put("Pepsi", new Snack("Pepsi", 1.25, 10));
        idleState = new IdleState();
        cokeHandler = new CokeHandler(null);
        machine = new VendingMachine(snacks, cokeHandler);
    }

    @Test
    public void testHandleRequestWithCokeSufficientFunds() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.25);
        cokeHandler.handleRequest(machine, "Coke", 1.25);
        assertEquals("Machine should not have any inserted money left", 0, machine.getInsertedMoney(), 0.0);
        assertEquals("Machine state should be IdleState after dispensing", IdleState.class, machine.getState().getClass());
        assertEquals("Coke quantity should decrease by 1", 9, snacks.get("Coke").getQuantity());
    }

    @Test
    public void testHandleRequestWithCokeInsufficientFunds() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.00);
        cokeHandler.handleRequest(machine, "Coke", 1.00);
        assertTrue("Machine state should remain in current state when funds are insufficient", machine.getState() instanceof WaitingForMoneyState);
    }

    @Test
    public void testHandleRequestWithCokeOutOfStock() {
        snacks.put("Coke", new Snack("Coke", 1.25, 0));
        machine.selectSnack("Coke");
        machine.insertMoney(1.25);
        cokeHandler.handleRequest(machine, "Coke", 1.25);
        assertEquals("Machine state should be IdleState when Coke is out of stock", IdleState.class, machine.getState().getClass());
        assertEquals("Machine should return money when Coke is out of stock", 0, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestNonCokeSnack() {
        machine.selectSnack("Pepsi");
        machine.insertMoney(1.00);
        cokeHandler.handleRequest(machine, "Pepsi", 1.00);
        assertEquals("Snack should remain selected as Pepsi", "Pepsi", machine.getSelectedSnack().getName());
        assertTrue("Machine should not process a non-Coke snack", machine.getInsertedMoney() == 1.00);
        assertNotNull("Snack should still be selected since handler does not process non-Coke", machine.getSelectedSnack());
    }
}
