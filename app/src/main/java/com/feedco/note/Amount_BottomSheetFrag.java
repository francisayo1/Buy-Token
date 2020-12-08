package com.feedco.note;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.feedco.note.databinding.PaymentLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.teamapt.monnify.sdk.Monnify;
import com.teamapt.monnify.sdk.data.model.TransactionDetails;
import com.teamapt.monnify.sdk.service.ApplicationMode;

import java.math.BigDecimal;
import java.util.Objects;


public class Amount_BottomSheetFrag extends BottomSheetDialogFragment {

    private PaymentLayoutBinding layoutBinding;
    private BottomSheetDialog bottomSheetDialog;

    private static final int INITIATE_PAYMENT_REQUEST_CODE = 100;
    private static final String KEY_RESULT = "Payment_Result";
    private Monnify monnify = Monnify.Companion.getInstance ( );
    private String amount;
    private  final String REFERENCE = String.valueOf ( System.currentTimeMillis ( ) );

    private Context context;

    //private TransactionViewModel transactionViewModel;

    private String meterNumber, meterType, name;

    private static final int COMMISSION = 100;

    private AmountListener amountListener;
   /* @Inject
    TransactionRepository transactionRepository;*/


    public Amount_BottomSheetFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        layoutBinding = DataBindingUtil.inflate ( inflater, R.layout.payment_layout, container, false );


