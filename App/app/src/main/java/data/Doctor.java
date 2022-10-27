package data;

public class Doctor extends User {
    protected Boolean available;
    protected String occupation;

    protected Doctor(String userID, String password, String name, String surname, String email, String occupation, String birthDate) {
        super(userID, password, name, surname, email, birthDate);
        this.occupation = occupation;
    }

   /* public Doctor(String userId, String first_name, String last_name, String email, String occupation) {
        super(userId, first_name, last_name, email);
        this.occupation = occupation;
    }*/

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }


    public String getOccupation() {
        return occupation;
    }
}
