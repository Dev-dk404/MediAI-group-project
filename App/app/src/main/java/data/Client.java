package data;

import java.util.ArrayList;

public class Client extends User {

    private InsuranceData insurance;

    //
    protected Client(String userID, String password, String name, String surname, String email, InsuranceData insurance, String birthDate) {
        super(userID, password, name, surname, email, birthDate);
        this.insurance = insurance;
    }



    /*//TODO: delete after testing
    //for testing purposes
    protected Client(String userID, String name, String surname, String email) {
        super(userID, name, surname, email);
        this.insurance = insurance;
    }*/

    public InsuranceData getInsurance() {
        return insurance;
    }

    public ArrayList<String> getInsuranceDetails() {
        return insurance.getDetails();
    }

    public String print() {
        String str = "";
        str += "----------";
        str += "id: " + userID + "\n";
        str += "name: " + name + "\n";
        str += "surname: " + surname + "\n";
        str += "email: " + email + "\n";
        str += "----------";
        return str;
    }

    public void setInsurance(InsuranceData retrieveInsurance) {
        insurance = retrieveInsurance;
    }
}
