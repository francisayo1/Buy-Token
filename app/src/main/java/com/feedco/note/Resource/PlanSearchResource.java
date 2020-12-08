package com.feedco.note.Resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PlanSearchResource <T>{
    @NonNull
    public final PlanSearchStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public PlanSearchResource(@NonNull PlanSearchStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> PlanSearchResource<T> success (@Nullable T data) {
        return new PlanSearchResource<>( PlanSearchResource.PlanSearchStatus.SUCCESS, data, null);
    }

    public static <T> PlanSearchResource<T> error(@NonNull String msg, @Nullable T data) {
        return new PlanSearchResource<>( PlanSearchResource.PlanSearchStatus.ERROR, data, msg);
    }

    public static <T> PlanSearchResource<T> loading(@Nullable T data) {
        return new PlanSearchResource<>( PlanSearchResource.PlanSearchStatus.LOADING, data, null);
    }



    public enum PlanSearchStatus { SUCCESS, ERROR, LOADING, NOT_AUTHENTICATED}
}
