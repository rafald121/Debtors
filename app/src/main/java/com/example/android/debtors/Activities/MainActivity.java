package com.example.android.debtors.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Enum.FragmentsIDsAndTags;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Fragments.FragmentAllClients;
import com.example.android.debtors.Fragments.FragmentDebtors;
import com.example.android.debtors.Fragments.FragmentPayments;
import com.example.android.debtors.Fragments.FragmentTransactions;
import com.example.android.debtors.R;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseClients dbClient;
    private DatabaseOwner dbOwner;
    private DatabasePayments dbPayment;
    private DatabaseTransactions dbTransaction;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;

    private TextView amountForMe, amountMeToOther;
    private Toolbar toolbar;

    private static final String urlNavHeaderBg = "http://4.bp.blogspot.com/_SJTl75q21RY/TDWCRlNqnTI/AAAAAAAAAMU/3avdZcJHwSw/s1600/money1.jpg";

    private String[] activityTitles;

    // tags used to attach the fragments
    public static int fragmentID = -1 ;
    public static int previousFragmentID = -1;
    public static int previousSubFragmentID = -1;


    public static final String TAG_ALL_CLIENTS = String.valueOf(R.string.tag_allclients);
    public static final String TAG_DEBTORS = String.valueOf(R.string.tag_debtors);
    public static final String TAG_TRANSACTIONS = String.valueOf(R.string.tag_transactions);
    public static final String TAG_PAYMENTS = String.valueOf(R.string.tag_payments);
    public static final String TAG_SETTINGS = String.valueOf(R.string.tag_settings);
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

        dbClient = new DatabaseClients(getApplicationContext());
        dbOwner = new DatabaseOwner(getApplicationContext());
        dbPayment = new DatabasePayments(getApplicationContext());
        dbTransaction = new DatabaseTransactions(getApplicationContext());

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        activityTitles = getResources().getStringArray(R.array.nav_item_toolbar_title);

        EventBus myEventBus = EventBus.getDefault();

        navHeader = navigationView.getHeaderView(0);
        amountForMe = (TextView) navHeader.findViewById(R.id.navigation_drawer_header_all_amount_for_me);
        amountMeToOther = (TextView) navHeader.findViewById(R.id.navigation_drawer_header_all_amount_me_to_other);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.navigation_drawer_header_background);

        loadNavHeader();
        countWholeDebtsAmounInHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            MainActivity.fragmentID = 0;

            previousFragmentID = fragmentID;
            fragmentID = FragmentsIDsAndTags.ALLCLIENTS;

            PREVIOUS_TAG = CURRENT_TAG;
            CURRENT_TAG = TAG_ALL_CLIENTS;

            loadSelectedFragment();
//          TODO  loadDebtorsFragment();
        }

        loadSelectedFragment();

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

        if (PREVIOUS_TAG == CURRENT_TAG) {
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

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);

        } else
            Log.i(TAG, "loadSelectedFragment: IS NULL ");

        drawer.closeDrawers();

        invalidateOptionsMenu();

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

        switch (MainActivity.fragmentID) {

            case FragmentsIDsAndTags.ALLCLIENTS:
                return fragmentAllClients;

            case FragmentsIDsAndTags.DEBTORS:
                return fragmentDebtors;

            case FragmentsIDsAndTags.TRANSACTIONS:
                return fragmentTransactions;

            case FragmentsIDsAndTags.PAYMENTS:
                return fragmentPayments;

            default:
                Log.e(TAG, "getSelectedFragment: ERROR");
                Log.i(TAG, "getSelectedFragment: fragment ID: " + MainActivity.fragmentID);
                return null;
        }
    }

    private void selectNavigationMenuToBeChecked() {
        navigationView.getMenu().getItem(MainActivity.fragmentID).setChecked(true);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[fragmentID]);
    }

    private void setUpNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                initFragments();

                switch (menuItem.getItemId()) {

                    case R.id.nav_all_clients:

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDsAndTags.ALLCLIENTS;

                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = FragmentsIDsAndTags.TAG_ALLCLIENTS;

                        break;
                    case R.id.nav_debtors:

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDsAndTags.DEBTORS;

                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = FragmentsIDsAndTags.TAG_DEBTORS;

                        break;
                    case R.id.nav_transactions:

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDsAndTags.TRANSACTIONS;
                        CURRENT_TAG = FragmentsIDsAndTags.TAG_TRANSACTIONS;

                        break;

                    case R.id.nav_payments:

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDsAndTags.PAYMENTS;

                        PREVIOUS_TAG = CURRENT_TAG;
                        CURRENT_TAG = FragmentsIDsAndTags.TAG_PAYMENTS;

                        break;

                    case R.id.nav_settings:

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDsAndTags.SETTINGS;

                        startActivity(new Intent(MainActivity.this, ActivitySettings.class));
                        return true;

                    case R.id.nav_about_me:

                        previousFragmentID = fragmentID;
                        fragmentID = FragmentsIDsAndTags.ABOUTME;

                        startActivity(new Intent(MainActivity.this, AboutMe.class));
                        return true;

                    default:
                        Log.e(TAG, "onNavigationItemSelected: ERROR");

                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadSelectedFragment();
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

        actionBarDrawerToggle.syncState();
    }

    private void loadNavHeader() {

        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

    }

    private void fabOn(){
        EventBus.getDefault().post(new ToggleFabWhenDrawerMove(true));
    }


    private void fabOff(){
        EventBus.getDefault().post(new ToggleFabWhenDrawerMove(false));
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        if (isKeyboardOpen()) {
            closeKeyboard();
            return;
        }

        int count = getFragmentManager().getBackStackEntryCount();
        Log.i(TAG, "onBackPressed: count: " + count);
//
//        if (count == 0) {
//            super.onBackPressed();
//        }
//}
//            android.app.Fragm2entTransaction ft = getFragmentManager().beginTransaction();
//            android.app.Fragment prev = getFragmentManager().findFragmentByTag(PREVIOUS_TAG);
//            android.app.Fragment curr = getFragmentManager().findFragmentByTag(CURRENT_TAG);
//            if(curr!=null)
//                ft.remove(curr);
//
//            ft.addToBackStack(null);
//            ft.replace(R.id.frame, prev, PREVIOUS_TAG);
//            ft.commit();
//
//
//            Log.i(TAG, "onBackPressed: current subfragment: " + MainActivity.subFragmentID);
//            Log.i(TAG, "onBackPressed: current fragment: " + MainActivity.fragmentID);
//            setToolbarTitle();
////TODO tutaj powinno sie cofac na podstawie historii fragmentow
//
//        } else {
//            getFragmentManager().popBackStack();
//        }

    }

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
//                fragmentID = FragmentsIDsAndTags.DEBTORS;
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
//        }
//
//        super.onBackPressed();
//    }
    private void countWholeDebtsAmounInHeader() {
        int forme = dbClient.getAmountForMe();
        int metoother = dbClient.getAmountMeToOther();

        amountForMe.append(String.valueOf(forme));
        amountMeToOther.append(String.valueOf(metoother));

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isKeyboardOpen() {
        Rect r = new Rect();
        View view = this.findViewById(android.R.id.content);
        view.getWindowVisibleDisplayFrame(r);

        int screenHeight = view.getRootView().getHeight();

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        int keypadHeight = screenHeight - r.bottom;

        Log.d(TAG, "keypadHeight = " + keypadHeight);

        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
           return true;
        }
        else {
            return false;
        }


    }
}

