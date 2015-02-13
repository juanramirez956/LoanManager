package com.example.user.loanmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DebtorsListFragment extends Fragment {
    private final  String  LOG_TAG = getClass().getSimpleName();
    public static final int REQUEST_CODE = 0;
    DebtorListAdapter mAdapter;
    List<DebtorSingle> debtorsList = new ArrayList<>();
    DataBaseHelper mDBHelper = null;

    public DebtorsListFragment() {
    }

    public DataBaseHelper getDBHelper() {
        if (mDBHelper == null)
            mDBHelper= OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);
        return mDBHelper;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_debtors_main, container, false);
        setHasOptionsMenu(true);
        final ListView listView = (ListView)rootView.findViewById(android.R.id.list);
        try {
            Dao<DebtorSingle,Integer> dao = getDBHelper().getDebtorDao();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        debtorsList = getDBHelper().selectAll();
        if (debtorsList.size()!=0)
        {
            mAdapter = new DebtorListAdapter(getActivity(),debtorsList);
            listView.setAdapter(mAdapter);
        }
        else
        {
            TextView emptyText = (TextView)rootView.findViewById(android.R.id.empty);
            listView.setEmptyView(emptyText);
        }
        mAdapter = new DebtorListAdapter(getActivity(),debtorsList);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent intent = new Intent(getActivity(),RegisterPaymentActivity.class);
                                                intent.putExtra("ID",Long.toString(id));
                                                intent.putExtra("POSITION",Integer.toString(position));
                                                startActivity(intent);
                                            }
                                        }
        );
        return rootView;
    }

    private void saveContactInDatabase(DebtorSingle debtor){
        try{
            Dao<DebtorPayments, Integer> dao2 = getDBHelper().getPaymentDao();
            Dao<DebtorSingle, Integer> dao = getDBHelper().getDebtorDao();
            int id = dao.create(debtor);
            Log.d("ID DEBTOR", Integer.toString(id));
            for(int i =0; i<30;i++)
            {
                DebtorPayments debtorPayments = new DebtorPayments(Integer.toString(i),Integer.toString(debtor.getmInitLoan()),"1000");
                dao.create(debtorPayments);
            }

        }catch (SQLException e){
            Log.e(LOG_TAG, "Can´t insert Contact into database " + mDBHelper.getDatabaseName(), e);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_debtorlist_fragment,menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case Activity.RESULT_OK:
                DebtorSingle debtor= new DebtorSingle();
                debtor.setmEntireName(data.getStringExtra(AddDebtorActivity.ENTIRENAME));
                debtor.setmInitLoan(Integer.parseInt(data.getStringExtra(AddDebtorActivity.INITLOAN)));
                debtor.setmPhoto(data.getByteArrayExtra(AddDebtorActivity.DEBTORPHOTO));
                debtorsList.add(debtor);
                saveContactInDatabase(debtor);
                mAdapter.notifyDataSetChanged();
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(getActivity(), R.string.canceled_message, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        Boolean handled=false;
        switch (menuId){
            case R.id.add_debtor:
                Intent createTaskActivity = new Intent(getActivity(), AddDebtorActivity.class);
                startActivityForResult(createTaskActivity, REQUEST_CODE);
                handled = true;
                break;
        }
        if(!handled)
        {
            handled = super.onOptionsItemSelected(item);
        }

        return handled;
    }

    @Override
    public void onDestroy() {
        if(mDBHelper != null){
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
        super.onDestroy();
    }
}
