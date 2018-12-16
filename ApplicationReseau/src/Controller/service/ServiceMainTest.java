package Controller.service;

import Model.Client.Client;
import Model.common.User;

import java.io.IOException;
import java.net.UnknownHostException;


// CLASSE DE TEST - UTILE UNIQUEMENT POUR DEV

public class ServiceMainTest {

    public static void main(String[] args) {
        User test = UserService.getInstance().getUser("Antonin");
        System.out.println(test.getPassword());
        System.out.println("DONE");
    }

}
