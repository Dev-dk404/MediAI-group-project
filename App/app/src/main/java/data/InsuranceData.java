package data;

import java.util.ArrayList;

class InsuranceData {

    private ArrayList<String> insuredPeople;//stores emails
    private String titular;//titular email
    private String startDate;
    private String renewedDate;
    private String endDate;

    protected InsuranceData() {
    }

    protected InsuranceData(String titular, ArrayList<String> insured, String start, String renew, String end) {
        this.titular = titular;
        insuredPeople = insured;
        startDate = start;
        renewedDate = renew;
        endDate = end;
    }

    public ArrayList<String> getInsuredPeople() {
        return insuredPeople;
    }

    public void setInsuredPeople(ArrayList<String> insuredPeople) {
        this.insuredPeople = insuredPeople;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRenewedDate() {
        return renewedDate;
    }

    public void setRenewedDate(String renewedDate) {
        this.renewedDate = renewedDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void addInsured(Client client) {
        if (!insuredPeople.contains(client)) insuredPeople.add(client.getEmail());
    }

    public ArrayList<String> getDetails() {
        ArrayList<String> strs = new ArrayList<String>();
        Client titular = MyApp.getData().getUserData(this.titular);
        strs.add("Insurance titular: " + titular.getName() + " " + titular.getSurname());
        String str = "";
        for (String insured : insuredPeople) {
            Client client= MyApp.getData().getUserData(insured);
            str += client.getName() + " " + client.getSurname() + ",";
        }
        strs.add("Insured people: " + str);

        return strs;
    }
}
