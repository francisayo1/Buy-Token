package com.feedco.note;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.feedco.note.Amount_BottomSheetFrag.AmountListener;

import com.feedco.note.Dagger.AppComponent;
import com.feedco.note.Dagger.DaggerAppComponent;
import com.feedco.note.Database.TransactionDB;
import com.feedco.note.Models.MeterSearchModel.Content;
import com.feedco.note.Models.MeterTransactionModel.MeterTransaction;
import com.feedco.note.Models.Tables.MeterTable;
import com.feedco.note.Repository.TransactionRepository;
import com.feedco.note.Resource.TransactionResource;
import com.feedco.note.ViewModel.TransactionViewModel;
import com.feedco.note.databinding.MeterDetailsBinding;
import com.google.android.material.button.MaterialButton;
import com.teamapt.monnify.sdk.MonnifyTransactionResponse;

import java.math.BigInteger;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


public class MeterDetailActivity extends AppCompatActivity implements AmountListener {


    private MeterDetailsBinding viewBinding;
    public static String companyName;

    Disposable disposable;
    boolean isDialogOpened = false;
    private static final String KEY_RESULT = "Payment_Result";
    Content content;
    String amount, reference;

    private String meterType, meterNumber, name, address;

    private TransactionViewModel transactionViewModel;
    @Inject
    TransactionRepository transactionRepository;

    @Inject
    TransactionDB transactionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        viewBinding = DataBindingUtil.setContentView ( this, R.layout.meter_details );

        Intent in = getIntent ( );
        content = in.getParcelableExtra ( "data" );

        companyName = in.getStringExtra ( "companyName" );
        displayDetails ( content );

        viewBinding.btNext.setOnClickListener ( view -> {
            //if(!isDialogOpened) {
            Amount_BottomSheetFrag bottomSheetFrag = new Amount_BottomSheetFrag ( );

            Bundle bundle = new Bundle ( );
            bundle.putString ( "meter", content.getMeterNumber ( ) );
            bundle.putString ( "meterType", content.getMeterType ( ) );
            bundle.putString ( "name", content.getCustomerName ( ) );
            bottomSheetFrag.setArguments ( bundle );
            bottomSheetFrag.show ( getSupportFragmentManager ( ), "AmountFrag" );
            // }
            //  isDialogOpened=true;
        } );
        transactionViewModel = new ViewModelProvider ( this ).get ( TransactionViewModel.class );

