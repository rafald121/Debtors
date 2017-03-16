package com.example.android.debtors.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Fragments.FragmentAllClients;
import com.example.android.debtors.Fragments.FragmentDebtors;
import com.example.android.debtors.Fragments.FragmentPayments;
import com.example.android.debtors.Fragments.FragmentTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Others.CircleTransform;
import com.example.android.debtors.R;
import com.example.android.debtors.Utils.UtilsDatabaseMethods;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    DatabaseClients dbClient;
    DatabaseOwner dbOwner;
    DatabasePayments dbPayment;
    DatabaseTransactions dbTransaction;

    String[] names = {"rafal", "marek", "karol", "adrian", "tomek", "jan", "andrzejek",
            "maniek", "maniok", "chamiok", "krzysztof", "zofia", "alfons", "kamil", "pawel"};
    String[] names2 = {"ania", "ula", "ciocia", "marianna", "ola", "ada", "marysia", "izabela"};
    HashMap<Long, Client> clientsMap = new HashMap<>();

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
//    private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://4.bp.blogspot.com/_SJTl75q21RY/TDWCRlNqnTI/AAAAAAAAAMU/3avdZcJHwSw/s1600/money1.jpg";
    private static final String urlProfileImg = "https://avatars3.githubusercontent.com/u/16782428?v=3&u=d6d5d36732184328f00b7ee90c1ef6f23627005e&s=400";

    // index to identify current nav menu item
    public static int navItemAmount = 4;
    public static int navItemIndex = 0;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    // tags used to attach the fragments
    private static final String TAG_ALL_CLIENTS = "tagAllClients";
    private static final String TAG_DEBTORS = "tagDebtors";
    private static final String TAG_TRANSACTIONS = "tagTransactions";
    private static final String TAG_PAYMENTS = "tagPayments";
    private static final String TAG_SETTINGS = "settings";
    public static String PREVIOUS_TAG = null;
    public static String CURRENT_TAG = TAG_DEBTORS;
    public static String CURRENT_SUB_TAG = null;

    public static boolean whenBackClickedOnDebtors = false; // if user click back button on
    // debtor fragment, whenBackClickedOnDebtors is set to true and when click another time app
    // minimalize

    public HashMap<Integer, Integer> navItemCount;
    UtilsDatabaseMethods databaseMethods;

    private Handler mHandler;
    private FragmentAllClients fragmentAllClients;
    private FragmentDebtors fragmentDebtors;
    private FragmentTransactions fragmentTransactions;
    private FragmentPayments fragmentPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFragments();

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        fab = (FloatingActionButton) findViewById(R.id.fab);

//        ARRAY OF AVAILABLE TAGS FROM NAVIGATION 
        activityTitles = getResources().getStringArray(R.array.nav_item_toolbar_title);
        for (String s : activityTitles)
            Log.i(TAG, "onCreate: " + s.toString());
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.navigation_drawer_header_name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.navigation_drawer_header_mail);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.navigation_drawer_header_background);
        imgProfile = (ImageView) navHeader.findViewById(R.id.navigation_drawer_header_profile);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            Log.i(TAG, "onCreate: savedInstanceState == null");
            navItemIndex = 0;
            PREVIOUS_TAG = CURRENT_TAG;
            CURRENT_TAG = TAG_ALL_CLIENTS;
            loadSelectedFragment();
//          TODO  loadDebtorsFragment();
        }

        loadSelectedFragment();

        databaseMethods = new UtilsDatabaseMethods(getApplicationContext());

        dbClient = new DatabaseClients(getApplicationContext());
        dbOwner = new DatabaseOwner(getApplicationContext());
        dbPayment = new DatabasePayments(getApplicationContext());
        dbTransaction = new DatabaseTransactions(getApplicationContext());

//        deleteDatabase("clientsDatabase");
//        deleteDatabase("paymentsDatabase");
//        deleteDatabase("transactionsDatabase");

//
//        createClients(names);
//        Owner owner = new Owner("Rafal", 500, 200);
//        Owner owner1 = new Owner("Cham", 420, 210);
//
//        dbOwner.createOwner(owner);
//        dbOwner.createOwner(owner1);

