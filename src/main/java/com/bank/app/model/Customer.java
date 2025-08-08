package com.bank.app.model;

import java.time.LocalDate;

public class Customer implements User {
    private String customerID; // auto increment
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String identityNumber;
    private String phone;
    private String address;
    private String email;
    private String type; // cá nhân, doanh nghiệp
    private String status; // mở || khóa || đóng
    private LocalDate registerDate;

    public Customer() {
        this.name = "";
        this.birthDate = null;
        this.gender = "";
        this.identityNumber = "";
        this.phone = "";
        this.address = "";
        this.email = "";
        this.type = "cá nhân";
        this.status = "mở";
        this.registerDate = LocalDate.now();
    }

    public Customer(String customerID, String name, LocalDate birthDate, String gender, String identityNumber,
            String phone, String address, String email, String type, String status, LocalDate registerDate) {
        this.customerID = customerID;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.identityNumber = identityNumber;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.type = type;
        this.status = status;
        this.registerDate = registerDate;
    }

    public Customer(String name, LocalDate birthDate, String gender, String identityNumber, String phone,
            String address, String email, String type, String status, LocalDate registerDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.identityNumber = identityNumber;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.type = type;
        this.status = status;
        this.registerDate = registerDate;
    }

    @Override
    public String getUserType() {
        return "Customer";
    }

    // getter
    public String getCustomerID() {
        return this.customerID;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public String getGender() {
        return this.gender;
    }

    public String getIndentityNumber() {
        return this.identityNumber;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    public LocalDate getRegisterDate() {
        return this.registerDate;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    // display
    public void display() {
        System.out.printf("Customer ID: %d \n", this.customerID);
        System.out.printf("Customer Name: %s \n", this.name);
        System.out.printf("Customer DOB: %s \n", this.birthDate.toString());
        System.out.printf("Customer Gender: %s \n", this.gender);
        System.out.printf("Customer Identity Num: %s \n", this.identityNumber);
        System.out.printf("Customer phone: %s \n", this.phone);
        System.out.printf("Customer address: %s \n", this.address);
        System.out.printf("Customer email: %s \n", this.email);
        System.out.printf("Customer type: %s \n", this.type);
        System.out.printf("Customer status: %s \n", this.status);
        System.out.printf("Customer register day: %s \n", this.registerDate.toString());

    }
}