        AppComponent appComponent = DaggerAppComponent.builder ( ).getApplicationContext ( getApplication ( ) ).build ( );
        appComponent.inject ( this );
    }

    public void displayDetails(Content content) {

        meterNumber = content.getMeterNumber ( );
        meterType = content.getMeterType ( );
        name = content.getCustomerName ( );
        address = content.getAddress ( );
        viewBinding.tvAddress.setText ( content.getAddress ( ) );
        viewBinding.tvName.setText ( content.getCustomerName ( ) );
        viewBinding.tvPhone.setText ( content.getCustomerPhone ( ) );
        viewBinding.tvEmail.setText ( "-" );
        viewBinding.tvMeterNumber.setText ( content.getMeterNumber ( ) );
        viewBinding.tvMeterType.setText ( content.getMeterType ( ) );
        if (TextUtils.isEmpty ( content.getCustomerArrears ( ) )) {
            viewBinding.tvArrears.setText ( "0" );
        } else viewBinding.tvArrears.setText ( content.getCustomerArrears ( ) );

        if (TextUtils.isEmpty ( content.getLastPurchaseDays ( ) )) {
            viewBinding.tvLastPurchaseDate.setText ( "No Date" );
        } else viewBinding.tvLastPurchaseDate.setText ( content.getLastPurchaseDays ( ) );


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );

        MonnifyTransactionResponse monnifyTransactionResponse = data.getParcelableExtra ( KEY_RESULT );

        if (monnifyTransactionResponse == null)
            return;

        String message = "";
        System.out.println ( "status" + monnifyTransactionResponse.getStatus ( ) );
        switch (monnifyTransactionResponse.getStatus ( )) {

            case PENDING: {
                message = "Transaction not paid for.";
                viewBinding.progress.setVisibility ( View.VISIBLE );
                break;
            }
            case PAID: {
                message = "Customer paid exact amount";
                viewBinding.progress.setVisibility ( View.INVISIBLE );

                // makeTransaction ( );
               // proceedToMainActivity ( );
                 insertRecordsToDB ( "1000","23:10","12221121212","Delhi","prepaid" );
                break;
            }
            case OVERPAID: {
                message = "You have paid more than expected amount.";
                viewBinding.progress.setVisibility ( View.INVISIBLE );

                makeTransaction ( );
                break;
            }
            case PARTIALLY_PAID: {
                message = "You have paid less than expected amount.";
                viewBinding.progress.setVisibility ( View.INVISIBLE );
                showDialog ( message, false );
                break;
            }
            case FAILED: {
                message = "Transaction completed unsuccessfully. This means no payment came in for Account Transfer method or attempt to charge card failed.";
                viewBinding.progress.setVisibility ( View.INVISIBLE );
                showDialog ( message, false );
                break;
            }
            case PAYMENT_GATEWAY_ERROR: {
                message = "Payment gateway error";
                viewBinding.progress.setVisibility ( View.INVISIBLE );
                showDialog ( message, false );
                break;
            }
        }

        Toast.makeText ( this, message, Toast.LENGTH_LONG ).show ( );
    }

    public void makeTransaction() {

        String companyName = MeterDetailActivity.companyName;
        if (!TextUtils.isEmpty ( meterNumber ) && !TextUtils.isEmpty ( companyName ) && !TextUtils.isEmpty ( meterType ) && !TextUtils.isEmpty ( amount ) && !TextUtils.isEmpty ( reference )) {
            System.out.println ( "Inside make trans" );
       //     LiveData <TransactionResource <MeterTransaction>> liveData = transactionViewModel.getTransactionLiveData ( meterNumber, MeterDetailActivity.companyName, meterType.toLowerCase ( ), Integer.parseInt ( amount ), new BigInteger ( "08099444192" ), reference, transactionRepository );
           // liveData.removeObservers ( this );

            //liveData.observe ( this, this::extractDetails );
        }


    }

    public void extractDetails(TransactionResource <MeterTransaction> data) {
        switch (data.status) {

            case LOADING: {
                viewBinding.progress.setVisibility ( View.VISIBLE );

                break;
            }

            case ERROR: {
                showDialog ( "Something went wrong please try again. Please use this reference id " + reference, false );
                viewBinding.progress.setVisibility ( View.INVISIBLE );
                break;
            }
            case AUTHENTICATED: {
                viewBinding.progress.setVisibility ( View.INVISIBLE );
                assert data.data != null;
                String description = data.data.getResponseDescription ( );
                if (description.equalsIgnoreCase ( "Transaction Successful" )) {
                    if (meterType.equalsIgnoreCase ( "prepaid" )) {
                        String amount = "₦" + data.data.getAmount ( );
                        String transId = data.data.getContent ( ).getTransactions ( ).getTransactionId ( );
                        String date = data.data.getTransactionDate ( ).getDate ( );
                        String token = data.data.getToken ( );
                        String units = data.data.getUnits ( );

                        showDialogPaymentSuccess ( amount, transId, date, token, units );
                        insertRecordsToDB ( amount, date, token, address, meterType );
                    } else {
                        String amount = "₦" + data.data.getAmount ( );
                        String transId = data.data.getContent ( ).getTransactions ( ).getTransactionId ( );
                        String date = data.data.getTransactionDate ( ).getDate ( );
                        // String token=data.data.getToken ();
                        //String units=data.data.getUnits ();

                        insertRecordsToDB ( amount, date, "0", address, meterType );
                        showDialogPaymentSuccess ( amount, transId, date, "0", "0" );

                    }


                }
                break;
            }

        }

    }

    private void showDialog(String errorMessage, boolean goFurther) {


        AlertDialog.Builder builder = new AlertDialog.Builder ( this );
        ViewGroup viewGroup = findViewById ( android.R.id.content );
        View dialogView = LayoutInflater.from ( viewGroup.getContext ( ) ).inflate ( R.layout.error_dialog, viewGroup, false );
        builder.setView ( dialogView );
        AlertDialog alertDialog = builder.create ( );


        MaterialButton bt_ok = dialogView.findViewById ( R.id.bt_ok );
        TextView tv_message = dialogView.findViewById ( R.id.tv_message );
        tv_message.setText ( errorMessage );
        bt_ok.setOnClickListener ( view -> {

            if (goFurther) {
                //   makeTransaction ( );
                alertDialog.dismiss ( );
            } else {
                proceedToMainActivity ( );
                alertDialog.dismiss ( );
            }
        } );
        alertDialog.show ( );
    }

    private void showDialogPaymentSuccess(String amount, String tranId, String date, String token, String units) {
        FragmentManager fragmentManager = getSupportFragmentManager ( );
        PaymentSuccessDialogFragment newFragment = new PaymentSuccessDialogFragment ( );
        FragmentTransaction transaction = fragmentManager.beginTransaction ( );
        transaction.setTransition ( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
        transaction.add ( android.R.id.content, newFragment ).addToBackStack ( null ).commit ( );

        String[] dateTime = date.split ( "\\s+" );

        Bundle bundle = new Bundle ( );
        bundle.putString ( "amount", amount );
        bundle.putString ( "tranId", tranId );
        bundle.putString ( "date", dateTime[0] );
        bundle.putString ( "time", dateTime[1] );
        bundle.putString ( "token", token );
        bundle.putString ( "units", units );

        newFragment.setArguments ( bundle );

    }


    @Override
    public void getDataFromFragment(String amount, String reference) {
        System.out.println ( "Amount " + amount );
        System.out.println ( "Reference " + reference );
        this.reference = reference;
        this.amount = amount;

    }

    public void proceedToMainActivity() {
        startActivity ( new Intent ( this, MainActivity.class ).addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK ));
    }


    public void insertRecordsToDB(String amount, String time, String token, String address, String meterType) {


        String[] arr = time.split ( "\\s" );
        MeterTable meterTable = new MeterTable ( );
        meterTable.setAmount ( amount );
        meterTable.setTime ( arr[0] );
        meterTable.setToken ( token );
        meterTable.setAddress ( address );
        meterTable.setMeterType ( meterType );

        System.out.println ( "Inside DB Save" );
        new Thread ( () -> transactionDB.transactionDao ( ).insertTransactionDetails ( meterTable ) ).start ( );
        /*Observable <MeterTable> observable = Observable.create ( emitter -> {
            transactionDB.transactionDao ( ).insertTransactionDetails ( meterTable );
            emitter.onComplete ( );
        } );


           observable.subscribeOn ( Schedulers.io ( ) );
*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy ( );
       // disposable.dispose ( );
    }
}
