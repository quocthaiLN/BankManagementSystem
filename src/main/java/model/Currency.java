package model;

public class Currency {
    private String currencyCode;
    private String currencyName;
    private double rateToUSD;

    Currency() {
        this.currencyCode = "VND";
        this.currencyName = "Việt Nam Đồng";
        this.rateToUSD = 0;
    }

    Currency(String currencyCode, String currencyName, double rateToUSD) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.rateToUSD = rateToUSD;
    }
}
