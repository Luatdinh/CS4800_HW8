public class DispensingSnackState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        // Do Nothing
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        // Do Nothing
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        if (machine.getSelectedSnack() != null) {
            machine.getSnackDispenser().handleRequest(machine, machine.getSelectedSnack().getName(), machine.getInsertedMoney());
        } else {
            System.out.println("Error: No snack selected.");
            machine.setState(new IdleState());
        }
    }
}
