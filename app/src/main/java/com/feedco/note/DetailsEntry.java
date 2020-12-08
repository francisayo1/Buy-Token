package com.feedco.note;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.feedco.note.CustomClasses.Constants;
import com.feedco.note.Dagger.AppComponent;
import com.feedco.note.Dagger.DaggerAppComponent;
import com.feedco.note.Database.TransactionDB;
import com.feedco.note.Models.MeterTransactionModel.MeterTransaction;
import com.feedco.note.Models.SmsModel.Auth;
import com.feedco.note.Models.SmsModel.GsmItem;
import com.feedco.note.Models.SmsModel.Message;
import com.feedco.note.Models.SmsModel.Recipients;
import com.feedco.note.Models.SmsModel.SMS;
import com.feedco.note.Models.SmsModel.SMSBody;
import com.feedco.note.Models.SmsModel.SmsResponse;
import com.feedco.note.Models.Tables.MeterTable;
import com.feedco.note.Repository.TransactionRepository;
import com.feedco.note.Resource.TransactionResource;
import com.feedco.note.ViewModel.TransactionViewModel;
import com.feedco.note.databinding.ActivityDetailsEntryBinding;
import com.feedco.note.databinding.ActivityPlanBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.teamapt.monnify.sdk.Monnify;
import com.teamapt.monnify.sdk.MonnifyTransactionResponse;
import com.teamapt.monnify.sdk.data.model.TransactionDetails;
import com.teamapt.monnify.sdk.service.ApplicationMode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class DetailsEntry extends AppCompatActivity {

    private String amount, variation_code, companyName;

    ActivityDetailsEntryBinding dataBinding;

    private String rechargeType;
    private String meterType, email, price, meter, phone;

    private static final int INITIATE_PAYMENT_REQUEST_CODE = 100;
    private static final String KEY_RESULT = "Payment_Result";
    private Monnify monnify = Monnify.Companion.getInstance ( );

    private final String REFERENCE = String.valueOf ( System.currentTimeMillis ( ) );

    private final double COMMISSION = 100;
    private TransactionViewModel transactionViewModel;
    @Inject
    TransactionRepository transactionRepository;

    private boolean dialogShown;
    @Inject
    TransactionDB transactionDB;
    PaymentSuccessDialogFragment newFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        dataBinding = DataBindingUtil.setContentView ( this, R.layout.activity_details_entry );

        ActionBar toolbar = getSupportActionBar ( );
        assert toolbar != null;
        toolbar.setDisplayShowHomeEnabled ( true );
        toolbar.setDisplayHomeAsUpEnabled ( true );
        toolbar.setDisplayHomeAsUpEnabled ( true );
        getSupportActionBar ( ).setTitle ( "Enter Details" );

        //receiving items via intent from different activity. In case of electricity and airtime its null.
        amount = getIntent ( ).getStringExtra ( "AMOUNT" );
        companyName = getIntent ( ).getStringExtra ( "SHORT_NAME" );
        variation_code = getIntent ( ).getStringExtra ( "VARIATION_CODE" );

        //setting default meter type to prepaid.
        meterType = "prepaid";

        transactionViewModel = new ViewModelProvider ( this ).get ( TransactionViewModel.class );

        AppComponent appComponent = DaggerAppComponent.builder ( ).getApplicationContext ( getApplication ( ) ).build ( );
        appComponent.inject ( this );


        rechargeType = CompanyListBottomSheet.rechargeType;

        if(rechargeType.equalsIgnoreCase ( "phone" ) ||rechargeType.equalsIgnoreCase ( "internet" )){

            dataBinding.etMeter.setVisibility ( View.GONE );
        }

        if (!rechargeType.equalsIgnoreCase ( "electricity" )) {

            dataBinding.rgType.setVisibility ( View.GONE );

//            dataBinding.etMeter.setVisibility ( View.GONE );
        }
        if (rechargeType.equalsIgnoreCase ( "tv" )) {

            dataBinding.etMeter.setHint ( "Smart Card Number" );
        }

        if (rechargeType.equalsIgnoreCase ( "tv" ) || rechargeType.equalsIgnoreCase ( "internet" )) {

            dataBinding.etAmount.setText ( amount );
            dataBinding.etAmount.setEnabled ( false );
        }

        dataBinding.rgType.setOnCheckedChangeListener ( (group, checkedId) -> {
            if (R.id.rb_prepaid == checkedId) {
                meterType = "prepaid";
                dataBinding.etMeter.setHint ( "Meter Number" );

            } else if (R.id.rb_postpaid == checkedId) {
                dataBinding.etMeter.setHint ( "Account Number" );
                meterType = "postpaid";
            }

        } );

        dataBinding.tvClose.setOnClickListener ( view->{
            onBackPressed ();
        } );

        monnify.setApiKey ( "MK_TEST_CT3V9UXVV8" );
        monnify.setContractCode ( "1161854536" );

        monnify.setApplicationMode ( ApplicationMode.TEST );


        dataBinding.btPay.setOnClickListener ( view -> {
            email = dataBinding.etEmail.getText ( ).toString ( );
            phone = dataBinding.etPhone.getText ( ).toString ( );
            price = dataBinding.etAmount.getText ( ).toString ( );
            meter = dataBinding.etMeter.getText ( ).toString ( );

            if (validateEntries ( email, phone,meter, price, meterType )) {

                double amount = Double.parseDouble ( price );
                completeTransaction ( amount + COMMISSION );
               // checkForRechargeTypeAndInitiateTransaction ( rechargeType );
            }
        } );

        dataBinding.tvRefresh.setOnClickListener ( view->{

        } );
    }
    private boolean validateEntries(String email, String phone, String meter, String amount, String type) {
        if (TextUtils.isEmpty ( phone ) || phone.length ( ) < 11) {
            System.out.println ("Length "+phone.length () );
            dataBinding.etPhone.setError ( "Enter Valid No." );
            return false;
        } else if ((rechargeType.equalsIgnoreCase ( "electricity" ) || rechargeType.equalsIgnoreCase ( "tv" )) &&TextUtils.isEmpty ( meter ) ) {
            //if (TextUtils.isEmpty ( meter )) {
                dataBinding.etMeter.setError ( "Enter valid No." );
               // showSnackBar ( "Select Meter Type" );
                return false;
            //}
        } else if (TextUtils.isEmpty ( amount ) || Double.parseDouble ( amount ) < 100) {
            dataBinding.etAmount.setError ( "Enter valid amount" );

            return false;
        } else if (!isValid ( email )) {
            System.out.println (isValid ( email ) );
            dataBinding.etEmail.setError ( "Enter valid email" );

            return false;
        } else if (dataBinding.rgType.getVisibility ( ) == View.VISIBLE) {

            showSnackBar ( "Select Meter Type " );
            return !TextUtils.isEmpty ( type );
        }

        return true;

    }
    private void completeTransaction(double totalAmount) {

        TransactionDetails transaction = new TransactionDetails.Builder ( )
                .amount ( new BigDecimal ( totalAmount ) )
                .currencyCode ( "NGN" )
                .customerName ( email )
                .customerEmail ( email )
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

        MonnifyTransactionResponse monnifyTransactionResponse = data.getParcelableExtra ( KEY_RESULT );

        if (monnifyTransactionResponse == null)
            return;

        String message = "";
        System.out.println ( "status" + monnifyTransactionResponse.getStatus ( ) );
        switch (monnifyTransactionResponse.getStatus ( )) {

            case PENDING: {
                message = "Transaction not paid for.";
                dataBinding.progressBar.setVisibility ( View.VISIBLE );
                break;
            }
            case PAID: {
                message = "Customer paid exact amount";
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );
               checkForRechargeTypeAndInitiateTransaction ( rechargeType );
                // makeTransaction ( );
                // proceedToMainActivity ( );
                // insertRecordsToDB ( "1000", "23:10", "12221121212", "Delhi", "prepaid" );
                break;
            }
            case OVERPAID: {
                message = "You have paid more than expected amount.";
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );

                // checkForRechargeTypeAndInitiateTransaction ( rechargeType );
                break;
            }
            case PARTIALLY_PAID: {
                message = "You have paid less than expected amount.";
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );
                showDialog ( message, false );
                break;
            }
            case FAILED: {
                message = "Transaction completed unsuccessfully. This means no payment came in for Account Transfer method or attempt to charge card failed.";
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );
                showDialog ( message, false );
                break;
            }
            case PAYMENT_GATEWAY_ERROR: {
                message = "Payment gateway error";
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );
                showDialog ( message, false );
                break;
            }
        }

        Toast.makeText ( this, message, Toast.LENGTH_LONG ).show ( );
    }

    private void checkForRechargeTypeAndInitiateTransaction(String rechargeType) {
        System.out.println ("Recharge Type "+rechargeType );
        switch (rechargeType) {

            case "electricity":

                makeMeterTransaction ( REFERENCE, companyName, meter, meterType, price, phone );


                break;
            case "internet":

                makeDataTransaction ( REFERENCE, companyName, phone, variation_code, price, phone );
                break;
            case "phone":
                makeAirtimeTransaction ( REFERENCE, companyName, price, phone );
                break;
            case "tv":
                makeTvTransaction ( REFERENCE, companyName, meter, variation_code, price, phone );
                break;

        }
    }

    private void makeMeterTransaction(String requestId, String service, String billerCode, String variationCode, String amount, String phone) {

        if (!TextUtils.isEmpty ( billerCode ) && !TextUtils.isEmpty ( service ) && !TextUtils.isEmpty ( variationCode ) && !TextUtils.isEmpty ( amount ) && !TextUtils.isEmpty ( requestId )) {
            LiveData <TransactionResource <MeterTransaction>> liveData = transactionViewModel.getTransactionLiveData ( "electricity",requestId, service, billerCode, variationCode, Double.parseDouble ( amount ), new BigInteger ( phone ), transactionRepository );
            liveData.removeObservers ( this );

            liveData.observe ( this, meterTransactionTransactionResource -> {
                extractDetails ( "electricity",meterTransactionTransactionResource );
            } );
        }


    }

    private void makeDataTransaction(String requestId, String service, String billerCode, String variationCode, String amount, String phone) {

        if (!TextUtils.isEmpty ( variationCode ) && !TextUtils.isEmpty ( service ) && !TextUtils.isEmpty ( billerCode ) && !TextUtils.isEmpty ( amount ) && !TextUtils.isEmpty ( requestId )) {
           // LiveData <TransactionResource <MeterTransaction>> liveData = transactionViewModel.getTransactionLiveData ( "internet",requestId, service, phone, variationCode, Double.parseDouble ( amount ), new BigInteger ( phone ), transactionRepository );
            LiveData <TransactionResource <MeterTransaction>> liveData = transactionViewModel.getTransactionLiveData ( "internet",requestId, service, "08011111111", variationCode, Double.parseDouble ( amount ), new BigInteger ( "08011111111" ), transactionRepository );
            liveData.removeObservers ( this );

            liveData.observe ( this, meterTransactionTransactionResource -> {
                extractDetails ( "internet",meterTransactionTransactionResource );
            } );
        }


    }

    private void makeTvTransaction(String requestId, String service, String billerCode, String variationCode, String amount, String phone) {

        if (!TextUtils.isEmpty ( billerCode ) && !TextUtils.isEmpty ( service ) && !TextUtils.isEmpty ( variationCode ) && !TextUtils.isEmpty ( amount )) {
            LiveData <TransactionResource <MeterTransaction>> liveData = transactionViewModel.getTransactionLiveData ( "tv",requestId, service, billerCode, variationCode, Double.parseDouble ( amount ), new BigInteger ( phone ), transactionRepository );
            liveData.removeObservers ( this );

            liveData.observe ( this, meterTransactionTransactionResource -> {
                extractDetails ( "tv",meterTransactionTransactionResource );
            } );
        }


    }

    private void makeAirtimeTransaction(String requestId, String service, String amount, String phone) {


        if (!TextUtils.isEmpty ( requestId ) && !TextUtils.isEmpty ( service ) && !TextUtils.isEmpty ( phone ) && !TextUtils.isEmpty ( amount )) {
            LiveData <TransactionResource <MeterTransaction>> liveData = transactionViewModel.getTransactionLiveData ("phone", requestId, service, "", "", Double.parseDouble ( amount ), new BigInteger ( "08011111111" ), transactionRepository );
            liveData.removeObservers ( this );

            liveData.observe ( this, meterTransactionTransactionResource -> {
                extractDetails ( "phone",meterTransactionTransactionResource );
            } );
        }


    }


    private  void sendSmsAfterTransaction(String phone,String token,String amount,String meterNo,String units,String date){
        SMSBody smsBody=new SMSBody ();
        SMS sms=new SMS ();
        Auth auth=new Auth ();
        auth.setUsername ( Constants.userName );
        auth.setApikey ( Constants.apiKey );
        sms.setAuth ( auth );

        Message message=new Message ();
        message.setMessagetext ( "Successful\n Amount: "+token+"\n"+"Meter No: "+meterNo +"\n"+" Units: "+ units+"\n"+"Date "+date);
        message.setSender ( Constants.senderName );
        message.setFlash ( "0");

      GsmItem gsmItem=  new GsmItem ();
      gsmItem.setMsidn ( phone );
      gsmItem.setMsgid (""+System.currentTimeMillis ());

        ArrayList<GsmItem> list=new ArrayList <> (  );
        list.add ( gsmItem);

        Recipients recipients=new Recipients ();
        recipients.setGsm (list );
        sms.setDndsender ( 1 );

        sms.setMessage ( message );
        sms.setRecipients ( recipients );

        smsBody.setsMS ( sms );

        transactionViewModel.getSmsResponseData ( Constants.smsUrl,smsBody,transactionRepository ).observe ( this, smsResponseTransactionResource -> {

            extractSmsDetails ( smsResponseTransactionResource );
        } );
    }

    public void extractSmsDetails(TransactionResource<SmsResponse> data ){
        switch (data.status) {

            case LOADING: {
                dataBinding.progressBar.setVisibility ( View.VISIBLE );

                break;
            }

            case ERROR: {

                assert data.data != null;
                if( data.data.getErrorMessage ().equalsIgnoreCase ( "No network available" )){
                    dataBinding.internet.setVisibility ( View.VISIBLE );
                }
                showDialog ( "Something went wrong please try again. Please use this reference id " + REFERENCE, false );
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );
                break;
            }
            case AUTHENTICATED: {
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );
                assert data.data != null;
                dataBinding.internet.setVisibility ( View.GONE );




                break;
            }

        }

    }

  /*  private void sendEmail(){
        final String username = "username@gmail.com";
        final String password = "password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("to-email@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = "path of file to be attached";
            String fileName = "attachmentName"
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }*/

    public void extractDetails(String rechargeType,TransactionResource <MeterTransaction> data) {
        switch (data.status) {

            case LOADING: {
                dataBinding.progressBar.setVisibility ( View.VISIBLE );

                break;
            }

            case ERROR: {

                assert data.data != null;
                if( data.data.getErrorMessage ().equalsIgnoreCase ( "No network available" )){
                   dataBinding.internet.setVisibility ( View.VISIBLE );
               }
                showDialog ( "Something went wrong please try again. Please use this reference id " + REFERENCE, false );
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );
                break;
            }
            case AUTHENTICATED: {
                dataBinding.progressBar.setVisibility ( View.INVISIBLE );
                assert data.data != null;
                dataBinding.internet.setVisibility ( View.GONE );

                String description = data.data.getResponseDescription ( );
                String amount = "₦" + data.data.getAmount ( );
                String transId = data.data.getContent ( ).getTransactions ( ).getTransactionId ( );
                String date = data.data.getTransactionDate ( ).getDate ( );
                if (description.equalsIgnoreCase ( "Transaction Successful" )) {


                    if (meterType.equalsIgnoreCase ( "prepaid" )) {
                        String token = data.data.getToken ( );
                        String units = data.data.getUnits ( );

                        showPaymentStatusDialog ( 1,amount, transId, date, token, units );
                        //insertRecordsToDB ( amount, date, token, address, meterType );
                    } else {

                        //insertRecordsToDB ( amount, date, "0", address, meterType );
                        showPaymentStatusDialog ( 1,amount, transId, date, "0", "0" );

                    }


                } else{


                    showPaymentStatusDialog ( 0,amount, transId, date,null,null );
                }
                break;
            }

        }

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

    private void showPaymentStatusDialog(int paymentStatus,String amount, String tranId, String date, String token, String units) {
        FragmentManager fragmentManager = getSupportFragmentManager ( );
         newFragment = new PaymentSuccessDialogFragment ( );
        FragmentTransaction transaction = fragmentManager.beginTransaction ( );
        transaction.setTransition ( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
        transaction.add ( android.R.id.content, newFragment ).addToBackStack ( null ).commit ( );

        String[] dateTime = date.split ( "\\s+" );

        sendSmsAfterTransaction ( phone,token,amount,meter,units,date);


        Bundle bundle = new Bundle ( );

        bundle.putString ( "amount", amount );
        bundle.putString ( "tranId", tranId );
        bundle.putString ( "date", dateTime[0] );
        bundle.putString ( "time", dateTime[1] );
        bundle.putInt ( "status", paymentStatus );
        if(paymentStatus==1) {
            bundle.putString ( "token", token );
            bundle.putString ( "units", units );
        }

        dialogShown=true;

        newFragment.setArguments ( bundle );

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
                // proceedToMainActivity ( );
                alertDialog.dismiss ( );
            }
        } );
        alertDialog.show ( );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed ( );
        return true;
    }



    private boolean isValid(String email) {
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches ( );
    }

    private void showSnackBar(String message) {
        Snackbar.make ( findViewById ( android.R.id.content ), message, Snackbar.LENGTH_LONG ).show ( );


    }

    @Override
    public void onBackPressed() {

       startMainActivity ();
       // startActivity
    }

    private void startMainActivity(){
        Intent intent=new Intent ( this,MainActivity.class );
        intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity ( intent );
    }
}