        layoutBinding.progress.setBackgroundColor ( getResources ( ).getColor ( R.color.colorPrimary ) );
        return layoutBinding.getRoot ( );

    }

    private boolean validate() {
        String amount = Objects.requireNonNull ( layoutBinding.etAmount.getText ( ) ).toString ( );

        return !TextUtils.isEmpty ( amount );


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );
        Bundle bundle = getArguments ( );
        //if (savedInstanceState != null) {
        if (bundle != null) {
            meterNumber = (String) bundle.get ( "meter" );
            meterType = (String) bundle.get ( "meterType" );
            name = (String) bundle.get ( "name" );

        }


        // }

        layoutBinding.btContinue.setOnClickListener ( view1 -> {
            if (validate ( )) {
                amount = Objects.requireNonNull ( layoutBinding.etAmount.getText ( ) ).toString ( );

                double totalAmount = Double.parseDouble ( amount );
                bottomSheetDialog.getBehavior ( ).setState ( BottomSheetBehavior.STATE_HIDDEN );

                completeTransaction ( (totalAmount + COMMISSION) );


                //startActivity ( new Intent ( getActivity ( ), PaymentGatewayActivity.class ).putExtra ( "amount", amount ) );
            }
        } );

        layoutBinding.tvClose.setOnClickListener ( view2 -> bottomSheetDialog.getBehavior ( ).setState ( BottomSheetBehavior.STATE_HIDDEN )
        );

        bottomSheetDialog.setDismissWithAnimation ( true );


        //live
       /* monnify.setApiKey ( "MK_PROD_DMBZQKEUNK" );
        monnify.setContractCode ( "633946583458" );
*/
        monnify.setApiKey ( "MK_TEST_CT3V9UXVV8" );
        monnify.setContractCode ( "1161854536" );

        monnify.setApplicationMode ( ApplicationMode.TEST );

        //transactionViewModel = new ViewModelProvider ( this ).get ( TransactionViewModel.class );
        /*AppComponent appComponent = DaggerAppComponent.create ( );
        appComponent.inject ( this );*/
    }

    private void completeTransaction(double totalAmount) {

        TransactionDetails transaction = new TransactionDetails.Builder ( )
                .amount ( new BigDecimal ( totalAmount ) )
                .currencyCode ( "NGN" )
                .customerName ( name )
                .customerEmail ( "mail.cus@tome.er" )
                .paymentReference ( REFERENCE )
                .paymentDescription ( MeterDetailActivity.companyName + " Payment" )
                .build ( );

        monnify.initializePayment (
                (Activity) context,
                transaction,
                INITIATE_PAYMENT_REQUEST_CODE,
                KEY_RESULT );
        amountListener.getDataFromFragment ( amount, REFERENCE );


    }

    /* @Override
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
                 layoutBinding.progress.setVisibility ( View.VISIBLE );
                 break;
             }
             case PAID: {
                 message = "Customer paid exact amount";
                 layoutBinding.progress.setVisibility ( View.INVISIBLE );
                 showFailureDialog ( null, true );

                 break;
             }
             case OVERPAID: {
                 message = "You have paid more than expected amount.";
                 layoutBinding.progress.setVisibility ( View.INVISIBLE );
                 showFailureDialog ( message, true );
                 break;
             }
             case PARTIALLY_PAID: {
                 message = "You have paid less than expected amount.";
                 layoutBinding.progress.setVisibility ( View.INVISIBLE );
                 showFailureDialog ( message, false );
                 break;
             }
             case FAILED: {
                 message = "Transaction completed unsuccessfully. This means no payment came in for Account Transfer method or attempt to charge card failed.";
                 layoutBinding.progress.setVisibility ( View.INVISIBLE );
                 showFailureDialog ( message, false );
                 break;
             }
             case PAYMENT_GATEWAY_ERROR: {
                 message = "Payment gateway error";
                 layoutBinding.progress.setVisibility ( View.INVISIBLE );
                 showFailureDialog ( message, false );
                 break;
             }
         }

         Toast.makeText ( context, message, Toast.LENGTH_LONG ).show ( );
     }
 */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach ( context );
        this.context = context;
        amountListener = (AmountListener) context;

    }

    @Override
    public void onStart() {
        super.onStart ( );

        bottomSheetDialog.getBehavior ( ).setState ( BottomSheetBehavior.STATE_EXPANDED );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog ( savedInstanceState );
        return bottomSheetDialog;
    }


   /* public void makeTransaction() {

        String companyName = MeterDetailActivity.companyName;
        if (!TextUtils.isEmpty ( meterNumber ) && !TextUtils.isEmpty ( companyName ) && !TextUtils.isEmpty ( meterType ) && !TextUtils.isEmpty ( amount ) && !TextUtils.isEmpty ( REFERENCE )) {
            LiveData <TransactionResource <MeterTransaction>> liveData = transactionViewModel.getTransactionLiveData ( meterNumber, MeterDetailActivity.companyName, meterType, Integer.parseInt ( amount ), 9000, REFERENCE, transactionRepository );
            liveData.removeObservers ( this );

            liveData.observe ( this, this::extractDetails );
        }


    }

    public void extractDetails(TransactionResource <MeterTransaction> data) {
        switch (data.status) {

            case LOADING: {
                layoutBinding.progress.setVisibility ( View.VISIBLE );

            }

            case ERROR: {
                layoutBinding.progress.setVisibility ( View.INVISIBLE );
            }
            case AUTHENTICATED: {
                layoutBinding.progress.setVisibility ( View.INVISIBLE );
                String description = data.data.getResponse_description ( );
                if (description.equalsIgnoreCase ( "Transaction Successful" )) {
                    if (meterType.equalsIgnoreCase ( "prepaid" )) {
                        String amount = "₦" + data.data.getAmount ( );
                        String transId = data.data.getContent ( ).getTransactions ( ).getTransactionId ( );
                        String date = data.data.getTransactionDate ( ).getDate ( );
                        String token = data.data.getToken ( );
                        String units = data.data.getUnits ( );

                        showDialogPaymentSuccess ( amount, transId, date, token, units );
                    } else {
                        String amount = "₦" + data.data.getAmount ( );
                        String transId = data.data.getContent ( ).getTransactions ( ).getTransactionId ( );
                        String date = data.data.getTransactionDate ( ).getDate ( );
                        // String token=data.data.getToken ();
                        //String units=data.data.getUnits ();

                        showDialogPaymentSuccess ( amount, transId, date, "0", "0" );
                    }


                }

            }

        }

    }

    private void showFailureDialog(String errorMessage, boolean goFurther) {


        AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity ( ) );
        ViewGroup viewGroup = getActivity ( ).findViewById ( android.R.id.content );
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

    private void showDialogPaymentSuccess(String amount, String tranId, String date, String token, String units) {
        FragmentManager fragmentManager = Objects.requireNonNull ( getActivity ( ) ).getSupportFragmentManager ( );
        PaymentSuccessDialogFragment newFragment = new PaymentSuccessDialogFragment ( );
        FragmentTransaction transaction = fragmentManager.beginTransaction ( );
        transaction.setTransition ( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
        transaction.add ( android.R.id.content, newFragment ).addToBackStack ( null ).commit ( );
    }
*/

    public interface AmountListener {
        void getDataFromFragment(String amount, String reference);
    }
}
