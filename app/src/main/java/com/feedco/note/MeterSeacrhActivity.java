package com.feedco.note;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.feedco.note.Dagger.AppComponent;
import com.feedco.note.Dagger.DaggerAppComponent;
import com.feedco.note.Models.MeterSearchModel.Content;
import com.feedco.note.Models.MeterSearchModel.MeterSearchModel;
import com.feedco.note.Repository.MeterSearchRepository;
import com.feedco.note.Resource.MeterSearchResource;
import com.feedco.note.ViewModel.CompanySelectorViewModel;
import com.feedco.note.ViewModel.MeterSearchViewModel;
import com.feedco.note.databinding.MeterSearchBinding;

import java.util.HashMap;

import javax.inject.Inject;

public class MeterSeacrhActivity extends AppCompatActivity {

    MeterSearchBinding viewBinding;
    AppComponent appComponent;
    MeterSearchViewModel meterSearchViewModel;
    private static final String TAG = "MeterSeacrhActivity";

    CompanySelectorViewModel companySelectorViewModel;

    @Inject
    MeterSearchRepository meterSearchRepository;

    String prepaid, postpaid, companyNameShort;
    HashMap <String, String> companyMap = new HashMap <> ( );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        viewBinding = DataBindingUtil.setContentView ( this, R.layout.meter_search );


        appComponent = DaggerAppComponent.builder ().getApplicationContext ( getApplication () ).build ();
        appComponent.inject ( this );

        meterSearchViewModel = new ViewModelProvider ( this ).get ( MeterSearchViewModel.class );

        setCompanyNames ( );
        companySelectorViewModel = new ViewModelProvider ( this  ).get ( CompanySelectorViewModel.class );
        viewBinding.btSearch.setOnClickListener ( v -> {
            if (validateFields ( )) {
                launchBottomSheetDialog ();
            }
        } );

        companySelectorViewModel.getCompanyName ( ).observe ( this, s -> {

            companyNameShort = s;

viewBinding.progressBar.setVisibility ( View.GONE );
            LiveData <MeterSearchResource <MeterSearchModel>> liveData = meterSearchViewModel.getMeterDetails ( prepaid==null?postpaid:prepaid, getCompanyName ( s ), prepaid==null?"postpaid":"prepaid", meterSearchRepository );
            liveData.removeObservers ( this );

            liveData.observe ( this, data -> {
                if (data != null) {
                    fetchDetails ( data );
                }
            } );


        } );




    }
    public String getCompanyName(String key){
       return companyMap.get ( key );
    }

    private HashMap <String, String> setCompanyNames() {


        companyMap.put ( "PHED", "portharcourt-electric" );
        companyMap.put ( "IKEDC", "ikeja-electric" );
        companyMap.put ( "EKEDC", "eko-electric" );
        companyMap.put ( "KEDCO", "kano-electric" );
        companyMap.put ( "JED", "jos-electric" );
        companyMap.put ( "IBEDC", "ibadan-electric" );
        return companyMap;
    }

    public boolean validateFields() {
        prepaid = viewBinding.tvPrepaid.getText ( ).toString ( );
        postpaid = viewBinding.tvPostpaid.getText ( ).toString ( );
        if (TextUtils.isEmpty ( prepaid ) && TextUtils.isEmpty ( postpaid )) {
            viewBinding.tvPrepaid.setError ( "Please Enter Meter No." );

            return false;
        }
        return true;


    }

    public void launchBottomSheetDialog() {

        CompanyListBottomSheet companyListBottomSheet = new CompanyListBottomSheet ( );
        companyListBottomSheet.show ( getSupportFragmentManager ( ), "companyDialog" );
    }

    public void fetchDetails(MeterSearchResource <MeterSearchModel> meterData) {

        switch (meterData.status) {
            case LOADING: {
                viewBinding.progressBar.setVisibility ( View.VISIBLE );
                Log.d ( TAG, "fetchDetails: Loading" );
                System.out.println ("Loading" );
                break;
            }
            case ERROR: {
                assert meterData.data != null;
                String error=meterData.data.getContent ( ).getError ( );
                viewBinding.progressBar.setVisibility ( View.GONE );

                Toast.makeText ( this, error, Toast.LENGTH_SHORT ).show ( );

                Log.d ( TAG, "fetchDetails: Error " + error );
                break;
            }
            case AUTHENTICATED: {
                viewBinding.progressBar.setVisibility ( View.GONE );
                assert meterData.data != null;
                Log.d ( TAG, "fetchDetails: meterData " + meterData.data.getContent ());
                System.out.println ("Calling " );
                launchUserDetails ( meterData.data.getContent () );


                break;
            }
        }


    }

    public void launchUserDetails(Content content) {

        Intent intent=new Intent ( this, MeterDetailActivity.class  );
       intent.putExtra ( "data",content );
        intent.putExtra ( "companyName",getCompanyName ( companyNameShort ) );
        startActivity ( intent );

    }
}
