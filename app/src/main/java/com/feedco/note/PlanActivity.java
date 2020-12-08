package com.feedco.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feedco.note.CustomClasses.CustomSnackBar;
import com.feedco.note.Dagger.AppComponent;
import com.feedco.note.Dagger.DaggerAppComponent;
import com.feedco.note.Models.PlanModel.Plans;
import com.feedco.note.Models.PlanModel.VarationsItem;
import com.feedco.note.Models.Tables.MeterTable;
import com.feedco.note.Repository.PlanRepository;
import com.feedco.note.Resource.PlanSearchResource;
import com.feedco.note.ViewModel.PlanViewModel;
import com.feedco.note.databinding.ActivityPlanBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PlanActivity extends AppCompatActivity {

    ActivityPlanBinding viewBinding;
    AppComponent appComponent;

    @Inject
    PlanRepository planRepository;

    private PlanDetailAdapter planDetailAdapter;

    private static String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        viewBinding = DataBindingUtil.setContentView ( this, R.layout.activity_plan );


        ActionBar toolbar = getSupportActionBar ( );
        toolbar.setDisplayShowHomeEnabled ( true );
        toolbar.setDisplayHomeAsUpEnabled ( true );
        toolbar.setDisplayHomeAsUpEnabled ( true );
        getSupportActionBar ( ).setTitle ( "Choose Plan" );

        //short_name like company name: PHED adn all
        companyName = getIntent ( ).getStringExtra ( "SHORT_NAME" );


        appComponent = DaggerAppComponent.builder ( ).getApplicationContext ( getApplication ( ) ).build ( );
        appComponent.inject ( this );
        viewBinding.rvPlans.setLayoutManager ( new LinearLayoutManager ( this ) );
        viewBinding.rvPlans.setHasFixedSize ( true );

        PlanViewModel planViewModel = new ViewModelProvider ( this ).get ( PlanViewModel.class );
        planViewModel.getPlansData ( planRepository, companyName ).observe ( this, this::retrieveDetails );

        planDetailAdapter = new PlanDetailAdapter ( this );
        viewBinding.rvPlans.setAdapter ( planDetailAdapter );

        viewBinding.tvRefresh.setOnClickListener ( view->{
            planViewModel.getPlansData ( planRepository, companyName ).observe ( this, this::retrieveDetails );

        } );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed ( );
        return true;
    }

    private void retrieveDetails(PlanSearchResource <Plans> planSearchResource) {

        switch (planSearchResource.status) {

            case LOADING: {
               viewBinding.progress.setVisibility ( View.VISIBLE );
            //   viewBinding.progress.setCo
                break;
            }
            case ERROR: {
                viewBinding.progress.setVisibility ( View.GONE );

                assert planSearchResource.data != null;
                if (planSearchResource.data.getResponseDescription ( ).equalsIgnoreCase ( "No network available" )) {

                    viewBinding.internet.setVisibility ( View.VISIBLE );
                } else {
                    CustomSnackBar.getInstance ( ).invokeSnackBar ( "Something went wrong" ,this);

                }
                break;
            }
            case SUCCESS: {
                viewBinding.progress.setVisibility ( View.GONE );
                assert planSearchResource.data != null;
                viewBinding.internet.setVisibility ( View.GONE );
                planDetailAdapter.setSource ( planSearchResource.data.getContent ( ).getVarations ( ) );
                break;
            }
        }

    }

    static class PlanDetailAdapter extends RecyclerView.Adapter <PlanDetailAdapter.PlanHolder> {

        List <VarationsItem> list = new ArrayList <> ( );

        Context context;

        PlanDetailAdapter(Context con) {
            this.context = con;
        }

        @NonNull
        @Override
        public PlanDetailAdapter.PlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from ( parent.getContext ( ) );
            View view = layoutInflater.inflate ( R.layout.plans_single_item, parent, false );
            return new PlanDetailAdapter.PlanHolder ( view );
        }

        @Override
        public void onBindViewHolder(@NonNull PlanDetailAdapter.PlanHolder holder, int position) {

            holder.BindTo ( holder, sortedList.get ( position ) );


        }

        SortedList <VarationsItem> sortedList = new SortedList <VarationsItem> ( VarationsItem.class, new SortedListAdapterCallback <VarationsItem> ( this ) {
            @Override
            public int compare(VarationsItem o1, VarationsItem o2) {
                return o1.getVariationAmount ( ).compareTo ( o2.getVariationAmount ( ) );
            }

            @Override
            public boolean areContentsTheSame(VarationsItem oldItem, VarationsItem newItem) {
                return oldItem.getVariationAmount ( ).equalsIgnoreCase ( newItem.getVariationAmount ( ) );
            }

            @Override
            public boolean areItemsTheSame(VarationsItem item1, VarationsItem item2) {
                return item1 == item2;
            }
        } );

        void setSource(List <VarationsItem> list) {
            if (!list.isEmpty ( )) {
                this.sortedList.addAll ( list );
            }
        }

        @Override
        public int getItemCount() {
            return sortedList.size ( );
            //return 1;
        }

        class PlanHolder extends RecyclerView.ViewHolder {

            TextView tv_price, tv_company, tv_planDesc;
            MaterialButton bt_select;
            CardView cv_planCard;


            PlanHolder(@NonNull View itemView) {
                super ( itemView );
                tv_price = itemView.findViewById ( R.id.tv_price );
                tv_company = itemView.findViewById ( R.id.tv_company );
                tv_planDesc = itemView.findViewById ( R.id.tv_planDesc );
                bt_select = itemView.findViewById ( R.id.bt_select );
                cv_planCard = itemView.findViewById ( R.id.cv_planCard );
                bt_select.setOnClickListener ( view -> {

                    Intent intent = new Intent ( context, DetailsEntry.class );
                    intent.putExtra ( "AMOUNT", sortedList.get ( getAdapterPosition ( ) ).getVariationAmount ( ) );
                    intent.putExtra ( "VARIATION_CODE", sortedList.get ( getAdapterPosition ( ) ).getVariationCode ( ) );
                    intent.putExtra ( "SHORT_NAME", companyName );
                    context.startActivity ( intent );

                } );

            }

            void BindTo(PlanHolder holder, VarationsItem varationsItem) {


                holder.tv_price.setText ( "₦" + varationsItem.getVariationAmount ( ) );
                holder.tv_company.setText ( "MTN" );
                holder.tv_planDesc.setText ( varationsItem.getName ( ) );

            }


        }
    }
}