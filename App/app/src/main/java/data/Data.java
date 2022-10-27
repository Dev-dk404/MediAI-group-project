package data;

import android.app.Activity;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.mediapp.LogInActv;
import com.example.mediapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class Data {

    final String TAG = "Data";
    FirebaseFirestore db;
    User user;
    //Hashtable<String, User> users;
    ArrayList<Doctor> doctors;
    ArrayList<Client> clients;
    private String[] occupations = new String[]{"orthopaedic surgeon", "pediatrist",
            "general practitioner", "neurologist", "student"};
    private FirebaseAuth mAuth;//for firebase authorization
    Activity actv;

    /* This class feeds data to the front end by request
     *  An Activity is passed as parameter so that some other things can be launched, such as
     * executors for database connection
     */
    public Data(Activity actv) {
        this.actv = actv;

        // Initialize Firebase Auth

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //  populateData();
        loadDoctors();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Log.d(TAG, "number of doctors: " + doctors.size());
        for (Doctor doc : doctors) {
            Log.d(TAG, "doctor loaded: " + doc.name);
        }
    }

    /*
    loads data from remote into the app
     */
    public void populateData() {
        db = FirebaseFirestore.getInstance();

        //createUsers(); //for creating users in database, delete after

        doctors = new ArrayList<Doctor>();
        clients = new ArrayList<Client>();
        //  users = new Hashtable<String, User>();
        loadDoctors();
        loadClients();
        // loadUsers();


    }


    /*
     * Loads Client objects into the clients data structure, based on data on the database
     */
    private void loadClients() {
        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                //returns a list of hashmaps, but can't parse directly to them
                List<Object> list = queryDocumentSnapshots.toObjects(Object.class);

                for (Object obj : list) {
                    if (obj instanceof HashMap) {
                        HashMap map = (HashMap) obj;
                        Client c = new Client((String) map.get("userID"), "",
                                (String) map.get("first_name"), (String) map.get("last_name"),
                                (String) map.get("email"), getInsurance((String) map.get("email")),
                                (String) map.get("date_of_birth"));
                        clients.add(c);

                    }
                }
                printClients();
            }
        });
        db.collection("users").get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "load clients failed");
            }
        });
    }

    private InsuranceData getInsurance(String email) {
        //TODO: return insurance data based on email
        return null;
    }

    /*
     * Loads all possible users into the users data structure, using their userId as key, from the clients and doctors structures

    private void loadUsers() {
        for (Doctor doc : doctors) {
            users.put(doc.userID.toUpperCase(Locale.ROOT), doc);
        }
        for (Client client : clients) {
            users.put(client.userID.toUpperCase(), client);
        }
    }

    /*
     * Loads Doctor objects into the doctors data structure, based on data on the database
     *
     */
    private void loadDoctors() {
        doctors = new ArrayList<Doctor>();


        Task getDocs = db.collection("practitioners").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        //returns a list of hashmaps, but can't parse directly to them
                        List<Object> list = queryDocumentSnapshots.toObjects(Object.class);

                        for (Object obj : list) {
                            if (obj instanceof HashMap) {
                                HashMap map = (HashMap) obj;
                                String occupation = occupations[2];
                                if (map.get("occupation") != null)
                                    occupation = (String) map.get("occupation");
                                if (map.containsKey("last_name")) {//to circumvent caps irregularity
                                    Doctor c = new Doctor((String) map.get("userId"),
                                            null,
                                            (String) map.get("first_name"),
                                            (String) map.get("last_name"),
                                            (String) map.get("email"),
                                            occupation,
                                            (String) map.get("date_of_birth"));
                                    doctors.add(c);
                                    Log.d(TAG, "loading doctor " + map.get("first_name"));
                                } else {
                                    Doctor c = new Doctor((String) map.get("userId"),
                                            null,
                                            (String) map.get("first_name"),
                                            (String) map.get("last_Name"),
                                            (String) map.get("email"),
                                            occupation,
                                            (String) map.get("date_of_birth"));
                                    doctors.add(c);
                                    Log.d(TAG, "loading doctor " + map.get("first_name"));
                                }
                            }
                        }
                        Log.d(TAG, "doctors loaded");
                    }
                });


        db.collection("practitioners").get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "load doctors failed");
                    }
                });


        Log.d(TAG, "doctors loading method finished");
    }

    /*
    returns the doctor with the assigned index
    @params: int doc, the index for the doctor
     */
    public Doctor getDoctor(int doc) {
        return doctors.get(doc);
    }

    /*
    returns the list of loaded doctors
     */
    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    /*
    returns the loaded client
     */
    public User getUser() {
        return user;
    }


    private void printClients() {
        Log.d(TAG, "size: " + clients.size());
        for (Client client : clients) {
            Log.d(TAG, "\n" + client.print());
        }
    }


    public void setUser() {
        FirebaseUser fbUser = mAuth.getCurrentUser();
        List<String> list = new ArrayList<String>();
        Log.d(TAG, "setting user: looking for user");
        db.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        //returns a list of hashmaps, but can't parse directly to them
                        List<Object> list = queryDocumentSnapshots.toObjects(Object.class);
                        for (Object obj : list) {
                            Log.d(TAG, "user: ");
                            if (obj instanceof HashMap) {
                                HashMap map = (HashMap) obj;

                                // Log.d(TAG, "signInWithEmail: fbUser " + fbUser.getEmail());
                                // Log.d(TAG, "signInWithEmail: usr " + map.get("email"));
                                // Log.d(TAG, areTheyEqual(map.get("email").toString(), fbUser.getEmail()));
                                if (map.get("email").equals(fbUser.getEmail())) {
                                    Log.d(TAG, "user found");
                                    Client client = new Client(
                                            //map.get("userID").toString(),
                                            "",
                                            "",
                                            map.get("first_name").toString(),
                                            map.get("last_name").toString(),
                                            fbUser.getEmail(),
                                            null,
                                            map.get("date_of_birth").toString());

                                    user = client;
                                    break;
                                }
                            }
                        }

                    }
                });
        if (user == null) {//if user is null it's a doctor (if not a doctor or client, log in failed)
            db.collection("users").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            //returns a list of hashmaps, but can't parse directly to them
                            List<Object> list = queryDocumentSnapshots.toObjects(Object.class);

                            for (Object obj : list) {
                                if (obj instanceof HashMap) {
                                    HashMap map = (HashMap) obj;
                                    if (map.get("email").equals(fbUser.getEmail())) {
                                        String occupation = occupations[2];
                                        if (map.get("occupation") != null)
                                            occupation = (String) map.get("occupation");

                                        Doctor doc = new Doctor((String) map.get("userId"),
                                                null,
                                                (String) map.get("first_name"),
                                                (String) map.get("last_name"),
                                                (String) map.get("email"),
                                                occupation,
                                                (String) map.get("date_of_birth"));
                                        user = doc;
                                        break;
                                    }
                                }
                            }

                        }
                    });
        }


    }

    private String areTheyEqual(String email1, String email2) {

        for (int i = 0; i < email1.length(); i++) {
            // Log.d(TAG, "" + email1.charAt(i));
            if (email1.charAt(i) != email2.charAt(i))
                return "no, char email1 " + email1.charAt(i) + "char email2 " + email2.charAt(i);
        }
        return "yes";
    }


    private void createUsers() {
        ArrayList<User> users = new ArrayList<User>();
        users.add(new Client("Armando", "testPassword", "Armando",
                "Campa Martínez", "manincampa@gmail.com", null, "1998-01-07"));
        users.add(new Client("Nacho", "testPassword2", "Ignacio",
                "Campa Martínez", "nachofake@email.com", null, "2000-01-11"));
        users.add(new Client("Alex", "testPassword3", "Alejandro",
                "Campa Martínez", "alexfake@email.es", null, "2002-11-04"));
        users.add(new Doctor("Armando", "testPassword4", "Armando",
                "Campa Rodriguez", "armandoDad@fakeemail.ie", occupations[0], "1964-08-18"));


        for (User user : users) {
            createUser(user);
        }
    }

    /*
    create users in the database (for backend testing purposes only)
     */
    private void createUser(User user) {

        Log.d(TAG, "HOOOOOLIIIIII");

        if (user instanceof Client) {//create it as a client
            mAuth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener(actv, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser fbUser = mAuth.getCurrentUser();

                                Map<String, Object> client = new HashMap<String, Object>();
                                client.put("first_name", user.name);
                                client.put("last_name", user.surname);
                                client.put("email", fbUser.getEmail());
                                client.put("date_of_birth", user.birthDate);

                                db.collection("users").document().set(client);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });
        } else if (user instanceof Doctor) {
            mAuth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener(actv, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser fbUser = mAuth.getCurrentUser();

                                Map<String, Object> doctor = new HashMap<String, Object>();
                                doctor.put("first_name", user.name);
                                doctor.put("last_name", user.surname);
                                doctor.put("email", fbUser.getEmail());
                                doctor.put("date_of_birth", user.birthDate);
                                String occupation;
                                if (((Doctor) user).occupation != null)
                                    occupation = ((Doctor) user).occupation;
                                else
                                    occupation = "General";

                                doctor.put("occupation", occupation);

                                db.collection("practitioners").document().set(doctor);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });

        } else {
            Log.d(TAG, "createUserWithEmail:failure, not a client or a doctor");
        }
    }

    public FirebaseAuth getMAuth() {
        return mAuth;
    }


    /*
    @returns Client associated with the email
     */
    public Client getUserData(String email) {
        Client ret = null;

        db.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        //returns a list of hashmaps, but can't parse directly to them
                        List<Object> list = queryDocumentSnapshots.toObjects(Object.class);
                        for (Object obj : list) {
                            Log.d(TAG, "user: ");
                            if (obj instanceof HashMap) {
                                HashMap map = (HashMap) obj;

                                // Log.d(TAG, "signInWithEmail: fbUser " + fbUser.getEmail());
                                // Log.d(TAG, "signInWithEmail: usr " + map.get("email"));
                                // Log.d(TAG, areTheyEqual(map.get("email").toString(), fbUser.getEmail()));
                                if (map.get("email").equals(email)) {
                                    Log.d(TAG, "user found");
                                    Client client = new Client(
                                            //map.get("userID").toString(),
                                            "",
                                            "",
                                            map.get("first_name").toString(),
                                            map.get("last_name").toString(),
                                            email,
                                            null,
                                            map.get("date_of_birth").toString());

                                    user = client;
                                    break;
                                }
                            }
                        }

                    }
                });
        return ret;
    }

    /*
    load insurance data into the database
     */
    public void createInsurance(InsuranceData insurance) {
        Map<String, Object> ins = new HashMap<>();
        ins.put("titular", insurance.getTitular());
        ins.put("start_date", insurance.getStartDate());
        ins.put("renewed_date", insurance.getRenewedDate());
        ins.put("end_date", insurance.getEndDate());
        String str = "";
        for (String person : insurance.getInsuredPeople()) {
            str += person + ",";
        }
        ins.put("insured_people", str);

        db.collection("insurance").document("LA")
                .set(ins)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    /*
    get insurance data from the database
     */
    public InsuranceData retrieveInsurance(String email) {
        InsuranceData insurance = new InsuranceData();
        db.collection("insurance").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Object> list = task.getResult().toObjects(Object.class);
                for (Object obj : list) {
                    if (obj instanceof HashMap) {
                        HashMap map = (HashMap) obj;

                        ArrayList<String> insured = new ArrayList<String>();
                        String[] parseList = ((String) map.get("insured_people")).split(",");
                        for (String str : parseList) {
                            insured.add(str);
                        }
                        if (insured.contains(email)) {
                            insurance.setTitular((String) map.get("titular"));
                            insurance.setStartDate((String) map.get("start_date"));
                            insurance.setEndDate((String) map.get("end_date"));
                            insurance.setRenewedDate((String) map.get("renewed_date"));
                            insurance.setInsuredPeople(insured);
                        }
                    }
                }
            }
        });
        return insurance;
    }

}
