package com.feedco.note.Dagger;

import android.app.Application;

import androidx.appcompat.widget.AppCompatImageView;

import com.feedco.note.Amount_BottomSheetFrag;
import com.feedco.note.DetailsEntry;
import com.feedco.note.MainActivity;
import com.feedco.note.MeterDetailActivity;
import com.feedco.note.MeterSeacrhActivity;
import com.feedco.note.PaymentGatewayActivity;
import com.feedco.note.PlanActivity;
import com.feedco.note.RetrofitInterceptor.NetworkInterceptor;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,MeterSearchClientModule.class,TransactionApiClientModule.class,PlanSearchClientModule.class})
public interface AppComponent {


    //void inject(MainActivity mainActivity);
    void inject(MeterSeacrhActivity meterSeacrhActivity);
    void inject(MeterDetailActivity meterDetailActivity);

    void inject(PaymentGatewayActivity paymentGatewayActivity);
    void inject(MainActivity mainActivity);
    void inject(PlanActivity mainActivity);
    void inject(DetailsEntry DetailsActivity);

    void inject(NetworkInterceptor interceptor);


    @Component.Builder
    interface  Builder{

        AppComponent build();

        @BindsInstance
    Builder getApplicationContext(Application application);

    }


}
