pragma solidity ^0.8.0;


contract Storage{
    User[] public users;
    medicalInformation[] public medicalHistory;

    struct User {
        string userID;
        string wallet;
        string firstName;
        string lastName;
        string email;
        string dateOfBirth;
    }

    struct medicalInformation{
        uint userIndex;
    }

    function storeUser(string calldata userID,string calldata wallet,string calldata firstName, string calldata lastName, string calldata email,string calldata dateOfBirth) external {
        User memory entry = User(userID,wallet,firstName,lastName,email,dateOfBirth);
        users.push(entry);
    }

    function storeMedicalInfo() external {
        medicalInformation memory entry = medicalInformation(1);
        medicalHistory.push(entry);
    }

    function retrieveUser(uint index) public view returns (User memory){
        return users[index];
    }
}