import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class SnackDispenseHandlerTest {

    private VendingMachine machine;
    private Map<String, Snack> snacks;
    private TestSnackDispenseHandler handler;
    private TestSnackDispenseHandler nextHandler;

    @Before
    public void setUp() {
        snacks = new HashMap<>();
        snacks.put("Coke", new Snack("Coke", 1.25, 10));
        nextHandler = new TestSnackDispenseHandler(null);
        handler = new TestSnackDispenseHandler(nextHandler);
        machine = new VendingMachine(snacks, handler);
    }

    @Test
    public void testHandleRequestWithNextHandler() {
        machine.insertMoney(1.25);
        machine.selectSnack("Coke");
        handler.handleRequest(machine, "Coke", 1.25);
        assertTrue("Next handler should have been called", nextHandler.wasCalled());
    }


    class TestSnackDispenseHandler extends SnackDispenseHandler {
        private boolean called = false;

        public TestSnackDispenseHandler(SnackDispenseHandler nextHandler) {
            super(nextHandler);
        }

        @Override
        public void handleRequest(VendingMachine machine, String snackName, double moneyInserted) {
            super.handleRequest(machine, snackName, moneyInserted);
            called = true;
        }

        public boolean wasCalled() {
            return called;
        }
    }
}
