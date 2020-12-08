package com.feedco.note;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.feedco.note.Models.MeterSearchModel.MeterSearchModel;
import com.feedco.note.Repository.MeterSearchRepository;
import com.feedco.note.Resource.MeterSearchResource;
import com.feedco.note.ViewModel.MeterSearchViewModel;
import com.google.android.material.button.MaterialButton;
import com.teamapt.monnify.sdk.Monnify;
import com.teamapt.monnify.sdk.MonnifyTransactionResponse;
import com.teamapt.monnify.sdk.data.model.TransactionDetails;

import java.math.BigDecimal;

import javax.inject.Inject;

public class PaymentGatewayActivity extends AppCompatActivity {
    private static final int INITIATE_PAYMENT_REQUEST_CODE = 100;
    private static final String KEY_RESULT = "Payment_Result";
    private Monnify monnify = Monnify.Companion.getInstance ( );

    MeterSearchViewModel meterSearchViewModel;

    @Inject
    MeterSearchRepository meterSearchRepository;
    String amount;


   // AppComponent appComponent;

    public static final String REFERENCE = String.valueOf ( System.currentTimeMillis ( ) );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_payment_gateway );

        meterSearchViewModel = new ViewModelProvider ( this ).get ( MeterSearchViewModel.class );

      /*  appComponent = DaggerAppComponent.builder ().build ();
        appComponent.inject ( this );
*/

    }

    public void completeTransaction(double amount) {

        TransactionDetails transaction = new TransactionDetails.Builder ( )
                .amount ( new BigDecimal ( amount ) )
                .currencyCode ( "NGN" )
                .customerName ( "Francis" )
                .customerEmail ( "mail.cus@tome.er" )
                .paymentReference ( REFERENCE )
                .paymentDescription ( MeterDetailActivity.companyName + " Payment" )
                .build ( );

        monnify.initializePayment (
                this,
                transaction,
                INITIATE_PAYMENT_REQUEST_CODE,
                KEY_RESULT );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        System.out.println ("Inside on result please" );
        MonnifyTransactionResponse monnifyTransactionResponse = data.getParcelableExtra ( KEY_RESULT );

        if (monnifyTransactionResponse == null)
            return;

        String message = "";
        System.out.println ( "status"+monnifyTransactionResponse.getStatus ( ));
        switch (monnifyTransactionResponse.getStatus ( )) {

            case PENDING: {
                message = "Transaction not paid for.";
               // layoutBinding.progress.setVisibility ( View.VISIBLE );
                break;
            }
            case PAID: {
                message = "Customer paid exact amount";
               // layoutBinding.progress.setVisibility ( View.INVISIBLE );
                showFailureDialog ( null, true );

                break;
            }
            case OVERPAID: {
                message = "You have paid more than expected amount.";
               // layoutBinding.progress.setVisibility ( View.INVISIBLE );
                showFailureDialog ( message, true );
                break;
            }
            case PARTIALLY_PAID: {
                message = "You have paid less than expected amount.";
            //    layoutBinding.progress.setVisibility ( View.INVISIBLE );
                showFailureDialog ( message, false );
                break;
            }
            case FAILED: {
                message = "Transaction completed unsuccessfully. This means no payment came in for Account Transfer method or attempt to charge card failed.";
              //  layoutBinding.progress.setVisibility ( View.INVISIBLE );
                showFailureDialog ( message, false );
                break;
            }
            case PAYMENT_GATEWAY_ERROR: {
                message = "Payment gateway error";
               // layoutBinding.progress.setVisibility ( View.INVISIBLE );
                showFailureDialog ( message, false );
                break;
            }
        }

        Toast.makeText ( this, message, Toast.LENGTH_LONG ).show ( );
    }

    private void showFailureDialog(String errorMessage, boolean goFurther) {


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
                alertDialog.dismiss ( );
            }
        } );
        alertDialog.show ( );
    }

    public void fetchDetails(MeterSearchResource <MeterSearchModel> meterData) {

        switch (meterData.status) {
            case LOADING: {
                //viewBinding.progressBar.setVisibility ( View.VISIBLE );
                // Log.d ( TAG, "fetchDetails: Loading" );
                System.out.println ( "Loading" );
                break;
            }
            case ERROR: {
                assert meterData.data != null;
                String error = meterData.data.getContent ( ).getError ( );
                //  viewBinding.progressBar.setVisibility ( View.GONE );

                Toast.makeText ( this, error, Toast.LENGTH_SHORT ).show ( );

                //  Log.d ( TAG, "fetchDetails: Error " + error );
                break;
            }
            case AUTHENTICATED: {
                //viewBinding.progressBar.setVisibility ( View.GONE );
                assert meterData.data != null;
                /// Log.d ( TAG, "fetchDetails: meterData " + meterData.data.getContent ());

                System.out.println ( "Data " + meterData.data.getContent ( ).getMeterNumber ( ) );
                System.out.println ( "Calling " );
                ///launchUserDetails ( meterData.data.getContent () );


                break;
            }
        }


    }


    /*public void makeRequest(View view) {

        LiveData <MeterSearchResource <MeterSearchModel>> liveData = meterSearchViewModel.getMeterDetails ( "27100403859", "portharcourt-electric", "prepaid", meterSearchRepository );
        liveData.removeObservers ( this );

        liveData.observe ( this, data -> {
            if (data != null) {
                fetchDetails ( data );
            }
        } );
      *//* meterSearchViewModel.getMeterDetails ( "27100403859", "portharcourt-electric", "prepaid", meterSearchRepository )
                .observe ( this, data -> {

                    //System.out.println ("Called times" );
                    if (data != null) {
                        fetchDetails ( data );
                    }

                } );
    }*//*
    }*/
}
