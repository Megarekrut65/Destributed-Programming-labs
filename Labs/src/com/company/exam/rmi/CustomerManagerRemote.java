package com.company.exam.rmi;

import com.company.exam.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerManagerRemote extends Remote {
    List<Customer> getCustomers() throws RemoteException;
    List<Customer> getSortedCustomers() throws RemoteException;
    List<Customer> getCustomersByCard(int start, int end) throws RemoteException;
}
