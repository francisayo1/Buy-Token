package com.feedco.note.Resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MeterSearchResource<T> {
    @NonNull
    public final MeterSearchStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public MeterSearchResource(@NonNull MeterSearchStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> MeterSearchResource<T> authenticated (@Nullable T data) {
        return new MeterSearchResource<>(MeterSearchStatus.AUTHENTICATED, data, null);
    }

    public static <T> MeterSearchResource<T> error(@NonNull String msg, @Nullable T data) {
        return new MeterSearchResource<>(MeterSearchStatus.ERROR, data, msg);
    }

    public static <T> MeterSearchResource<T> loading(@Nullable T data) {
        return new MeterSearchResource<>(MeterSearchStatus.LOADING, data, null);
    }

    public static <T> MeterSearchResource<T> logout () {
        return new MeterSearchResource<>(MeterSearchStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum MeterSearchStatus { AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED}

}
