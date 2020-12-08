package com.feedco.note.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.feedco.note.DAO.TransactionDao;
import com.feedco.note.Models.Tables.MeterTable;

@Database(entities = {MeterTable.class}, version = 1)
public abstract class TransactionDB  extends RoomDatabase {



        private static TransactionDB INSTANCE;

        public abstract TransactionDao transactionDao();

        /*public static AppDatabase getAppDatabase(Context context) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), TransactionDB.class, "user-database")
                                // allow queries on the main thread.
                                // Don't do this on a real app! See PersistenceBasicSample for an example.
                                .allowMainThreadQueries()
                                .build();
            }
            return INSTANCE;
        }*/

        public static void destroyInstance() {
            INSTANCE = null;
        }
    }

