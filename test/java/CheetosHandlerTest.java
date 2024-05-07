import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class CheetosHandlerTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;
    private CheetosHandler cheetosHandler;
    private IdleState idleState;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Cheetos", new Snack("Cheetos", 1.50, 10));
        snacks.put("Lays", new Snack("Lays", 1.20, 10));
        idleState = new IdleState();
        cheetosHandler = new CheetosHandler(null);
        machine = new VendingMachine(snacks, cheetosHandler);
    }

    @Test
    public void testHandleRequestWithCheetosSufficientFunds() {
        machine.selectSnack("Cheetos");
        machine.insertMoney(1.50);
        cheetosHandler.handleRequest(machine, "Cheetos", 1.50);
        assertEquals("Machine should not have any inserted money left", 0, machine.getInsertedMoney(), 0.0);
        assertEquals("Machine state should be IdleState after dispensing", IdleState.class, machine.getState().getClass());
        assertEquals("Cheetos quantity should decrease by 1", 9, snacks.get("Cheetos").getQuantity());
    }

    @Test
    public void testHandleRequestWithCheetosInsufficientFunds() {
        machine.selectSnack("Cheetos");
        machine.insertMoney(1.00);
        cheetosHandler.handleRequest(machine, "Cheetos", 1.00);
        assertTrue("Machine state should remain unchanged when funds are insufficient", machine.getState() instanceof WaitingForMoneyState);
        assertEquals("Insufficient funds should not alter money inserted", 1.00, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestWithCheetosOutOfStock() {
        snacks.put("Cheetos", new Snack("Cheetos", 1.50, 0));
        machine.selectSnack("Cheetos");
        machine.insertMoney(1.50);
        cheetosHandler.handleRequest(machine, "Cheetos", 1.50);
        assertEquals("Machine state should be IdleState when Cheetos are out of stock", IdleState.class, machine.getState().getClass());
        assertEquals("Machine should return money when Cheetos are out of stock", 0, machine.getInsertedMoney(), 0.0);
    }

    @Test
    public void testHandleRequestNonCheetosSnack() {
        machine.selectSnack("Lays");
        machine.insertMoney(1.20);
        cheetosHandler.handleRequest(machine, "Lays", 1.20);
        assertNotNull("Next handler should be called when the snack is not Cheetos", machine.getSelectedSnack());
        assertEquals("Snack remains as Lays", "Lays", machine.getSelectedSnack().getName());
    }
}
