package com.feedco.note;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.feedco.note.databinding.FragmentPaymentSuccessDialogBinding;
import com.feedco.note.databinding.PaymentFailureDialogBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentSuccessDialogFragment extends DialogFragment {

    private String token, date, time, transId, amount, units;
    private int paymentStatus;

    private FragmentPaymentSuccessDialogBinding viewBinding1;
    private PaymentFailureDialogBinding viewBinding2;
    public PaymentSuccessDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments ( );

        if (bundle != null) {
            amount = bundle.getString ( "amount" );
            transId = bundle.getString ( "tranId" );
            date = bundle.getString ( "date" );
            time = bundle.getString ( "time" );
            token = bundle.getString ( "token" );
            units = bundle.getString ( "units" );
            paymentStatus=bundle.getInt ( "status" );
        }

        if(paymentStatus==1){
            viewBinding1=DataBindingUtil.inflate ( inflater,R.layout.fragment_payment_success_dialog,container,false );
            return viewBinding1.getRoot ();
        }
        else{

            viewBinding2=DataBindingUtil.inflate ( inflater,R.layout.payment_failure_dialog,container,false );
            return viewBinding2.getRoot ();
        }




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );

        if(paymentStatus==1){
            viewBinding1.tvToken.setText ( token );
            viewBinding1.tvAmount.setText ( amount );
            viewBinding1.tvDate.setText ( date );
            viewBinding1.tvTransactionId.setText ( transId );
            viewBinding1.tvTime.setText ( time );
            viewBinding1.tvUnits.setText ( units );
            viewBinding1.fab.setOnClickListener ( item-> proceedToMainActivity () );

        }
        else{

                viewBinding2.tvAmount.setText ( amount );
                viewBinding2.tvDate.setText ( date );
                viewBinding2.tvTransactionId.setText ( transId );
                viewBinding2.tvTime.setText ( time );
                viewBinding2.fab.setOnClickListener ( item-> proceedToMainActivity () );


        }




    }
    private void proceedToMainActivity() {
        startActivity ( new Intent ( getActivity (), MainActivity.class ) );
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog ( savedInstanceState );

        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        return dialog;

    }
}
