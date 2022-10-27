package data;

public abstract class User {
    protected String userID, password, name, surname, email, birthDate;

    public User(String userID, String password, String name, String surname, String email, String birthDate) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
    }

/*
    //TODO: for testing purposes, delete
    public User(String userID, String name, String surname, String email) {
        this.userID = userID;
        this.password = "";
        this.name = name;
        this.surname = surname;
        this.email = email;
    }*/

    public boolean checkCredentials(String id, String pass) {
        return (id.equals(userID) && pass.equals(password));
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    protected void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
