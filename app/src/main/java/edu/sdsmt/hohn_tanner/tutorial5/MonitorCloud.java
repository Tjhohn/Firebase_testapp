package edu.sdsmt.hohn_tanner.tutorial5;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MonitorCloud {

    public final static MonitorCloud INSTANCE = new MonitorCloud();


    private static final String USER = "name";
    private static final String EMAIL = "fake@email.com";
    private static final String PASSWORD = "12345678";
    private static final String TAG = "monitor";

    // Firebase instance variables
    private final FirebaseAuth userAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;
    private final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

    private boolean authenticated = false;

    public boolean isAuthenticated(){
        return authenticated;
    }

    /**
     *  private to defeat instantiation.
     */
    private MonitorCloud() {

    }
}
