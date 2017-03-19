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
import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Enum.FragmentsIDs;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Fragments.FragmentAllClients;
import com.example.android.debtors.Fragments.FragmentDebtors;
import com.example.android.debtors.Fragments.FragmentPayments;
import com.example.android.debtors.Fragments.FragmentTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Others.CircleTransform;
import com.example.android.debtors.R;
import com.example.android.debtors.Utils.UtilsDatabaseMethods;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    DatabaseClients dbClient;
    DatabaseOwner dbOwner;
    DatabasePayments dbPayment;
    DatabaseTransactions dbTransaction;

//    String[] names = {"rafal", "marek", "karol", "adrian", "tomek", "jan", "andrzejek",
//            "maniek", "maniok", "chamiok", "krzysztof", "zofia", "alfons", "kamil", "pawel"};
//    String[] names2 = {"ania", "ula", "ciocia", "marianna", "ola", "ada", "marysia", "izabela"};

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;

    private static final String urlNavHeaderBg = "http://4.bp.blogspot.com/_SJTl75q21RY/TDWCRlNqnTI/AAAAAAAAAMU/3avdZcJHwSw/s1600/money1.jpg";
    private static final String urlProfileImg = "https://avatars3.githubusercontent.com/u/16782428?v=3&u=d6d5d36732184328f00b7ee90c1ef6f23627005e&s=400";

    public static int navItemIndex = 0;

    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;

    // tags used to attach the fragments
    public static int fragmentID = -1 ;
    public static int previousFragmentID = -1;
    public static int subFragmentID = -1 ;
    public static int previousSubFragmentID = -1;


    public static final String TAG_ALL_CLIENTS = String.valueOf(R.string.tag_allclients);
    private static final String TAG_DEBTORS = String.valueOf(R.string.tag_debtors);
    private static final String TAG_TRANSACTIONS = String.valueOf(R.string.tag_transactions);
    private static final String TAG_PAYMENTS = String.valueOf(R.string.tag_payments);
    private static final String TAG_SETTINGS = String.valueOf(R.string.tag_settings);
    public static String PREVIOUS_TAG = null;
    public static String CURRENT_TAG = TAG_DEBTORS;
    public static String CURRENT_SUB_TAG = null;

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

        activityTitles = getResources().getStringArray(R.array.nav_item_toolbar_title);

        EventBus myEventBus = EventBus.getDefault();

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.navigation_drawer_header_name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.navigation_drawer_header_mail);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.navigation_drawer_header_background);
        imgProfile = (ImageView) navHeader.findViewById(R.id.navigation_drawer_header_profile);

        loadNavHeader();

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;

            previousFragmentID = fragmentID;
            fragmentID = FragmentsIDs.ALLCLIENTS;

            PREVIOUS_TAG = CURRENT_TAG;
            CURRENT_TAG = TAG_ALL_CLIENTS;

            loadSelectedFragment();
