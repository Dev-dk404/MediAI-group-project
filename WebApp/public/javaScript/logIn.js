import { initializeApp } from "https://www.gstatic.com/firebasejs/9.1.1/firebase-app.js";
import { getAuth, signInWithEmailAndPassword} from "https://www.gstatic.com/firebasejs/9.1.1/firebase-auth.js";
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

// Check if user already logged in
var currentUserType = await getCurrentUserType();

if (currentUserType == 2 || currentUserType == 1) {
    window.location.href = "index.html";
}

/*=====================================================================================
=============================== Login Functionality ===================================
=======================================================================================*/

// Setting Login Button Function
if (document.getElementById("loginButton") !== null) {
    document.getElementById("loginButton").onclick = logIn;
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

// Log in Function
function logIn() {
    console.log("logging in");

    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    // Validate Input Fields
    if (validateEmail(email) == false || validatePassword(password) == false) {
        alert("Email or Password is Invalid")
        return
        //Dont continue running the code
    }

    // Move on with auth
    signInWithEmailAndPassword(auth, email, password)
    .then((userCredential) => {
            // Signed in 

            // Storing User ID in local storage
            localStorage.setItem("userId", auth.currentUser.uid)
            const user = userCredential.user;
            alert("Logged in!")
            window.location.href = "index.html"
        })
        .catch((error) => {

            const errorCode = error.code;
            const errorMessage = error.message;

            alert("Incorrect email and/or password!")
        });
}

function loginOnEnter(input) {
    if (input == Element){}
    input.addEventListener("keyup", function(event){
        if (event.keyCode === 13) {
            event.preventDefault();
            document.getElementById("loginButton").click();
        }
    })
};

// Set "Enter" key to submit form when typing in input(s)
var email = document.getElementById("email");
var password = document.getElementById("password");
loginOnEnter(email)
loginOnEnter(password)


console.log(firebaseConfig);
console.log(auth);