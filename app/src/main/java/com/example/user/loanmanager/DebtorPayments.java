package com.example.user.loanmanager;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by user on 12/02/2015.
 */
public class DebtorPayments {
    public final static String ID           = "_id";
    public final static String DEBTOR_ID    = "DebtorId";
    public final static String PAYMENT      = "InitLoan";
    public final static String PERDAY       = "PerDay";

    @DatabaseField(generatedId = true,
            columnName = ID)                    private int _id;

    public DebtorPayments(String mDebtorId, String mPayment, String mPerday) {
        this.mDebtorId = mDebtorId;
        this.mPayment = mPayment;
        this.mPerday = mPerday;
    }

    @DatabaseField(columnName = DEBTOR_ID)      private String mDebtorId;
    @DatabaseField(columnName = PAYMENT)        private String mPayment;
    @DatabaseField(columnName = PERDAY)         private String mPerday;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getmDebtorId() {
        return mDebtorId;
    }

    public void setmDebtorId(String mDebtorId) {
        this.mDebtorId = mDebtorId;
    }

    public String getmPayment() {
        return mPayment;
    }

    public void setmPayment(String mPayment) {
        this.mPayment = mPayment;
    }

    public String getmPerday() {
        return mPerday;
    }

    public void setmPerday(String mPerday) {
        this.mPerday = mPerday;
    }




}
