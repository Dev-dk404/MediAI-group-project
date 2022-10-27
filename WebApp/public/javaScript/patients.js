// Import the functions you need from the SDKs you need
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.1.1/firebase-app.js";
import { getAuth, onAuthStateChanged, } from "https://www.gstatic.com/firebasejs/9.1.1/firebase-auth.js";
import { getFirestore, collection, query, where, getDocs } from "https://www.gstatic.com/firebasejs/9.1.1/firebase-firestore.js";
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

// Verifies if Practitioner is signed in, if not >> redirect to index.html
var currentUserType = await getCurrentUserType();

if (currentUserType !== 2) {
    window.location.href = "index.html";
}

function populateTable(){
    
}

var logOutLink = document.getElementById("logOut");

logOutLink.addEventListener("click", (e) => {
    localStorage.removeItem("userId");
    e.preventDefault();
    auth.signOut();
    location.reload();
    window.location.href = "index.html";
})