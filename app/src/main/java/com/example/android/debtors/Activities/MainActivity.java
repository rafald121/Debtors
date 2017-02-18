package com.example.android.debtors.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import com.example.android.debtors.Logic.RealizePaymentHelper;
import com.example.android.debtors.Logic.RealizeTransactionHelper;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.Others.CircleTransform;
import com.example.android.debtors.R;
import com.example.android.debtors.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.util.Log.i;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    DatabaseClients dbClient;
    DatabaseOwner dbOwner;
    DatabasePayments dbPayment;
    DatabaseTransactions dbTransaction;

    String[] names = {"rafal", "marek", "karol", "adrian" , "tomek" , "jan", "andrzejek",
            "maniek", "maniok", "chamiok"};
    HashMap<Long,Client> clientsMap = new HashMap<>();

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://4.bp.blogspot.com/_SJTl75q21RY/TDWCRlNqnTI/AAAAAAAAAMU/3avdZcJHwSw/s1600/money1.jpg";
    private static final String urlProfileImg = "https://avatars3.githubusercontent.com/u/16782428?v=3&u=d6d5d36732184328f00b7ee90c1ef6f23627005e&s=400";

    // index to identify current nav menu item
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
    public static String CURRENT_TAG = TAG_DEBTORS;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

//        ARRAY OF AVAILABLE TAGS FROM NAVIGATION 
        activityTitles = getResources().getStringArray(R.array.nav_item_toolbar_title);
        for(String s: activityTitles)
            Log.i(TAG, "onCreate: " + s.toString());
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.navigation_drawer_header_name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.navigation_drawer_header_mail);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.navigation_drawer_header_background);
        imgProfile = (ImageView) navHeader.findViewById(R.id.navigation_drawer_header_profile);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_DEBTORS;
            loadSelectedFragment();
//          TODO  loadDebtorsFragment();
        }



        dbClient = new DatabaseClients(getApplicationContext());
        dbOwner = new DatabaseOwner(getApplicationContext());
        dbPayment = new DatabasePayments(getApplicationContext());
        dbTransaction = new DatabaseTransactions(getApplicationContext());
//        dbPayment.deletePaymentInRange(15,20);
//        simulatePayments();

//        List<Client> listOfClient = new ArrayList<>();
//        List<Client> listOfAllClientFromDatabase = new ArrayList<>();
//        List<Client> listOfClientWithLeftAmountFromTo = new ArrayList<>();
//        List<Client> listOfUserByName = new ArrayList<>();
//        listOfAllClientFromDatabase = getAllClients();
//        listOfClientWithLeftAmountFromTo = getClientInLeftAmountRange();
//        List<Payment> listOfAllPayments = getPayments();
//        List<Payment> listOfPaymentsByClientId = getPaymentByClientId(2);
//        List<Payment>
//        List<TransactionForClient> listOfTransactionByClientID = getTransactionByClientId(7);
//        List<TransactionForClient> listOfTransactionByOwnerID = getTransactionByOwnerId(2);
//        List<Owner> listOfAllOwners = getOwner();


//        simulateTransaction();
//        simulateTransactionWithPayment();


//        List<Payment> listOfAllPayments = getPayments();
//        List<Payment> listOfPaymentsByClientId = getPaymentByClientId(2);
//        List<Payment> listOfPaymentsByOwnerId = getPaymentByOwnerId(1);

//        List<TransactionForClient> listOfAllTransaction = getTransaction();

//        Log.i(TAG, "onCreate: listOfPaymentsByClientId" + listOfPaymentsByClientId.toString();
//        simulatePayments(owner,clientJurand);
//        simulateTransaction();
//        simulateTransactionWithPayment();
    }

    private void loadSelectedFragment(){
        Log.i(TAG, "loadSelectedFragment: START");
//        make that navigation item as selected in navigation layout
        selectNavigationMenuToBeChecked();
//      set title of action bar depends on selected fragment
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
//            TODO PRZETESTOWAC
            toggleFab();
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
            Log.i(TAG, "loadSelectedFragment: mPendingRunnable is not null and go on: ");
            mHandler.post(mPendingRunnable);
        } else
            Log.i(TAG, "loadSelectedFragment: IS NULL ");

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

