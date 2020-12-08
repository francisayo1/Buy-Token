package com.feedco.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import com.feedco.note.Models.Tables.MeterTable;
import com.feedco.note.databinding.RechargeOptionsBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    //SummaryBinding viewBinding;

    RechargeOptionsBinding viewBinding;

    //  RecyclerView rv_transaction;

   /* @Inject
    TransactionDB transactionDB;

    AppComponent appComponent;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        viewBinding = DataBindingUtil.setContentView ( this, R.layout.recharge_options );

        viewBinding.cvElectricity.setOnClickListener ( v -> {
            launchBottomSheetDialog ("electricity" );
        } );
        viewBinding.cvInternet.setOnClickListener ( v -> {
            launchBottomSheetDialog ("internet" );
        } );
        viewBinding.cvPhone.setOnClickListener ( v -> {
            launchBottomSheetDialog ("phone" );
        } );
        viewBinding.cvTv.setOnClickListener ( v -> {
            launchBottomSheetDialog ("tv" );
        } );

       /* rv_transaction = viewBinding.rvTransaction;

        rv_transaction.setLayoutManager ( new LinearLayoutManager ( this ) );
        rv_transaction.setHasFixedSize ( true );

        MeterTransaction meterTransactionAdapter = new MeterTransaction (this );
        rv_transaction.setAdapter ( meterTransactionAdapter );


        viewBinding.chipPrepaid.setOnClickListener ( view -> {
            launchMeterSearchActivity ( );

        } );

        viewBinding.chipPostpaid.setOnClickListener ( view -> {
            launchMeterSearchActivity ( );
        } );

        appComponent = DaggerAppComponent.builder ( ).getApplicationContext ( getApplication ( ) ).build ( );
        appComponent.inject ( this );
       // transactionDB.transactionDao ().deteletTable ();


        transactionDB.transactionDao ( ).getAllTransaction ( ).observe (
                this, meterTables -> {
                    if (meterTables.size ()!=0) {
                        System.out.println ("Meter size "+meterTables.size () );
                        meterTransactionAdapter.setSource ( meterTables );
                        viewBinding.viewFlipper.showNext ( );
                    }
                    else{
                        System.out.println ("Meter size1 "+meterTables.size () );
                        //meterTransactionAdapter.setSource ( meterTables );
                    }


                }
        );*/


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ( ).inflate ( R.menu.main, menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ( );
        if (id == R.id.history) {
            //launchCartActivity ( );
            startActivity ( new Intent ( this,ContactDetails.class ) );

        }

        return super.onOptionsItemSelected ( item );
    }

    public void launchBottomSheetDialog(String rechargeType) {

        CompanyListBottomSheet companyListBottomSheet = CompanyListBottomSheet.newInstance ( rechargeType,null );
        companyListBottomSheet.show ( getSupportFragmentManager ( ), "companyDialog" );
    }

    public void launchMeterSearchActivity() {

        startActivity ( new Intent ( this, MeterSeacrhActivity.class ) );
    }


    static class MeterTransaction extends RecyclerView.Adapter <MeterTransaction.TransactionHolder> {

        List <MeterTable> list = new ArrayList <> ( );

        Context context;

        MeterTransaction(Context con) {
            this.context = con;
        }

        @NonNull
        @Override
        public MeterTransaction.TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from ( parent.getContext ( ) );
            View view = layoutInflater.inflate ( R.layout.transactions_singleitem, parent, false );
            return new TransactionHolder ( view );
        }

        @Override
        public void onBindViewHolder(@NonNull MeterTransaction.TransactionHolder holder, int position) {

            holder.BindTo ( holder, sortedList.get ( position ) );


        }

        SortedList <MeterTable> sortedList = new SortedList <> ( MeterTable.class, new SortedListAdapterCallback <MeterTable> ( this ) {
            @Override
            public int compare(MeterTable o1, MeterTable o2) {
                return o1.getAmount ( ).compareTo ( o2.getAmount ( ) );
            }

            @Override
            public boolean areContentsTheSame(MeterTable oldItem, MeterTable newItem) {
                return oldItem.getTransactionId ( ).equalsIgnoreCase ( newItem.getTransactionId ( ) );
            }

            @Override
            public boolean areItemsTheSame(MeterTable item1, MeterTable item2) {
                return item1.getTransactionId ( ).equalsIgnoreCase ( item2.getTransactionId ( ) );
            }
        } );

        void setSource(List <MeterTable> list) {
            if (!list.isEmpty ( )) {
                this.sortedList.addAll ( list );
            }
        }

        @Override
        public int getItemCount() {
            System.out.println ( "List Size " + list.size ( ) );
            return sortedList.size ( );
            //return 1;
        }

        static class TransactionHolder extends RecyclerView.ViewHolder {

            TextView tv_amount, tv_status, tv_token, tv_receipt, tv_time;


            TransactionHolder(@NonNull View itemView) {
                super ( itemView );
                tv_amount = itemView.findViewById ( R.id.tv_amount );
                tv_status = itemView.findViewById ( R.id.tv_status );
                tv_token = itemView.findViewById ( R.id.tv_token );
                tv_receipt = itemView.findViewById ( R.id.tv_receipt );
                tv_time = itemView.findViewById ( R.id.tv_time );

            }

            void BindTo(MeterTransaction.TransactionHolder holder, MeterTable meterTable) {


                holder.tv_time.setText ( meterTable.getTime ( ) );
                holder.tv_token.setText ( "Token:" + meterTable.getToken ( ) );
                holder.tv_amount.setText ( meterTable.getAmount ( ) );
                holder.tv_receipt.setText ( meterTable.getAddress ( ) );
                holder.tv_status.setText ( meterTable.getMeterType ( ) );
            }


        }
    }


}
