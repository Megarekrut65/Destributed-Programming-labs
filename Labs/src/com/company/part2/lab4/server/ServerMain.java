package com.company.part2.lab4.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("DepartmentManager", new DepartmentManagerRMIServer(3306));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
