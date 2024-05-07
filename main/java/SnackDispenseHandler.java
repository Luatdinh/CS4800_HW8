public abstract class SnackDispenseHandler {
    protected SnackDispenseHandler nextHandler;

    public SnackDispenseHandler(SnackDispenseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handleRequest(VendingMachine machine, String snackName, double moneyInserted) {
        if (nextHandler != null) {
            nextHandler.handleRequest(machine, snackName, moneyInserted);
        }
    }
}
