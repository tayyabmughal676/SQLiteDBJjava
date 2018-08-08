package com.example.mrtayyab.sqlitedb;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private EditText mName , mID , mProfession , mSalary ;
    private Button mAdd , mView , mDelete , mUpdate , mViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        mName = findViewById(R.id.myName);
        mID = findViewById(R.id.myId);
        mProfession = findViewById(R.id.myProfession);
        mSalary = findViewById(R.id.mySalary);
        mAdd = findViewById(R.id.myAddBtn);
        mView = findViewById(R.id.myViewBtn);
        mDelete = findViewById(R.id.myDeleteBtn);
        mUpdate = findViewById(R.id.myUpdateBtn);
        mViewAll = findViewById(R.id.myViewAllBtn);

        AddData();
        getData();
        updateData();
        deleteData();
        viewAllData();

    }

    public void viewAllData() {

    mViewAll.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cursor res = myDb.getAllData();
            if(res.getCount() == 0){
                showMessage("Error " , "Nothing found");
                return;

            }
            else{
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id:" + res.getString(0)+ "\n");
                    buffer.append("Name: " + res.getString(1)+"\n\n");
                    buffer.append("Profession: " + res.getString(2)+"\n\n");
                    buffer.append("Salary: " + res.getString(3)+"\n\n");

                }

                showMessage("Data" , buffer.toString());
            }
        }
    });


    }



    public void deleteData() {
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mID.getText().toString().trim();
                if(TextUtils.isEmpty(id)){
                    mName.setError("Enter id");
                }

                Integer deleterows =myDb.daleteData(id);
                if(deleterows > 0 ){
                    Toast.makeText(getApplicationContext(), "Data deleted " , Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(), "Data could not deleted " , Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void updateData() {

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mID.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String profession = mProfession.getText().toString().trim();
                String salary = mSalary.getText().toString().trim();

                if(TextUtils.isEmpty(id)){
                    mName.setError("Enter id");
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    mName.setError("Enter name");
                    return;
                }

                if(TextUtils.isEmpty(profession)){
                    mProfession.setError("Enter profession");
                    return;
                }
                if(TextUtils.isEmpty(salary)){
                    mSalary.setError("Enter salary");
                    return;
                }

                boolean isUpdated = myDb.updateData(id ,name , profession, salary);
                if(isUpdated == true){
                    Toast.makeText(getApplicationContext(), "Data updated " , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Data could not be updated " , Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    public void  AddData(){

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String profession = mProfession.getText().toString().trim();
                String salary = mSalary.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    mName.setError("Enter name");
                    return;
                }

                if(TextUtils.isEmpty(profession)){
                    mProfession.setError("Enter profession");
                    return;
                }
                if(TextUtils.isEmpty(salary)){
                    mSalary.setError("Enter salary");
                    return;
                }

                boolean isInserted = myDb.intertData(name , profession, salary);
                if(isInserted == true){
                    Toast.makeText(getApplicationContext(), "Data inserted " , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Data could not be inserted " , Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

//// ---- GET DATA


    public void getData(){
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mID.getText().toString().trim();
                if(TextUtils.isEmpty(id)){
                    mID.setError("Enter ID");
                    return;
                }

                Cursor res = myDb.getData(id);
                String data = null;
                if(res.moveToFirst()){
                    data = "Id:" + res.getString(0)+"\n"+
                            "Name:" + res.getString(1)+"\n"+
                            "Profession:" + res.getString(2)+"\n"+
                            "Salary:"+ res.getString(3)+"\n";
                }
                showMessage("Data" , data);
            }
        });
    }

    private void showMessage(String title, String message) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
