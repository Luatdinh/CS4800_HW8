public interface StateOfVendingMachine {
    void selectSnack(VendingMachine machine, String snackName);
    void insertMoney(VendingMachine machine, double amount);
    void dispenseSnack(VendingMachine machine);
}
