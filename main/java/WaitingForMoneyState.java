public class WaitingForMoneyState implements StateOfVendingMachine {
    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        // Do Nothing
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        Snack selectedSnack = machine.getSelectedSnack();
        double totalInserted = machine.getInsertedMoney() + amount;
        machine.setInsertedMoney(totalInserted);

        if (selectedSnack == null) {
            System.out.println("Error: No snack selected.");
            return;
        }
        if (totalInserted < selectedSnack.getPrice()) {
            System.out.println("Please insert $" + String.format("%.2f",(selectedSnack.getPrice() - totalInserted)) + " more to purchase the " + selectedSnack.getName());
        } else {
            if (selectedSnack.getQuantity() > 0) {
                System.out.println("Sufficient money inserted");
                machine.setState(new DispensingSnackState());
            } else {
                System.out.println("Sorry, " + selectedSnack.getName() + " is out of stock.");
                machine.returnMoney();
                machine.setState(new IdleState());
            }
        }
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        // Do Nothing
    }
}