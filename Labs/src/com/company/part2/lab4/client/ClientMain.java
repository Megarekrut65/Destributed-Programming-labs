package com.company.part2.lab4.client;

import com.company.part2.lab4.shared.DepartmentManagerRemote;
import com.company.part2.lab4.shared.DepartmentManagerRemoteConnector;
import com.company.part2.subjectarea.DepartmentManagerExamples;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientMain {
    public static void main(String[] args) {
        try {
            DepartmentManagerRemote managerRemote =
                    (DepartmentManagerRemote)Naming.lookup("//localhost/DepartmentManager");
            new DepartmentManagerExamples(
                            new DepartmentManagerRemoteConnector(managerRemote));
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
