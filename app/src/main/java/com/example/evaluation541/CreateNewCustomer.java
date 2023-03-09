package com.example.evaluation541;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateNewCustomer extends AppCompatActivity {
    private static Button btnQuery;
    private static EditText editemcode;
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "http://192.168.254.15/ancuin/InsertTrans.php";
    private static String TAG_MESSAGE = "message" , TAG_SUCCESS = "success";
    private static String online_dataset = "";
    private static String fullname;
    private static String Gender;

    //Gender
    RadioButton male,female;
    View.OnClickListener MaleandFemale;

    //Spinner
    Spinner status;
    public String StatusofUser = "";
    String StringStatus[] = {"Single", "Married", "Widow", "divorced"};
    ArrayAdapter <String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_customer);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        editemcode = (EditText) findViewById(R.id.edtitemcode);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        status = (Spinner) findViewById(R.id.status);

        //btn
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = editemcode.getText().toString();
                new uploadDataToURL().execute();
            }
        });

        //Gender
        MaleandFemale = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rdoList = (RadioButton) view;
                switch (rdoList.getId()) {
                    case R.id.male:
                        Gender = "Male";
                        break;
                    case R.id.female:
                        Gender = "Female";
                        break;
                }
            }
        };
        male.setOnClickListener(MaleandFemale);
        female.setOnClickListener(MaleandFemale);

        //Spinner
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, StringStatus);
        status.setAdapter(adapter);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int stats, long l) {

                switch (stats) {
                    case 0:
                        StatusofUser = "Single";
                        break;
                    case 1:
                        StatusofUser = "Married";
                        break;
                    case 2:
                        StatusofUser = "Widow";
                        break;
                    case 3:
                        StatusofUser = "divorced";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private class uploadDataToURL extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(CreateNewCustomer.this);

        public uploadDataToURL() { }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setMessage(cMessage);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int nSuccess;
            try {
                ContentValues cv = new ContentValues();
                //insert anything in this code
                cPostSQL = " '" + fullname + "' , '" + Gender + "' , '" + StatusofUser + "' ";
                cv.put("code", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHost, "POST", cv);
                if(json != null) {
                    nSuccess = json.getInt(TAG_SUCCESS);
                    if (nSuccess == 1) {
                        online_dataset = json.getString(TAG_MESSAGE);
                        return online_dataset;
                    } else {
                        return json.getString(TAG_MESSAGE);
                    }
                } else {
                    return "HTTPSERVER_ERROR";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            String isEmpty = "";
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateNewCustomer.this);
            if (s !=null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR")) { }
                Toast.makeText(CreateNewCustomer.this, s, Toast.LENGTH_SHORT).show();
            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }
}