package com.company.mwangidavidwanjohi.medmanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.company.mwangidavidwanjohi.medmanager.models.UserProfile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingProgressBar;
    private static final int RC_SIGN_IN=9001;
    private FirebaseAuth mAuth;

    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void updateUI(FirebaseUser a){
        if (a!=null){
            //signed in
          saveUserDetails();
        //launch new activity
            Intent i=new Intent(this,HomeActivity.class);
            startActivity(i);

        }else{
            //not signed in
        }
    }

    public void  saveUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user!=null){
            //user signed in

        for (UserInfo profile : user.getProviderData()) {
            // Id of the provider (ex: google.com)
            String providerId = profile.getProviderId();

            // UID specific to the provider
            String uid = profile.getUid();

            // Name, email address, and profile photo Url
            String name = profile.getDisplayName();
            String email = profile.getEmail();
//            Uri photoUrl = profile.getPhotoUrl();

            //save the record using dbflow

            UserProfile loggedUser=new UserProfile();
            loggedUser.id=1;
            loggedUser.name=name;
            loggedUser.email=email;
            loggedUser.save();
        }
    }else{
//        no user signed in
        System.exit(1);
    }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadingProgressBar=(ProgressBar)findViewById(R.id.loading_progress_bar);

        //dbflow initialization
        FlowManager.init(this);

        //check for permission to be granted before the user continues
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {android.Manifest.permission.RECEIVE_BOOT_COMPLETED, android.Manifest.permission.INTERNET};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        //google sign in
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //determine if the user is signed in or not
         mAuth = FirebaseAuth.getInstance();


//        button that handles the sign in of user
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        loadingProgressBar.setVisibility(View.VISIBLE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loadingProgressBar.setVisibility(View.GONE);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getApplicationContext(),"Google sign in failed",Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }
             private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

                 AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                 mAuth.signInWithCredential(credential)
                         .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     // Sign in success, update UI with the signed-in user's information
//                                     Log.d(TAG, "signInWithCredential:success");
                                     FirebaseUser user = mAuth.getCurrentUser();
                                     updateUI(user);
                                 } else {
                                     // If sign in fails, display a message to the user.
                                     updateUI(null);
                                 }
                                 }
                         });
             }



             @Override
             public void onBackPressed() {
                 super.onBackPressed();
                 finish();
             }

             //permission checker when the app is opened

             public static boolean hasPermissions(Context context, String... permissions)
             {
                 if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
                 {
                     for (String permission : permissions)
                     {
                         if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                         {
                             return false;
                         }
                     }
                 }
                 return true;
             }
         }