//          TODO  loadDebtorsFragment();
        }

        loadSelectedFragment();

        dbClient = new DatabaseClients(getApplicationContext());
        dbOwner = new DatabaseOwner(getApplicationContext());
        dbPayment = new DatabasePayments(getApplicationContext());
        dbTransaction = new DatabaseTransactions(getApplicationContext());

    }

    private void initFragments() {
        fragmentAllClients = new FragmentAllClients();
        fragmentDebtors = new FragmentDebtors();
        fragmentPayments = new FragmentPayments();
        fragmentTransactions = new FragmentTransactions();
    }

    private void loadSelectedFragment() {
        Log.i(TAG, "loadSelectedFragment: START");
        setNavigationDrawerItemAmount();
        selectNavigationMenuToBeChecked();
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (PREVIOUS_TAG == CURRENT_TAG) {
            Log.i(TAG, "loadSelectedFragment: wtf");
            drawer.closeDrawers();
            fabOn();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: start run");
                // update the main content by replacing fragments
                Fragment fragment = getSelectedFragment();
                if(fragment.isAdded()) {
                    Log.i(TAG, "run: ");
                    return;
                }
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
        TextView itemCounterDebtors = (TextView) navigationView.getMenu().findItem(R.id.nav_debtors).getActionView();
        TextView itemCounterPayments = (TextView) navigationView.getMenu().findItem(R.id.nav_payments).getActionView();
        TextView itemCounterTransactions = (TextView) navigationView.getMenu().findItem(R.id.nav_transactions).getActionView();

        if (itemCounterAllClients != null) {
            itemCounterAllClients.setText(String.valueOf(amountAllClients));
        }
        else {
            Log.e(TAG, "setNavigationDrawerItemAmount: itemCounterAlLClients is null");
        }

        if (itemCounterDebtors != null) {
            itemCounterDebtors.setText(String.valueOf(amountDebtors));
        }
        else {
            Log.e(TAG, "setNavigationDrawerItemAmount: itemCounterDebtors is null");
        }

        if (itemCounterPayments != null) {
            itemCounterPayments.setText(String.valueOf(amountPayments));
        }
        else {
            Log.e(TAG, "setNavigationDrawerItemAmount: itemCounterPayments is null");
        }

        if (itemCounterTransactions != null)
            itemCounterTransactions.setText(String.valueOf(amountTransactions));
        else
            Log.e(TAG, "setNavigationDrawerItemAmount: itemCounterTransaction is null");
    }

    private Fragment getSelectedFragment() {
        Log.i(TAG, "getSelectedFragment: START");
        switch (navItemIndex) {
            case 0:
                return fragmentAllClients;
            case 1:
                return fragmentDebtors;
            case 2:
                return fragmentTransactions;
            case 3:
                return fragmentPayments;
            default:
                Log.e(TAG, "getSelectedFragment: ERROR");
                return null;
        }
    }

    private void selectNavigationMenuToBeChecked() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void setUpNavigationView() {
        Log.i(TAG, "setUpNavigationView: START");
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                initFragments();

                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_all_clients:
                        navItemIndex = 0;

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDs.ALLCLIENTS;
                        subFragmentID = FragmentsIDs.ALLCLIENTS;

                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = TAG_ALL_CLIENTS;

                        break;
                    case R.id.nav_debtors:
                        navItemIndex = 1;

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDs.DEBTORS;
                        subFragmentID = FragmentsIDs.DEBTORSFORME;

                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = TAG_DEBTORS;

                        break;
                    case R.id.nav_transactions:
                        navItemIndex = 2;

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDs.TRANSACTIONS;
                        subFragmentID = FragmentsIDs.TRANSACTIONSALL;

                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = TAG_TRANSACTIONS;
                        break;

                    case R.id.nav_payments:
                        navItemIndex = 3;

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDs.PAYMENTS;
                        subFragmentID = FragmentsIDs.PAYMENTSALL;

                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = TAG_PAYMENTS;

                        break;

                    case R.id.nav_settings:

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDs.SETTINGS;

                        startActivity(new Intent(MainActivity.this, ActivitySettings.class));
                        return true;

                    case R.id.nav_about_me:

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDs.ABOUTME;

                        startActivity(new Intent(MainActivity.this, AboutMe.class));
                        return true;

                    default:
                        Log.e(TAG, "onNavigationItemSelected: ERROR");
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
                Log.i(TAG, "onNavigationItemSelected: END");
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        fabOn();
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        fabOff();
                        super.onDrawerOpened(drawerView);
                    }
                };

        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        Log.i(TAG, "setUpNavigationView: END");
    }

//    @Override
//    public void onBackPressed() {
////        TODO when clicked two times ask if quit app
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawers();
//            return;
//        }
//
//        // This code loads home fragment when back key is pressed
//        // when user is in other fragment than home
//        if (shouldLoadHomeFragOnBackPress) {
//            // checking if user is on other navigation menu
//            // rather than home
//            if (navItemIndex != 1) { // jeśli navItem nie jest 1
//                navItemIndex = 1;
//
//                previousFragmentID = fragmentID;
//                fragmentID = FragmentsIDs.DEBTORS;
//
//                PREVIOUS_TAG = CURRENT_TAG;
//                CURRENT_TAG = TAG_DEBTORS;
//                loadSelectedFragment();
//                return;
//            } else { // jesli navItem jest 1
//                if (!whenBackClickedOnDebtors) { //if false, change variable to true and return
//                    // nothing(do anything)
//                    whenBackClickedOnDebtors = true;
//                    return;
//                } else { // if true user have clicked back button once and next time minimalize app
////                    minimalize app
//                }
//            }
//        }
//
//        super.onBackPressed();
//    }


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
    }

    private void fabOn(){
        EventBus.getDefault().post(new ToggleFabWhenDrawerMove(true));
    }

    private void fabOff(){
        EventBus.getDefault().post(new ToggleFabWhenDrawerMove(false));
    }

}