//        TODO TEST IT
        invalidateOptionsMenu();
        Log.i(TAG, "loadSelectedFragment: END");
    }


    private Fragment getSelectedFragment() {
        Log.i(TAG, "getSelectedFragment: START");
        switch (navItemIndex) {
            case 0:
                // all clients
                FragmentAllClients allClients = new FragmentAllClients();
                Log.i(TAG, "getSelectedFragment: END");
                return allClients;
            case 1:
                // debtors
                FragmentDebtors debtors = new FragmentDebtors();
                Log.i(TAG, "getSelectedFragment: END");
                return debtors;
            case 2:
                // transactions
                FragmentTransactions transactions = new FragmentTransactions();
                Log.i(TAG, "getSelectedFragment: END");
                return transactions;
            case 3:
                // payments
                FragmentPayments payments = new FragmentPayments();
                Log.i(TAG, "getSelectedFragment: END");
                return payments;
            default:
//                debtors fragment is default
                Log.i(TAG, "getSelectedFragment: END");
                return new FragmentDebtors();
        }
    }
//
    private void selectNavigationMenuToBeChecked(){
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle(){
        Log.i(TAG, "setToolbarTitle: index" + navItemIndex);
        Log.i(TAG, "setToolbarTitle: value for index" + activityTitles[navItemIndex]);
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }
    private void toggleFab(){
        if(navItemIndex == 0 )
            fab.show();
        else
            fab.hide();
    }
    private void setUpNavigationView() {
        Log.i(TAG, "setUpNavigationView: START");
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.i(TAG, "onNavigationItemSelected: START");
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_all_clients:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_ALL_CLIENTS;
                        Log.i(TAG, "onNavigationItemSelected: END");
                        break;
                    case R.id.nav_debtors:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_DEBTORS;
                        Log.i(TAG, "onNavigationItemSelected: END");
                        break;
                    case R.id.nav_transactions:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_TRANSACTIONS;
                        Log.i(TAG, "onNavigationItemSelected: END");
                        break;
                    case R.id.nav_payments:
                        navItemIndex = 3;
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


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        Log.i(TAG, "setUpNavigationView: END");
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 1) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_DEBTORS;
                loadSelectedFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        TODO all clients and debtors menu?

        if(navItemIndex == 2)
            getMenuInflater().inflate(R.menu.menu_transactions, menu);
        if(navItemIndex == 3)
            getMenuInflater().inflate(R.menu.menu_payments,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

//TODO KIEDY OPCJA Z MENU KLIKNIETA -> UZUPELNIC
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        // user is in notifications fragment
//        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }
//
//        // user is in notifications fragment
//        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }

        return super.onOptionsItemSelected(item);
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
        navigationView.getMenu().getItem(3).setActionView(R.layout.dot_test);
    }


    private void simulateTransactionWithPayment(){
        Log.w(TAG, "simulateTransactionWithPayment: ");
        //        WLASCICIEL
        Owner owner = dbOwner.getOwner(1);

//        KLIECI
        Client clientJurand = dbClient.getClientByID(4); //kupujacy 2

        TransactionForClient transactionWithPayment = new TransactionForClient(Utils.getDateTime(), owner, clientJurand, 6, 30, 100, true);

        getInfoAboutTransaction(transactionWithPayment);

        Log.w(TAG, "onCreate: BEFORE TRANSACTION WITH PAYMENT" );
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerTransactions(owner);
//        getListOfClientTransactions(clientJurand);

        RealizeTransactionHelper realizeTransactionHelper = new RealizeTransactionHelper();
        realizeTransactionHelper.realizeTransaction(getApplicationContext(), transactionWithPayment);

        Log.w(TAG, "onCreate: AFTER TRANSACTION WITH PAYMENT ");

        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerTransactions(owner);
//        getListOfClientTransactions(clientJurand);

        dbClient.updateClient(clientJurand);

    }
    private void simulatePayments(){
        Log.w(TAG, "simulatePayments: " );

        Owner owner = dbOwner.getOwner(1);

        Client clientJurand = dbClient.getClientByID(8); //kupujacy 2

        Payment payment = new Payment(Utils.getDateTime(), owner, clientJurand, 20, true);

        Log.i(TAG, "simulatePayments: CREATING PAYMENT  " + payment.toString());
//        dbPayment.createPayment(payment);

        Log.w(TAG, "onCreate: BEFORE PAYMENT" );
        getInfoAboutPayments(payment);
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);

        RealizePaymentHelper realizePaymentHelper = new RealizePaymentHelper();
        realizePaymentHelper.realizePayment(getApplicationContext(), payment);

        Log.w(TAG, "onCreate: AFTER PAYMENT ");
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerPayments(owner);
//        getListOfClientPayments(clientJurand);

        dbClient.updateClient(clientJurand);


    }
    private void simulatePayments(Owner owner, Client clientJurand){
        Log.w(TAG, "simulatePayments: " );

//        PLATNOSC, clientJurand - klient, 50 - kwota, true - dostaję, false - płacę
        Payment payment = new Payment(Utils.getDateTime(), owner, clientJurand, 50, true);
        //tworzony obiekt
        Log.w(TAG, "onCreate: BEFORE PAYMENT" );
        getInfoAboutPayments(payment);
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);


        RealizePaymentHelper realizePaymentHelper = new RealizePaymentHelper();
        realizePaymentHelper.realizePayment(getApplicationContext(), payment);

        Log.w(TAG, "onCreate: AFTER PAYMENT ");
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerPayments(owner);
//        getListOfClientPayments(clientJurand);


        dbClient.updateClient(clientJurand);


    }
    private void simulateTransaction(){
        Log.w(TAG, "simulateTransaction: ");

//        WLASCICIEL
        Owner owner = dbOwner.getOwner(1);
//        KLIECI
        Client clientJurand = dbClient.getClientByID(4); //kupujacy 2

//        TRANZAKCJA
        //o godziinie X owner robi tranzakcje z jurandem za 5 po 10,
        // true - owner - sprzedający,
        // false - owner - kupujący
        TransactionForClient transactionForClient = new TransactionForClient(Utils.getDateTime(),
                owner, clientJurand, 2, 100, true);

//        dbTransaction.createTransaction(transactionForClient);

        getInfoAboutTransaction(transactionForClient);

        Log.w(TAG, "onCreate: BEFORE TRANSACTION" );
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerTransactions(owner);
//        getListOfClientTransactions(clientJurand);

        RealizeTransactionHelper realizeTransactionHelper = new RealizeTransactionHelper();
        realizeTransactionHelper.realizeTransaction(getApplicationContext(),transactionForClient);
        Log.w(TAG, "onCreate: AFTER TRANSACTION ");

        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerTransactions(owner);
//        getListOfClientTransactions(clientJurand);


        dbClient.updateClient(clientJurand);
    }

    private void createClients(String[] names){
        for(int i = 0 ; i< names.length -1 ; i++){
            Client client = new Client(names[i], 50*i);
            dbClient.createClient(client);
        }
    }
    private List<Owner> getOwner(){
        i(TAG, "getOwner:  OWNER");

        List<Owner> listOfOwners = dbOwner.getAllOwners();
        Log.i(TAG, "getOwner: size of list of owners: " + listOfOwners.size());
        for(Owner o : listOfOwners)
            i(TAG, "getAllOwners: owner: " + o.toString());

        return  listOfOwners;
    }

    private List<Payment> getPayments(){
        List<Payment> listOfPayments = dbPayment.getAllPayments();

        for(Payment p : listOfPayments)
            Log.i(TAG, "getPayments: " + p.toString(true));

        return listOfPayments;
    }

    private List<TransactionForClient> getTransaction(){
        Log.i(TAG, "getTransaction: ");
        List<TransactionForClient> listOfTransaction = dbTransaction.getListOfTransaction();

        if (listOfTransaction.size()==0)
            Log.i(TAG, "getTransaction: LIST OF TRANSACTION = 0");

        for(TransactionForClient t: listOfTransaction)
            Log.i(TAG, "getTransaction: transaction: " + t.toString());

        return listOfTransaction;
    }
    private List<TransactionForClient> getTransactionByClientId(long clientID) {
        List<TransactionForClient> transactionByClientID = dbTransaction.getTransactionFromClient(clientID);
        Log.w(TAG, "getTransactionByClientId: " );

        if (transactionByClientID.size() == 0 )
            Log.e(TAG, "getTransactionByClientId: LIST OF TRANSACTION BY CLIENT ID IS EMPTY");

        for(TransactionForClient t : transactionByClientID){
            Log.i(TAG, "getTransactionByClientId: transaction by client id: " + t.toString());
        }

        return transactionByClientID;
    }

    private List<TransactionForClient> getTransactionByOwnerId(long ownerID) {
        List<TransactionForClient> transactionByOwnerID = dbTransaction.getTransactionFromOwned(ownerID);
        Log.w(TAG, "getTransactionByOwnerId: " );

        if (transactionByOwnerID.size() == 0 )
            Log.e(TAG, "getTransactionByOwnerId: LIST OF TRANSACTION BY OWNER ID IS EMPTY");

        for(TransactionForClient t : transactionByOwnerID){
            Log.i(TAG, "getTransactionByOwnerId: transaction by owner id: " + t.toString());
        }

        return transactionByOwnerID;
    }
    private void getListOfOwnerTransactions(Owner owner){
        i(TAG, "getListOfOwnerTransactions: lista tranzakcji " + owner.getListOfTransaction());
    }

    private void getListOfClientTransactions(Client client){
        i(TAG, "getListOfClientTransactions: lista tranzakcji " + client.getListOfTransaction());
    }

    private void getListOfOwnerPayments(Owner owner){
        i(TAG, "getListOfOwnerPayments: lista zapłat: " + owner.getListOfPayments());
    }
    private void getListOfClientPayments(Client client){
        i(TAG, "getListOfClientPayments: lista zapłat: " + client.getListOfPayments());
    }
    private void getInfoAboutTransaction(TransactionForClient transaction){
        i(TAG, "getInfoAboutTransaction: " + transaction.toString());
    }
    private void getInfoAboutPayments(Payment payment){
        i(TAG, "getInfoAboutPayments: " + payment.toString());
    }
    private void getInfoAboutOwner(Owner owner){
        i(TAG, "getInfoAboutOwner: " + owner.toString());
    }
    private void getInfoAboutClient(Client client){
        i(TAG, "getInfoAboutClient: " + client.toString());
    }
    private void printList(List<Client> list){
        if(list.size() == 0 )
            i(TAG, "printList: LISTA PUSTA");
        i(TAG, "printList: hao");
        for(Client c : list)
            i(TAG, "printList: drukuje klienta: " + c.toString());
    }
    private void deleteAllPayments(){
        List<Payment> listOfPayments = dbPayment.getAllPayments();
        int pajmeny = listOfPayments.size();

        for(int i = 0 ; i<pajmeny;i++){
            dbPayment.deletePayment(listOfPayments.get(i).getPaymentID());
        }
    }

    private void deleteAllClients() {
        
        List<Client> listOfClients = dbClient.getAllClient();
        int liczbaklientuw = listOfClients.size();

        Log.i(TAG, "deleteAllClients: size of list of clients : " + liczbaklientuw);

//        while(liczbaklientuw>0){
//            Log.i(TAG, "deleteAllClients: deleting client id: " + liczbaklientuw);
//            db.deleteClient(liczbaklientuw);
//            liczbaklientuw--;
//        }

        for(int i = 0 ; i<liczbaklientuw;i++){
            Log.i(TAG, "deleteAllClients: deleted client: " + i);
            dbClient.deleteClient(listOfClients.get(i).getClientId());
        }

    }

    private List<Payment> getPaymentByClientId(long clientID){
        Log.i(TAG, "getPaymentByClientId: payment dla danego id clienta");
        List<Payment> listOfPayments = dbPayment.getPaymentsFromClient(clientID);

        if(listOfPayments.size()==0)
            Log.e(TAG, "getPaymentByClientId: THERE IS NOT ANY PAYMENTS FOR THAT CLIENT" );

        for(Payment p : listOfPayments){
            Log.i(TAG, "getPaymentByClientId: payment: " + p.toString(true));
        }

        return listOfPayments;
    }

    private List<Payment> getPaymentByOwnerId(long ownerID){
        Log.i(TAG, "getPaymentByOwnerId: payment dla danego id ownera");
        List<Payment> listOfPayments = dbPayment.getPaymentsFromOwner(ownerID);

        if(listOfPayments.size()==0)
            Log.e(TAG, "getPaymentByOwnerId: THERE IS NOT ANY PAYMENTS FOR THAT OWNER" );

        for(Payment p : listOfPayments){
            Log.i(TAG, "getPaymentByOwnerId: payment: " + p.toString(true));
        }

        return listOfPayments;
    }
    private List<Client> getAllClients() {
        i(TAG, "getAllClients: WSZYSCY KLIENCI");
        List<Client> listOfClients = dbClient.getAllClient();
        i(TAG, "getAllClients: liczba klientów: " + listOfClients.size());
        for(Client c : listOfClients)
            i(TAG, "getAllClients: client: " + c.toString());

        return  listOfClients;
    }
    
    private List<Client> getClientInLeftAmountRange(){
        i(TAG, "getClientInLeftAmountRange: Z ZAKRESU OD DO HAJSU");
        
        List<Client> listOfClientWithLeftAmountFromTo = new ArrayList<>();

        listOfClientWithLeftAmountFromTo = dbClient.getClientWithLeftAmountSorted(50,150);


        for(Client client : listOfClientWithLeftAmountFromTo){
            i(TAG, "onCreate: uzytkownik: " + client.toString());
        }
        
        return listOfClientWithLeftAmountFromTo;
    }

    private List<Client> getClientsByName(String name){

        List<Client> listOfUserByName = new ArrayList<>();

        listOfUserByName =  dbClient.getClientByName(name);
        return listOfUserByName;
    }



}