//        simulatePayments();
//
//        simulateTransaction();
//        simulateTransactionWithPayment();
    }

    private void initFragments() {
        fragmentAllClients = new FragmentAllClients();
        fragmentDebtors = new FragmentDebtors();
        fragmentPayments = new FragmentPayments();
        fragmentTransactions = new FragmentTransactions();
    }



    private void loadSelectedFragment() {
        Log.i(TAG, "loadSelectedFragment: START");
//        count amount of items in every fragment
        setNavigationDrawerItemAmount();
//        make that navigation item as selected in navigation layout
        selectNavigationMenuToBeChecked();
//      set title of action bar depends on selected fragment
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        Log.i(TAG, "loadSelectedFragment: CURRENT TAG: " + CURRENT_TAG);

        if (PREVIOUS_TAG == CURRENT_TAG) {
            Log.i(TAG, "loadSelectedFragment: wtf");
            drawer.closeDrawers();
            toggleFabOn();
            // show or hide the fab button
//            TODO PRZETESTOWAC
//            toggleFab();
            return;
        }
        Log.i(TAG, "loadSelectedFragment: fragment index before open it: " + navItemIndex);

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: start run");
                // update the main content by replacing fragments
                Fragment fragment = getSelectedFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();

                Log.i(TAG, "run: end run");
            }
        };

// If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);

        } else
            Log.i(TAG, "loadSelectedFragment: IS NULL ");

        // show or hide the fab button
//        toggleFabOn();

        //Closing drawer on item click
        drawer.closeDrawers();

