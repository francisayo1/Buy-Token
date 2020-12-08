package com.feedco.note.Resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TransactionResource<T> {
    @NonNull
    public final MeterSearchStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public TransactionResource(@NonNull MeterSearchStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> TransactionResource <T> authenticated (@Nullable T data) {
        return new TransactionResource <> ( MeterSearchStatus.AUTHENTICATED, data, null);
    }

    public static <T> TransactionResource <T> error(@NonNull String msg, @Nullable T data) {
        return new TransactionResource <> ( MeterSearchStatus.ERROR, data, msg);
    }

    public static <T> TransactionResource <T> loading(@Nullable T data) {
        return new TransactionResource <> ( MeterSearchStatus.LOADING, data, null);
    }

    public static <T> TransactionResource <T> logout () {
        return new TransactionResource <> ( MeterSearchStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum MeterSearchStatus { AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED}

}
