package main.java.model;
import java.time.LocalDate;

public class Customer implements User {
    private int customerID; // auto increment
    private String name; 
    private LocalDate birthDate;
    private String gender;
    private String identityNumber;
    private String phone;
    private String address;
    private String email;
    private String type; //  cá nhân,  doanh nghiệp
    private String status; // mở || khóa || đóng => open || lock || close
    private LocalDate registerDate;

    Customer() {
        this.name = "";
        this.birthDate = null;
        this.gender = "";
        this.identityNumber = "";
        this.phone = "";
        this.address = "";
        this.email = "";
        this.type = "cá nhân";
        this.status = "open";
        this.registerDate = null;
    }

    Customer(String name, LocalDate birthDate, String gender, String identityNumber, String phone, String address, String email, String type, String status, LocalDate registerDate) {
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
}
