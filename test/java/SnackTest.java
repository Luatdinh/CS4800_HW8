import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SnackTest {

    private Snack snack;

    @Before
    public void setUp() {
        snack = new Snack("Coke", 1.25, 10);
    }

    @Test
    public void testGetName() {
        assertEquals("Coke", snack.getName());
    }

    @Test
    public void testSetName() {
        snack.setName("Pepsi");
        assertEquals("Pepsi", snack.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1.25, snack.getPrice(), 0.0);
    }

    @Test
    public void testSetPrice() {
        snack.setPrice(1.50);
        assertEquals(1.50, snack.getPrice(), 0.0);
    }

    @Test
    public void testGetQuantity() {
        assertEquals(10, snack.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        snack.setQuantity(15);
        assertEquals(15, snack.getQuantity());
    }

    @Test
    public void testReduceQuantity() {
        snack.reduceQuantity();
        assertEquals(9, snack.getQuantity());
    }

    @Test
    public void testReduceQuantityAtZero() {
        snack.setQuantity(0);
        snack.reduceQuantity();
        assertEquals(0, snack.getQuantity());
    }
}
