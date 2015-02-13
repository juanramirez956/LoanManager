package com.example.user.loanmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 11/02/2015.
 */
public class DebtorListAdapter extends ArrayAdapter<DebtorSingle> {
    private  List<DebtorSingle> mContactsDebtors;
    private  Context mContext;

    public DebtorListAdapter(Context context, List<DebtorSingle> DebtorsList) {
        super(context,R.layout.debtor_card, DebtorsList);
        mContext = context;
        mContactsDebtors= DebtorsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if(convertView != null)
        {
            rowView = convertView;

        }
        else
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.debtor_card,parent,false);
            if(rowView !=null)
            {
                TextView txv_EntireName =(TextView)rowView.findViewById(R.id.entire_name);
                txv_EntireName.setText(mContactsDebtors.get(position).getmEntireName());

                byte[] byteArray = mContactsDebtors.get(position).getmPhoto();

                if(byteArray != null){
                    ImageView imageViewPhoto = (ImageView)rowView.findViewById(R.id.photo);
                    Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    imageViewPhoto.setImageBitmap(bm);
                }


            }
        }
        return rowView;
    }
}
