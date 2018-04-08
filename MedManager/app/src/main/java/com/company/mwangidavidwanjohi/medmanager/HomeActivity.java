package com.company.mwangidavidwanjohi.medmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.company.mwangidavidwanjohi.medmanager.fragments.ActiveMedicationFragment;
import com.company.mwangidavidwanjohi.medmanager.fragments.AddMedicineFragment;
import com.company.mwangidavidwanjohi.medmanager.fragments.CompletedMedicationFragment;
import com.company.mwangidavidwanjohi.medmanager.fragments.ProfileFragment;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.UserProfile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    UserProfile userProfile=null;
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }
    public void updateUI(GoogleSignInAccount a){
        if (a==null){
            //not signed in
            //launch main activity
          Intent intent=new Intent(this,MainActivity.class);
          startActivity(intent);
        }else {
              userProfile=SQLite.select().from(UserProfile.class).querySingle();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the dbflow
        FlowManager.init(this);


        //set the main fragment as the fragment that contains the active medication
        ActiveMedicationFragment activeMedicationFragment=new ActiveMedicationFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame,activeMedicationFragment);
        transaction.commit();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new Medicine creation
                AddMedicineFragment newMedicine=new AddMedicineFragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame,newMedicine);
//                transaction.addToBackStack(null);
                transaction.commit();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_medication) {
            // new Medicine creation
            AddMedicineFragment newMedicine=new AddMedicineFragment();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame,newMedicine);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.active_medication) {

            //active medication which is the default fragment that launches when app is launched
            ActiveMedicationFragment activeMedicationFragment=new ActiveMedicationFragment();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame,activeMedicationFragment);
            transaction.commit();
        } else if (id == R.id.completed_medication) {

            //completed medication fragment
            CompletedMedicationFragment completedMedicationFragment=new CompletedMedicationFragment();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame,completedMedicationFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.profile) {
            //go to the profile fragment
            ProfileFragment profileFragment=new ProfileFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame,profileFragment);
//            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
        }
//        else if (id==R.id.nav_user_name){
//            if (userProfile!=null){
//                TextView nav_user_name=(TextView)findViewById(R.id.nav_user_name);
//                nav_user_name.setText(userProfile.name);
//            }
//        }else if (id==R.id.nav_user_email){
//            if (userProfile!=null){
//                TextView nav_user_email=(TextView)findViewById(R.id.nav_user_email);
//                nav_user_email.setText(userProfile.email);
//            }
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
