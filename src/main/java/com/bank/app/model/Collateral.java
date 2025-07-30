package com.bank.app.model;


// Thế chấp
public class Collateral {
    private String collateralID;
    private int ownerID;
    private String assetType;
    private double estimatedValue;
    private String currency;
    private String status;

    Collateral() {
        this.collateralID = "";
        this.ownerID = 0;
        this.assetType = "";
        this.estimatedValue = 0;
        this.currency = "VND";
        this.status = "";
    }

    Collateral(String collateralID, int ownerID, String assetType, double estimatedValue, String currency, String status) {
        this.collateralID = collateralID;
        this.ownerID = ownerID;
        this.assetType = assetType;
        this.estimatedValue = estimatedValue;
        this.currency = currency;
        this.status = status;
    }
}
