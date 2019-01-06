package xyz.eeckhout.smartcity.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.DecodeException;
import com.auth0.android.jwt.JWT;

import xyz.eeckhout.smartcity.ApiException;
import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.TokenHelper;
import xyz.eeckhout.smartcity.api.AccountsApi;
import xyz.eeckhout.smartcity.model.UserLoginDTO;
import xyz.eeckhout.smartcity.model.UserMinalInfoDTO;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private int init_fragment;
    private UserMinalInfoDTO user;

    public UserMinalInfoDTO getUser() {
        return user;
    }

    public void setUser(UserMinalInfoDTO user) {
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkIsLogged();
        enableMyLocationIfPermitted();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new GetUserTask().execute();

        if(savedInstanceState != null){
            startFragment( (int) savedInstanceState.getLong("fragment_actif", R.id.nav_camera));
        }
        else{
            startFragment();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // La permission est garantie
                } else {
                    // La permission est refusée
                }
                break;
            }
        }
    }

    private void getMapFragment(){
        if (Utils.isDataConnectionAvailable(getApplicationContext())) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, MapFragment.newInstance())
                    .commit();
        } else {
            getInternetErrorFragment();
            Toast.makeText(getApplicationContext(), R.string.error_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void getAccountFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new AccountFragment())
                .commit();
    }

    public void getMapPreferenceFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new MapSettingFragment())
                .commit();
    }

    public void getServiceFragment(){
        init_fragment = R.id.service_layout;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new ServiceFragment())
                .commit();
    }

    private void getInternetErrorFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new InternetConnectionErrorFragment())
                .commit();
    }

    private void getRGPDFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new RGPDFragment())
                .commit();
    }


    private void getCGUFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new CGUFragment())
                .commit();
    }

    private void getAuthorsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new AuthorsFragment())
                .commit();
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

    private void startFragment(int id){
        setTitle(getString(R.string.app_name));
        if (id == R.id.nav_map) {
            init_fragment = R.id.nav_map;
            getMapFragment();
        } else if (id == R.id.nav_settings) {
            init_fragment = R.id.nav_settings;
            getMapPreferenceFragment();
        } else if (id == R.id.nav_rgpd) {
            init_fragment = R.id.nav_rgpd;
            getRGPDFragment();
        } else if (id == R.id.nav_cgu) {
            init_fragment = R.id.nav_cgu;
            getCGUFragment();
        } else if (id == R.id.nav_authors) {
            init_fragment = R.id.nav_authors;
            getAuthorsFragment();
        } else if (id == R.id.service_layout) {
            getServiceFragment();
        }
    }

    public void checkIsLogged(){
        Boolean isConnected =  TokenHelper.isTokenValid(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("accessToken",""));
        if(!isConnected){
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove("accessToken").commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void startFragment(){
        startFragment(R.id.nav_map);
    }

    public void showBottomSheetDialogFragment() {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        startFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("fragment_actif", init_fragment);
    }

    public class GetUserTask extends AsyncTask<Void, Void, UserMinalInfoDTO> {

        private UserLoginDTO loginDTO;

        GetUserTask() {
        }

        @Override
        protected UserMinalInfoDTO doInBackground(Void... params) {
            JWT token;
            String jwt = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("accessToken", null);
            try {
                token = new JWT(jwt);
                String uid = token.getClaim("uid").asString();
                AccountsApi api = new AccountsApi();
                api.getApiClient().setAccessToken(jwt);
                UserMinalInfoDTO user = api.getUserById(uid);
                return user;
            }
            catch(DecodeException e){
                Log.i("erreur", e.getMessage());
            } catch (ApiException e) {
                Log.i("erreur", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(final UserMinalInfoDTO user) {
            if (user != null) {
                TextView userName = (TextView) findViewById(R.id.nav_userName);
                TextView email = (TextView) findViewById(R.id.nav_userEmail);
                email.setText(user.getEmail());
                if(user.getFirstName() != null && !user.getFirstName().isEmpty()){
                    userName.setText(user.getFirstName() + " "+ user.getLastName());
                }
                else {
                    userName.setText(user.getUserName());
                }

                Button logout = findViewById(R.id.button_logout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove("accessToken").commit();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
            setUser(user);
        }

        @Override
        protected void onCancelled() {
        }
    }
}
