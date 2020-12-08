package com.feedco.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feedco.note.ViewModel.CompanySelectorViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class CompanyListBottomSheet extends BottomSheetDialogFragment {

    CompanySelectorViewModel companySelectorViewModel;
    RecyclerView rv_company;
    MeterCompanyAdapter meterCompanyAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static String rechargeType;

    public CompanyListBottomSheet() {
        // Required empty public constructor
    }

    public static CompanyListBottomSheet newInstance(String param1, String param2) {
        CompanyListBottomSheet fragment = new CompanyListBottomSheet ( );
        Bundle args = new Bundle ( );
        args.putString ( ARG_PARAM1, param1 );
        args.putString ( ARG_PARAM2, param2 );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments ( ) != null) {
            rechargeType = getArguments ( ).getString ( ARG_PARAM1 );
           // mParam2 = getArguments ( ).getString ( ARG_PARAM2 );
        }
        return inflater.inflate ( R.layout.bottomsheet_dialog_meter, container, false );

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv_company = view.findViewById ( R.id.rv_company );

        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager ( 2,LinearLayoutManager.VERTICAL );
       // rv_company.setLayoutManager ( new LinearLayoutManager ( getActivity ( ), LinearLayoutManager.HORIZONTAL, false ) );
        rv_company.setLayoutManager ( staggeredGridLayoutManager );

       /* function to setup the adapter for different companies based on the recharge type*/
        showCompanyTypes ( rechargeType );
        rv_company.setAdapter ( meterCompanyAdapter );



        companySelectorViewModel = new ViewModelProvider ( Objects.requireNonNull ( getActivity ( ) ) ).get ( CompanySelectorViewModel.class );


    }

    public void showCompanyTypes(String rechargeType){

        switch (rechargeType){

            case "electricity" : meterCompanyAdapter = new MeterCompanyAdapter ( getFullNames ( ), getShortNames ( ),imageList() );


            break;
            case "internet" : meterCompanyAdapter = new MeterCompanyAdapter ( getDataFullNames ( ), getDataShortNames ( ),getInternetImage () );
            break;
            case "phone" : meterCompanyAdapter = new MeterCompanyAdapter ( getAirtimeFullNames ( ), getAirtimeShortNames ( ),getInternetImage ());
            break;
            case "tv" : meterCompanyAdapter = new MeterCompanyAdapter ( getTvFullNames ( ), getTvShortNames ( ),getTvImages ());
            break;

        }
    }

    /*private HashMap<String,String> getCompanyNames() {

        HashMap <String, String> companyMap = new HashMap <> ( );

        companyMap.put ( "PHED", "Port Harcourt" );
        companyMap.put ( "IKEDC", "IKEJA" );
        companyMap.put ( "EKEDC", "EKO" );
        companyMap.put ( "KEDCO", "KANO" );
        companyMap.put ( "JED", "JOS" );
        companyMap.put ( "IBEDC", "IBADAN" );
        return companyMap;
    }*/


    private List<Integer> getInternetImage(){
        List <Integer> list = new ArrayList <> ( );
        list.add ( R.drawable.mtn_logo );
        list.add ( R.drawable.glo_logo );
        list.add ( R.drawable.airtel_logo );
        list.add ( R.drawable.etisalat );

        return list;
    }
    private List<Integer> getTvImages(){
        List <Integer> list = new ArrayList <> ( );
        list.add ( R.drawable.dstv_logo );
        list.add ( R.drawable.gotv_logo );
        list.add ( R.drawable.startimes );

        return list;
    }
    private List<String> getTvFullNames(){
        List <String> list = new ArrayList <> ( );
        list.add ( "DSTV" );
        list.add ( "GOTV" );
        list.add ( "Startimes" );

        return list;
    }
    private List<String> getTvShortNames(){
        List <String> list = new ArrayList <> ( );
        list.add ( "dstv" );
        list.add ( "gotv" );
        list.add ( "startimes" );

        return list;
    }

    private List<String> getDataFullNames(){
        List <String> list = new ArrayList <> ( );
        list.add ( "MTN Data" );
        list.add ( "GLO Data" );
        list.add ( "Airtel Data" );
        list.add ( "9Mobile Data" );

        return list;
    }
    private List<String> getDataShortNames(){
        List <String> list = new ArrayList <> ( );
        list.add ( "mtn-data" );
        list.add ( "glo-data" );
        list.add ( "airtel-data" );
        list.add ( "etisalat-data" );

        return list;
    }
    private List<String> getAirtimeFullNames(){
        List <String> list = new ArrayList <> ( );
        list.add ( "MTN Airtime" );
        list.add ( "GLO Airtime" );
        list.add ( "Airtel Airtime" );
        list.add ( "9Mobile Airtime" );

        return list;
    }
    private List<String> getAirtimeShortNames(){
        List <String> list = new ArrayList <> ( );
        list.add ( "mtn" );
        list.add ( "glo" );
        list.add ( "airtel" );
        list.add ( "etisalat" );

        return list;
    }


    public List <String> getFullNames() {
        List <String> list = new ArrayList <> ( );
        list.add ( "Port Harcourt" );
        list.add ( "IKEJA" );
        list.add ( "EKO" );
        list.add ( "KANO" );
        list.add ( "IBADAN" );
        list.add ( "JOS" );

        return list;
    }
    public List <String> getShortNames() {
        List <String> list = new ArrayList <> ( );
        list.add ( "portharcourt-electric" );
        list.add ( "ikeja-electric" );
        list.add ( "eko-electric" );
        list.add ( "kano-electric" );
        list.add ( "ibadan-electric" );
        list.add ( "jos-electric" );
        return list;
    }

    public List <Integer> imageList(){
        List <Integer> list = new ArrayList <> ( );
        list.add ( R.drawable.phed_logo );
        list.add ( R.drawable.ikeja_logo );
        list.add ( R.drawable.eko_logo );
        list.add ( R.drawable.kano_logo );
        list.add ( R.drawable.ibadan_logo );
        list.add ( R.drawable.jos_logo );


        return list;
    }




    class MeterCompanyAdapter extends RecyclerView.Adapter <MeterCompanyAdapter.CompanyHolder> {

        Context context;

        List <String> fullNames, shortNames;List<Integer>imageList;


        MeterCompanyAdapter(List <String> fullNames, List <String> shortNames,List <Integer> imageList) {
            this.fullNames = fullNames;
            this.shortNames = shortNames;
            this.imageList=imageList;

        }

        @NonNull
        @Override
        public CompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from ( parent.getContext ( ) );
            View view = layoutInflater.inflate ( R.layout.company_single_item, parent, false );
            return new CompanyHolder ( view );
        }

        @Override
        public void onBindViewHolder(@NonNull CompanyHolder holder, int position) {

            int imageName=imageList.get ( position );
            holder.BindTo ( holder, fullNames.get ( position ), shortNames.get ( position ),imageName );


        }

        @Override
        public int getItemCount() {
            return fullNames.size ( );
        }

        class CompanyHolder extends RecyclerView.ViewHolder {

            TextView tv_companyFull;
            ImageView im_companyImage;
            MaterialCardView cv_companyCard;

            public CompanyHolder(@NonNull View itemView) {
                super ( itemView );
                tv_companyFull = itemView.findViewById ( R.id.tv_fullName );
                im_companyImage = itemView.findViewById ( R.id.im_companyImage );
                cv_companyCard = itemView.findViewById ( R.id.cv_companyCard );

                cv_companyCard.setOnClickListener ( v -> {

                    if(rechargeType.equalsIgnoreCase ( "internet" )||rechargeType.equalsIgnoreCase ( "tv" )){

                        startActivity ( new Intent ( getActivity (),PlanActivity.class ).putExtra ( "SHORT_NAME",shortNames.get ( getAdapterPosition () ) ) );
                    }
                    else{
                        startActivity ( new Intent ( getActivity (),DetailsEntry.class ).putExtra ( "SHORT_NAME",shortNames.get ( getAdapterPosition () ) ) );

                    }

                } );


            }

            public void BindTo(CompanyHolder holder, String fullName, String shortName,int imageName) {

                holder.tv_companyFull.setText ( fullName );
                holder.im_companyImage.setImageDrawable ( getResources ().getDrawable ( imageName ) );
            }


        }


    }

}

