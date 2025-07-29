package com.bank.app.model;

import java.time.LocalDate;

public class Customer {
    private String customerID;
    private String name;
    private LocalDate birthday;
    private String gender;
    private String identityNumber;
    private String number;
    private String address;
    private String email;
    private String type;
    private String status;
    private LocalDate registerDate;

    public Customer() {
        this.customerID = "";
        name = "";
        birthday = null;
        gender = "";
        identityNumber = "";
        number = "";
        address = "";
        email = " ";
        type = "";
        status = "";
        registerDate = null;
    }

    public Customer(String customerID, String name, LocalDate birthday, String gender,
            String identityNumber, String number, String address,
            String email, String type, String status, LocalDate registerDate) {
        this.customerID = customerID;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.identityNumber = identityNumber;
        this.number = number;
        this.address = address;
        this.email = email;
        this.type = type;
        this.status = status;
        this.registerDate = registerDate;
    }

}
