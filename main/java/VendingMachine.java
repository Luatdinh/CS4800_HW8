import java.util.Map;

public class VendingMachine {
    private StateOfVendingMachine state;
    private Map<String, Snack> snacks;
    private Snack selectedSnack;
    private double insertedMoney;
    private SnackDispenseHandler snackDispenser;


    public VendingMachine(Map<String, Snack> snacks, SnackDispenseHandler snackDispenser) {
        this.snacks = snacks;
        this.state = new IdleState();
        this.snackDispenser = snackDispenser;
    }


    public SnackDispenseHandler getSnackDispenser() {
        return snackDispenser;
    }
    public void selectSnack(String snackName) {
        state.selectSnack(this, snackName);
    }


    public void insertMoney(double amount) {
        state.insertMoney(this, amount);
    }


    public void dispenseSnack() {
        if (state != null) {
            state.dispenseSnack(this);
        } else {
            System.out.println("State is not set properly.");
        }
    }


    public StateOfVendingMachine getState() {
        return state;
    }

    public void setState(StateOfVendingMachine state) {
        this.state = state;
    }

    public Map<String, Snack> getSnacks() {
        return snacks;
    }

    public Snack getSelectedSnack() {
        return selectedSnack;
    }

    public void setSelectedSnack(Snack selectedSnack) {
        this.selectedSnack = selectedSnack;
    }

    public double getInsertedMoney() {
        return insertedMoney;
    }

    public void setInsertedMoney(double insertedMoney) {
        this.insertedMoney = insertedMoney;
    }


    public void returnMoney() {
        System.out.println("Returning $" + insertedMoney + " to the user.");
        this.insertedMoney = 0;
    }
}
