public class VirtualCurrency {
    private static double currency = 100;

    public static String getCurrency() {
        return String.valueOf(currency);
    }

    public static double getCurrencyNumber() {
        return currency;
    }

    public static void setCurrency(double currencyInput) {
        currency = currencyInput;
    }

    public static void addCurrency(double amount) {
        currency += amount;
    }

    public static void forceSubtract(double amount) {
        currency -= amount;
    }
}