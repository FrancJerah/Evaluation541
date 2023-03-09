package com.example.evaluation541;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageRecords extends AppCompatActivity {

    private static Button btnQuery;
    TextView textView, txtDefault, txtDefault_gender, txtDefault_civilStatus, txtDefault_ID;
    private static EditText edtitemcode;
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "http://192.168.254.115/ancuin/SelectItemDetails.php";
    private static String urlHostDelete = "http://192.168.254.115/ancuin/delete.php";
    private static String urlHostGender = "http://192.168.254.115/ancuin/selectGender.php";
    private static String urlHostCivilStatus = "http://192.168.254.115/ancuin/selectCivilStatus.php";
    private static String urlHostID = "http://192.168.254.115/ancuin/selectId.php";
    private static String TAG_MESSAGE = "message" , TAG_SUCCESS = "success";
    private static String online_dataset = "";
    private static String cItemcode = "";
    //how to globalize string
    public static String wew = "";
    public static String gender = "";
    public static String civilstats = "";

    private String ems,gen,civ,aydi;

    String cItemSelected, cItemSelected_gender, cItemSelected_civilStatus, cItemSelected_ID;
    ArrayAdapter <String> adapter_fnames;
    ArrayAdapter <String> adapter_gender;
    ArrayAdapter <String> adapter_civilStatus;
    ArrayAdapter <String> adapter_ID;
    ArrayList <String> list_fnames;
    ArrayList <String> list_gender;
    ArrayList <String> list_civilStatus;
    ArrayList <String> list_ID;
    AdapterView.OnItemLongClickListener longItemListener_fnames;
    Context context = this;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_records);

        btnQuery = (Button) findViewById(R.id.btnQuery);
        edtitemcode = (EditText) findViewById(R.id.edtitemcode);
        txtDefault = (TextView) findViewById(R.id.tv_default);
        listView = (ListView) findViewById(R.id.listview);
        textView = (TextView) findViewById(R.id.textView4);
        txtDefault_gender = (TextView) findViewById(R.id.txt_gender);
        txtDefault_civilStatus = (TextView) findViewById(R.id.txt_civilStatus);
        txtDefault_ID = (TextView) findViewById(R.id.txt_ID);

        txtDefault.setVisibility(View.GONE);
        txtDefault_gender.setVisibility(View.GONE);
        txtDefault_civilStatus.setVisibility(View.GONE);
        txtDefault_ID.setVisibility(View.GONE);

        Toast.makeText(ManageRecords.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cItemcode = edtitemcode.getText().toString();

                new uploadDataToURL().execute();
                new Gender().execute();
                new Civil().execute();
                new id().execute();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cItemSelected = adapter_fnames.getItem(position);
                cItemSelected_gender = adapter_gender.getItem(position);
                cItemSelected_civilStatus = adapter_civilStatus.getItem(position);
                cItemSelected_ID = adapter_ID.getItem(position);

                androidx.appcompat.app.AlertDialog.Builder alert_confirm =
                        new androidx.appcompat.app.AlertDialog.Builder(context);
                alert_confirm.setMessage("Edit the records of " + " " + cItemSelected);
                alert_confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        txtDefault.setText(cItemSelected);
                        txtDefault_gender.setText(cItemSelected_gender);
                        txtDefault_civilStatus.setText(cItemSelected_civilStatus);
                        txtDefault_ID.setText(cItemSelected_ID);
                    }
                })
                return false;
            }
        } {

        }

    }
}