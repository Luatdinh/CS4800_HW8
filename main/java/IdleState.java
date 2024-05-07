public class IdleState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        Snack selectedSnack = machine.getSnacks().get(snackName);
        if (selectedSnack != null) {
            machine.setSelectedSnack(selectedSnack);
            machine.setState(new WaitingForMoneyState());
            System.out.println("Snack selected: " + snackName + ". Please insert $" + selectedSnack.getPrice());
        } else {
            System.out.println("Snack not found. Please select a valid snack.");
            if (machine.getInsertedMoney() > 0) {
                machine.returnMoney();
            }
        }
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        // Do Nothing
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        // Do Nothing
    }
}
