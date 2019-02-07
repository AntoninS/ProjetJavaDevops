package Model.common;

import Controller.service.HorseService;
import Controller.service.UserService;

public class User {

    private int id;
    private String pseudo;
    private String password;
    private float money;

    public User() {

    }

    public User(int id, String pseudo, String password, float money) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.money = money;
    }

    public void updateMoney() {
        UserService.getInstance().updateMoney(this.id, this.money);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
