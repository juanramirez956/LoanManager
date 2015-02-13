package com.example.user.loanmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;


public class AddDebtorActivity extends ActionBarActivity {
    private final static String LOG_TAG = "AddDebtorActivity";

    public final static String ENTIRENAME = "ENTIRENAME";
    public final static String INITLOAN  = "INITLOAN";
    public final static String INSTALLMENTS  = "INSTALLMENTS";
    public final static String DEBTORPHOTO  = "DEBTORPHOTO";
    public final static String FINALVALUE  = "FINALVALUE";

    public final static int INTEREST = 3; // 3%
    public final static int CAMERA_REQUEST  = 31415;

    private TextView mFutureValue;
    private EditText mInitValue,mEntireName;
    private RadioGroup mRgp;
    private RadioButton mRbt;
    private ImageView mImage;
    private Button mButtonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debtor);

        mEntireName = (EditText)findViewById(R.id.txt_entire_name);
        mImage = (ImageView)findViewById(R.id.imageButton);
        mInitValue = (EditText)findViewById(R.id.txt_init_loan);
        mRgp = (RadioGroup) findViewById(R.id.rgp_installments);
        mFutureValue = (TextView)findViewById(R.id.lbl_future_value);
        mButtonSave=(Button)findViewById(R.id.btn_save);

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Log.d(LOG_TAG,mEntireName.getText().toString());
                Log.d(LOG_TAG,mInitValue.getText().toString());
                intent.putExtra(ENTIRENAME,mEntireName.getText().toString());
                intent.putExtra(INITLOAN ,mInitValue.getText().toString());
                intent.putExtra(INSTALLMENTS, ((RadioButton) findViewById(mRgp.getCheckedRadioButtonId())).getText().toString());
                intent.putExtra(FINALVALUE,mFutureValue.getText().toString());

                if(mImage.getDrawable() != null){
                    mImage.buildDrawingCache();
                    Bitmap bm=((BitmapDrawable)mImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    intent.putExtra(DEBTORPHOTO ,stream.toByteArray());
                }

                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(CameraIntent, CAMERA_REQUEST);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CalcFinalValue();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        };

        mRgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup mradRadioGroup, int checkedId)
            {
                CalcFinalValue();
            }
        });

        mInitValue.addTextChangedListener(textWatcher);
    }

    private void CalcFinalValue() {
        String value = ((RadioButton)findViewById(mRgp.getCheckedRadioButtonId() )).getText().toString();
        String initValue = mInitValue.getText().toString();
        if(value.length()>0 && initValue.length()>0) {
            if (Integer.parseInt(value) > 0 && Integer.parseInt(initValue) > 0) {
                    int calculated =(Integer.parseInt(initValue) * (INTEREST) * Integer.parseInt(value)) ;
                mFutureValue.setText(Integer.toString(calculated));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_debtor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST){

            switch (resultCode){
                case RESULT_OK:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mImage.setImageBitmap(photo);
                    break;
                case RESULT_CANCELED:
                    //mTextView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
