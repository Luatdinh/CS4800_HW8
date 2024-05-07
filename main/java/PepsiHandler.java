public class PepsiHandler extends SnackDispenseHandler {
    public PepsiHandler(SnackDispenseHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleRequest(VendingMachine machine, String snackName, double moneyInserted) {
        if (snackName.equalsIgnoreCase("Pepsi")) {
            Snack snack = machine.getSnacks().get(snackName);
            if (snack != null) {
                if (moneyInserted >= snack.getPrice()) {
                    if (snack.getQuantity() > 0) {
                        snack.reduceQuantity();
                        System.out.println("Dispensing: " + snack.getName());
                        machine.setInsertedMoney(0);
                        machine.setSelectedSnack(null);
                        machine.setState(new IdleState());
                        System.out.println("Please collect your " + snack.getName() + ". Thank you!");
                    } else {
                        System.out.println("Sorry, " + snack.getName() + " is out of stock.");
                        machine.returnMoney();
                        machine.setState(new IdleState());
                    }
                } else {
                    System.out.println("Insufficient funds. Please insert $" + (snack.getPrice() - moneyInserted) + " more.");
                }
            } else {
                super.handleRequest(machine, snackName, moneyInserted);
            }
        } else {
            super.handleRequest(machine, snackName, moneyInserted);
        }
    }
}

