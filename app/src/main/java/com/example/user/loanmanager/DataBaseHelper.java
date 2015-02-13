package com.example.user.loanmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 09/02/2015.
 */
public class DataBaseHelper  extends OrmLiteSqliteOpenHelper {

    private final static String DATABASE_NAME = "LoanManager.db";
    private final static int DATABASE_VERSION = 2;
    private final static String LOG_TAG = DataBaseHelper.class.getSimpleName();

    private Dao<DebtorSingle,Integer> mDebtorDao = null;
    private Dao<DebtorPayments,Integer> mPaymentDao = null;

    public Dao<DebtorSingle, Integer> getDebtorDao() throws SQLException {
        if(mDebtorDao == null)
        {
            mDebtorDao = getDao(DebtorSingle.class);
        }
        return mDebtorDao;
    }

    public Dao<DebtorPayments, Integer> getPaymentDao() throws SQLException {
        if(mPaymentDao == null)
        {
            mPaymentDao = getDao(DebtorPayments.class);
        }
        return mPaymentDao;
    }



    public DataBaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(LOG_TAG, "CREATING DATABASE.");
            TableUtils.createTable(connectionSource, DebtorSingle.class);
            TableUtils.createTable(connectionSource,DebtorPayments.class);
        } catch (SQLException e) {
            Log.i(LOG_TAG,"ERROR CREATING DATABASE",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

      public List<DebtorSingle> selectAll() {
          List<DebtorSingle> contactsResult = null;

          try {
              Dao<DebtorSingle, Integer> contactDao = getDebtorDao();
              contactsResult = contactDao.queryForAll();

          } catch (SQLException e) {
              e.printStackTrace();
          }
          return contactsResult;
      }

        public List<DebtorPayments> selectAllPayments() {
              List<DebtorPayments> paymentsResult = null;

              try {
                  Dao<DebtorPayments,Integer> paymentDao = getPaymentDao();
                  paymentsResult = paymentDao.queryForAll();

              } catch (SQLException e) {
                  e.printStackTrace();
              }
              return paymentsResult;
    }


}


