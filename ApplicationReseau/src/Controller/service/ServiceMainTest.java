package Controller.service;

import Model.common.User.User;


// CLASSE DE TEST - UTILE UNIQUEMENT POUR DEV

public class ServiceMainTest {

    public static void main(String[] args) {
        User test = UserService.getInstance().getUser("Antonin");
        System.out.println(test.getPassword());
        System.out.println("DONE");
    }

}
