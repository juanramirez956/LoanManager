package com.example.user.loanmanager;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by user on 11/02/2015.
 */

@DatabaseTable
public class DebtorSingle {

    public final static String ID           = "_id";
    public final static String ENTIRENAME   = "EntireName";
    public final static String INITLOAN     = "InitLoan";
    public final static String PHOTO        = "Photo";

    @DatabaseField(generatedId = true,
            columnName = ID)                    private int _id;
    @DatabaseField(columnName = ENTIRENAME)     private String mEntireName;
    @DatabaseField(columnName = INITLOAN)       private int mInitLoan;
    @DatabaseField(columnName = PHOTO,
    dataType = DataType.BYTE_ARRAY)             private byte[] mPhoto;


    public String getmEntireName() {
        return mEntireName;
    }

    public void setmEntireName(String mEntireName) {
        this.mEntireName = mEntireName;
    }

    public int getmInitLoan() {
        return mInitLoan;
    }

    public void setmInitLoan(int mInitLoan) {
        this.mInitLoan = mInitLoan;
    }

    public byte[] getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(byte[] mPhoto) {
        this.mPhoto = mPhoto;
    }
}