//        TODO TEST IT
        invalidateOptionsMenu();
        Log.i(TAG, "loadSelectedFragment: END");
    }

    private void setNavigationDrawerItemAmount() {

        DatabaseClients _dbClient = new DatabaseClients(getApplicationContext());
        DatabasePayments _dbPayment = new DatabasePayments(getApplicationContext());
        DatabaseTransactions _dbTransaction = new DatabaseTransactions(getApplicationContext());

        int amountAllClients = _dbClient.getAmountOfAllClient();
        int amountDebtors = _dbClient.getDebtorsAmount();
        int amountTransactions = _dbTransaction.getAmountOfTransactions();
        int amountPayments = _dbPayment.getAmountOfPayments();


        TextView itemCounterAllClients = (TextView) navigationView.getMenu().findItem(R.id.nav_all_clients).getActionView();
        if (itemCounterAllClients != null)
            itemCounterAllClients.setText(String.valueOf(amountAllClients));
        else
            Log.e(TAG, "setNavigationDrawerItemAmount: itemCounterAlLClients is null");

        TextView itemCounterDebtors = (TextView) navigationView.getMenu().findItem(R.id.nav_debtors).getActionView();
        if (itemCounterDebtors != null)
            itemCounterDebtors.setText(String.valueOf(amountDebtors));
        else
            Log.e(TAG, "setNavigationDrawerItemAmount: itemCounterDebtors is null");

        TextView itemCounterPayments = (TextView) navigationView.getMenu().findItem(R.id.nav_payments).getActionView();
        if (itemCounterPayments != null)
            itemCounterPayments.setText(String.valueOf(amountPayments));
        else
            Log.e(TAG, "setNavigationDrawerItemAmount: itemCounterPayments is null");

        TextView itemCounterTransactions = (TextView) navigationView.getMenu().findItem(R.id.nav_transactions).getActionView();
        if (itemCounterTransactions != null)
            itemCounterTransactions.setText(String.valueOf(amountTransactions));
        else
            Log.e(TAG, "setNavigationDrawerItemAmount: itemCounterTransaction is null");


    }

    private Fragment getSelectedFragment() {
        Log.i(TAG, "getSelectedFragment: START");
        switch (navItemIndex) {
            case 0:
                Log.i(TAG, "getSelectedFragment: END");
                return fragmentAllClients;
            case 1:
                Log.i(TAG, "getSelectedFragment: END");
                return fragmentDebtors;
            case 2:
                Log.i(TAG, "getSelectedFragment: END");
                return fragmentTransactions;
            case 3:
                Log.i(TAG, "getSelectedFragment: END");
                return fragmentPayments;
            default:
                Log.e(TAG, "getSelectedFragment: ERROR");
                return null;
        }
    }

    //
    private void selectNavigationMenuToBeChecked() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void toggleFabOn() {
//        fab.show();
        switch (navItemIndex){
            case 0:
                if (fragmentAllClients != null) {
                    fragmentAllClients.showFAB();
                } else
                    Log.e(TAG, "toggleFabOn: fragmentAllClients is null");
                break;
            case 1:
                if (fragmentDebtors != null) {
                    fragmentDebtors.showFAB();
                } else
                    Log.e(TAG, "toggleFabOn: fragmentDEBTors is null" );
                break;
            case 2:
                if (fragmentTransactions != null) {
                    Log.i(TAG, "toggleFabOn: fragmentTransactions");
                    fragmentTransactions.showFAB();
                } else
                    Log.e(TAG, "toggleFabOn: fragment transaction is null");
                break;
            case 3:
                if (fragmentPayments != null) {

                    fragmentPayments.showFAB();
                } else
                    Log.e(TAG, "toggleFabOn: fragment payments is null" );

                break;
            default:
                Log.e(TAG, "toggleFabOn: ERROR");
        }
    }

    private void toggleFabOff() {
//        fab.hide();
        switch (navItemIndex){
            case 0:
                if (fragmentAllClients != null) {
                    fragmentAllClients.hideFAB();
                } else
                    Log.e(TAG, "toggleFabOn: fragmentAllClients is null");
                break;
            case 1:
                if (fragmentDebtors != null) {

                    fragmentDebtors.hideFAB();
                } else
                    Log.e(TAG, "toggleFabOn: fragmentDEBTors is null" );
                break;
            case 2:
                if (fragmentTransactions != null) {

                    fragmentTransactions.hideFAB();
                } else
                    Log.e(TAG, "toggleFabOn: fragment transaction is null");
                break;
            case 3:
                if (fragmentPayments != null) {

                    fragmentPayments.hideFAB();
                } else
                    Log.e(TAG, "toggleFabOn: fragment payments is null" );

                break;
            default:
                Log.e(TAG, "toggleFabOn: ERROR");
        }
//        if (fragmentTransactions != null) {
//            fragmentTransactions.hideFab();
//        }
    }

    private void setUpNavigationView() {
        Log.i(TAG, "setUpNavigationView: START");
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.i(TAG, "onNavigationItemSelected: START");
                Log.i(TAG, "onNavigationItemSelected: TAG BEFORE CLICK: " + CURRENT_TAG);
                //Check to see which item was being clicked and perform appropriate action

                if (menuItem.getItemId() != R.id.nav_debtors) // if user hasn't check debtors in
                    // navigation asign whenBackClickedOnDebtors=false again
                    whenBackClickedOnDebtors = false;

                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_all_clients:
                        navItemIndex = 0;
                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = TAG_ALL_CLIENTS;
                        Log.i(TAG, "onNavigationItemSelected: END");
                        break;
                    case R.id.nav_debtors:
                        navItemIndex = 1;
                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = TAG_DEBTORS;
                        Log.i(TAG, "onNavigationItemSelected: END");
                        break;
                    case R.id.nav_transactions:
                        navItemIndex = 2;
                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = TAG_TRANSACTIONS;
                        Log.i(TAG, "onNavigationItemSelected: END");
                        break;
                    case R.id.nav_payments:
                        navItemIndex = 3;
                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = TAG_PAYMENTS;
                        Log.i(TAG, "onNavigationItemSelected: END");
                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent(MainActivity.this, ActivitySettings.class));
                        drawer.closeDrawers();
                        Log.i(TAG, "onNavigationItemSelected: END");
                        return true;
                    case R.id.nav_about_me:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutMe.class));
                        drawer.closeDrawers();
                        Log.i(TAG, "onNavigationItemSelected: END");
                        return true;
                    default:
                        navItemIndex = 0;

                }
                Log.i(TAG, "onNavigationItemSelected: selected index: " + navItemIndex);
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadSelectedFragment();
                Log.i(TAG, "onNavigationItemSelected: END2!");
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        Log.i(TAG, "onDrawerClosed: ");
                        // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                        toggleFabOn();
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        Log.i(TAG, "onDrawerOpened: ");
                        // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                        toggleFabOff();
                        super.onDrawerOpened(drawerView);
                    }
                };

        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        Log.i(TAG, "setUpNavigationView: END");
    }

    @Override
    public void onBackPressed() {
//        TODO when clicked two times ask if quit app
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 1) { // jeśli navItem nie jest 1
                navItemIndex = 1;
                PREVIOUS_TAG = CURRENT_TAG;
                CURRENT_TAG = TAG_DEBTORS;
                loadSelectedFragment();
                return;
            } else { // jesli navItem jest 1
                if (!whenBackClickedOnDebtors) { //if false, change variable to true and return
                    // nothing(do anything)
                    whenBackClickedOnDebtors = true;
                    return;
                } else { // if true user have clicked back button once and next time minimalize app
//                    minimalize app
                }
            }
        }

        super.onBackPressed();
    }


    private void loadNavHeader() {

        // name, website
        txtName.setText("Rafał Dołęga");
        txtWebsite.setText("rafald121@gmail.com");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);


        // showing dot next to notifications label
//        TODO wklepać aby zamiast kropki pokazywaly się liczby
//        TextView amount = (TextView) findViewById(R.layout.navigation_drawer_item_counter);
//        navigationView.getMenu().getItem(2).setAct
//        navigationView.getMenu().getItem(3).setActionView(R.layout.dot_test);
    }


    public void hideFABs() {

    }


    public void createClients(String[] names) {
        for (int i = 0; i < names.length - 1; i++) {

            Client client = new Client(names[i], 100 * i);
            dbClient.createClient(client);

        }
    }
}

