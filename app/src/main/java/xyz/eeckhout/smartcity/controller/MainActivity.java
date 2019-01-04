package xyz.eeckhout.smartcity.controller;

import android.Manifest;
import android.accounts.AccountAuthenticatorActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.DecodeException;
import com.auth0.android.jwt.JWT;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

import butterknife.OnClick;
import xyz.eeckhout.smartcity.ApiClient;
import xyz.eeckhout.smartcity.ApiException;
import xyz.eeckhout.smartcity.LoginActivity;
import xyz.eeckhout.smartcity.api.AccountsApi;
import xyz.eeckhout.smartcity.api.JwtApi;
import xyz.eeckhout.smartcity.controller.AccountFragment;
import xyz.eeckhout.smartcity.controller.BottomSheetFragment;
import xyz.eeckhout.smartcity.controller.Map_settingFragment;
import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.model.TokenDTO;
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

        enableMyLocationIfPermitted();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AVEC LE FRAGMENT
                showBottomSheetDialogFragment();

            }
        });

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
                    .replace(R.id.content, MapsFragment.newInstance())
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
                .replace(R.id.content, new Map_settingFragment())
                .commit();
    }

    private void getInternetErrorFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new InternetConnectionErrorFragment())
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private void startFragment(int id){
        if (id == R.id.nav_camera) {
            init_fragment = R.id.nav_camera;
            getMapFragment();
        } else if (id == R.id.nav_gallery) {
            init_fragment = R.id.nav_gallery;
            getMapPreferenceFragment();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            init_fragment = R.id.nav_manage;
            getAccountFragment();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
    }

    private void startFragment(){
        startFragment(R.id.nav_camera);
    }

    public void showBottomSheetDialogFragment() {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void showBottomSheetDialogFragment(Marker marker) {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();


        TextView view = findViewById(R.id.preview);
        //view.setText(marker.getTitle());
        //bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
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
                //api.getApiClient().setAccessToken(jwt);
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
            }
            setUser(user);
        }

        @Override
        protected void onCancelled() {
        }
    }
}
