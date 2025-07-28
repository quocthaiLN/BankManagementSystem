package service;

import model.Account;
import model.Customer;

public interface AccountServiceImpl {
    boolean addAccount(Customer infoCustomer, Account insertedAccount);
}
