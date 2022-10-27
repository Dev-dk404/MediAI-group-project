import { initializeApp } from "https://www.gstatic.com/firebasejs/9.1.1/firebase-app.js";
import { getAuth, createUserWithEmailAndPassword, onAuthStateChanged} from "https://www.gstatic.com/firebasejs/9.1.1/firebase-auth.js";
import { getFirestore, doc, setDoc, addDoc} from "https://www.gstatic.com/firebasejs/9.1.1/firebase-firestore.js";
import { getCurrentUserType } from "./index.js";
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyBawA_FVydb-HcDYrq61Ede3pFz_gl0jZg",
    authDomain: "medi-app-b5149.firebaseapp.com",
    databaseURL: "https://medi-app-b5149-default-rtdb.europe-west1.firebasedatabase.app",
    projectId: "medi-app-b5149",
    storageBucket: "medi-app-b5149.appspot.com",
    messagingSenderId: "74095638486",
    appId: "1:74095638486:web:40193e4a04a1e895765677",
    measurementId: "G-83XMSS5VZN"
};

// Initialize Firebase Modules
const app = initializeApp(firebaseConfig);
const auth = getAuth();
const db = getFirestore();

// Check if user already logged in
var currentUserType = await getCurrentUserType();

if (currentUserType == 2 || currentUserType == 1) {
    window.location.href = "index.html";
}

/*=====================================================================================
============================= Registration Functionality ==============================
=======================================================================================*/

// Setting Register Button Function
if (document.getElementById("registerButton") !== null) {
    document.getElementById("registerButton").onclick = register;
}

// Email Vaidation
function validateEmail(email) {
    var expression = /^[^@]+@\w+(\.\w+)+\w$/
    if (expression.test(email) == true) {
        //Email is good
        return true
    } else {
        //Email is not good
        return false
    }
};

// Password Validation
function validatePassword(password) {
    //Firebase only accepts lengths greater than 6
    if (password < 6) {
        return false
    } else {
        return true
    }
};

// Validating Full Name
function validateFields(field) {
    if (field == null) {
        return false
    }

    if (field.length <= 0) {
        return false
    } else {
        return true
    }
};

// Is practitioner
document.getElementById("practitionerCheckbox").onclick = function(){
    var practitionerCheckbox = document.getElementById("practitionerCheckbox")
    if(practitionerCheckbox.checked){
        document.getElementById("practitionerId").disabled = false
    } else {
    document.getElementById("practitionerId").disabled = true
    }
};



// Register Function
async function register() {
    console.log("Registering");

    // Get all our input fields
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var dateOfBirth = document.getElementById("dateOfBirth").value;
    var practitionerId = document.getElementById("practitionerId").value;
    var practitionerCheckbox = document.getElementById("practitionerCheckbox")
    var walletAddress = document.getElementById("walletAddress").value;


    // Validate Input Fields
    if (password != confirmPassword) {
        alert("Passwords do not match");
        return;
        // Don't continue running the code
    }

    if (validateEmail(email) == false || validatePassword(password) == false) {
        alert("Email or Password is Invalid");
        return;
        // Dont continue running the code
    }

    if (validateFields(firstName) == false) {
        alert("Please enter a First Name");
        return;
        // Dont continue running the code
    }

    if (validateFields(lastName) == false) {
        alert("Please enter a Last Name");
        return;
        // Dont continue running the code
    }

    if (dateOfBirth == ""){
        alert("Please select a valid date of birth");
        return;
        // Dont continue running the code
    }

    if ((validateFields(practitionerId) == false) && (practitionerCheckbox.checked)) {
        alert("Please enter a practitioner ID")
        return;
        // Dont continue running the code
    }

    // Move on with auth
    createUserWithEmailAndPassword(auth, email, password)
        .then(async function() {

            // Storing User Data in Firestore DB
            const user = auth.currentUser;
            const uid = user.uid;

            if(practitionerCheckbox.checked){
                await setDoc(doc(db, "practitioners", uid), {
                    practitioner_id : practitionerId,
                    first_name : firstName,
                    last_Name : lastName,
                    date_of_birth : dateOfBirth,
                    email : email,
                    userID : uid,
                  });
            } else {
                
                  await setDoc(doc(db, "users", uid), {
                    first_name : firstName,
                    last_Name : lastName,
                    date_of_birth : dateOfBirth,
                    email : email,
                    userID : uid,
                    wallet_address : walletAddress
                  });
            }
            

            alert("User Created!")
            window.location.href = "index.html";

        })
        .catch(function(error) {
            // Firebase will use this to alert of its errors
            const errorCode = error.code
            const errorMessage = error.message

            console.log(errorCode + " : " + errorMessage)
        })
};

function registerOnEnter(input) {
    if (input == Element){}
    input.addEventListener("keyup", function(event){
        if (event.keyCode === 13) {
            event.preventDefault();
            document.getElementById("registerButton").click();
        }
    })
};

// Set "Enter" key to submit form when typing in input(s)
var email = document.getElementById("email");
var password = document.getElementById("password");
var confirmPassword = document.getElementById("confirmPassword");
var firstName = document.getElementById("firstName");
var lastName = document.getElementById("lastName");
var practitionerId = document.getElementById("practitionerId");
var walletAddress = document.getElementById("walletAddress");

registerOnEnter(email)
registerOnEnter(password)
registerOnEnter(confirmPassword)
registerOnEnter(firstName)
registerOnEnter(lastName)
registerOnEnter(practitionerId)
registerOnEnter(walletAddress)

console.log(db)
console.log(firebaseConfig);
console.log(auth);