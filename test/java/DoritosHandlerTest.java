import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class DoritosHandlerTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;
    private DoritosHandler doritosHandler;
    private IdleState idleState;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Doritos", new Snack("Doritos", 1.50, 10));
        snacks.put("Coke", new Snack("Coke", 1.25, 10));
        idleState = new IdleState();
        doritosHandler = new DoritosHandler(null);
        machine = new VendingMachine(snacks, doritosHandler);
    }

    @Test
    public void testHandleRequestWithDoritosSufficientFunds() {
        machine.selectSnack("Doritos");
        machine.insertMoney(1.50);
        doritosHandler.handleRequest(machine, "Doritos", 1.50);
        assertEquals("Machine should not have any inserted money left", 0, machine.getInsertedMoney(), 0.0);
        assertEquals("Machine state should be IdleState after dispensing", IdleState.class, machine.getState().getClass());
        assertEquals("Doritos quantity should decrease by 1", 9, snacks.get("Doritos").getQuantity());
    }

    @Test
    public void testHandleRequestWithDoritosInsufficientFunds() {
        machine.selectSnack("Doritos");
        machine.insertMoney(1.00);
        doritosHandler.handleRequest(machine, "Doritos", 1.00);
        assertTrue("Machine state should remain unchanged when funds are insufficient", machine.getState() instanceof WaitingForMoneyState);
    }

    @Test
    public void testHandleRequestWithDoritosOutOfStock() {
        snacks.put("Doritos", new Snack("Doritos", 1.50, 0));
        machine.selectSnack("Doritos");
        machine.insertMoney(1.50);
        doritosHandler.handleRequest(machine, "Doritos", 1.50);
        assertEquals("Machine state should be IdleState when Doritos are out of stock", IdleState.class, machine.getState().getClass());
        assertEquals("Machine should return money when Doritos are out of stock", 0, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestNonDoritosSnack() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.25);
        doritosHandler.handleRequest(machine, "Coke", 1.25);
        assertNotNull("Next handler should be called when the snack is not Doritos", machine.getSelectedSnack());
    }
}
