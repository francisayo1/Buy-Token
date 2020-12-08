package com.feedco.note.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.feedco.note.Models.Tables.MeterTable;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    public void insertTransactionDetails(MeterTable meterTable);

    @Query ( "select * from  Tbl_transaction" )
    LiveData<List<MeterTable>>  getAllTransaction();

    @Query ("delete from Tbl_transaction")
    public void deteletTable();
}
