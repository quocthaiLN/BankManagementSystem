package model;

public class Branch {
    private String branchID;
    private String branchName;
    private String address;
    private String status; // mở đóng

    Branch() {
        this.branchID = "";
        this.branchName = "";
        this.address = "";
        this.status = "mở";
    }

    Branch(String branchID, String branchName, String address, String status) {
        this.branchID = branchID;
        this.branchName = branchName;
        this.address = address;
        this.status = status;
    }
}
