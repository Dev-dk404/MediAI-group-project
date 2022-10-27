// Import the functions you need from the SDKs you need
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.1.1/firebase-app.js";
import { getAuth, onAuthStateChanged } from "https://www.gstatic.com/firebasejs/9.1.1/firebase-auth.js";
import { getFirestore, collection, query, where, getDocs } from "https://www.gstatic.com/firebasejs/9.1.1/firebase-firestore.js";
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
const userId = localStorage.getItem('userId');

//Logged in or not functionality
async function getCurrentUserType(){
    var res;
        // Checking if user logged in is pracitioner or not
        if (userId !== null){
            const practitionersRef = collection(db, "practitioners");
            const q = query(practitionersRef, where("userID", "==", userId));
            const querySnapshot = await getDocs(q);
            var counter = 0;
            
            querySnapshot.forEach((doc) => { counter++; });
            
            if (counter == 0) { // Regular User Logged In (Return False)
                console.log("Regular Account logged in");
                res = 1;

            } else if (counter == 1){ // Practitioner Account Logged In (Return True)
                console.log("Practitioner Account logged in");
                res = 2;
            }

        } else { // No User Logged In (Return Null)
            console.log('No User');
            res = 0; 
        }
    return res;
}

export {getCurrentUserType};

async function adjustUI(){
    var currentUserType = await getCurrentUserType();

    //If any accoutnt is logged in, generate Log Out button
    if (currentUserType == 1 || currentUserType == 2) { 
        var registerLink = document.getElementById("registerLink");

        registerLink.innerHTML = "Log Out";
        registerLink.addEventListener("click", (e) => {
            e.preventDefault();
            localStorage.removeItem("userId")
            auth.signOut();
            location.reload();
        })
    }

    switch (currentUserType){
        case 1: // Adjust UI For Standard User Account
            var logInLink = document.getElementById("logInElement");
            logInLink.remove();
            break;

        case 2: // Adjust UI For Practitioner Account
            document.getElementById("logInLink").href="patients.html";
            document.getElementById("logInLink").innerHTML = "My Patients";
            break;
    }
}

// =======================================
// =============== M A I N ===============
// =======================================

if (document.title == "MediCheck - Home"){
    adjustUI();
}
