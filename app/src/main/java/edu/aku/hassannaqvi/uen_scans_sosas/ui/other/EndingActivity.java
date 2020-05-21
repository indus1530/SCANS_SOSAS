package edu.aku.hassannaqvi.uen_scans_sosas.ui.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.aku.hassannaqvi.uen_scans_sosas.R;
import edu.aku.hassannaqvi.uen_scans_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_scans_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_scans_sosas.databinding.ActivityEndingBinding;
import edu.aku.hassannaqvi.uen_scans_sosas.validator.ValidatorClass;

public class EndingActivity extends AppCompatActivity {

    ActivityEndingBinding bi;
    private int PhotoSerial;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending);
        bi.setCallback(this);
        PhotoSerial = 1;
        boolean check = getIntent().getExtras().getBoolean("complete");

        if (check) {
            bi.istatus1.setEnabled(true);
            bi.istatus2.setEnabled(false);
            bi.istatus3.setEnabled(false);
            bi.istatus4.setEnabled(false);
            bi.istatus5.setEnabled(false);
        } else {
            //fldGrpmn0823Reason.setVisibility(View.GONE);
            bi.istatus1.setEnabled(false);
            bi.istatus2.setEnabled(true);
            bi.istatus3.setEnabled(true);
            bi.istatus4.setEnabled(true);
            bi.istatus5.setEnabled(true);
        }

        MainApp.problemType = 0;
        MainApp.childCount = 0;


    }

    public void BtnContinue() {
        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() {
        MainApp.fc.setIstatus(
                bi.istatus1.isChecked() ? "1" :
                        bi.istatus2.isChecked() ? "2" :
                                bi.istatus3.isChecked() ? "3" :
                                        bi.istatus4.isChecked() ? "4" :
                                                bi.istatus5.isChecked() ? "5"
                                                        : "0");
        MainApp.fc.setEndingdatetime(dtToday);
    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);
        int updcount = db.updateEnding();
        return updcount == 1;
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.llend);
    }

    public void takePhoto(View view) {

        Intent intent = new Intent(this, TakePhoto.class);

        // Adjust Identification Information to uniquely identify every photo and link to form
        //intent.putExtra("picID", MainApp.fc.getClusterCode() + "_" + MainApp.fc.getHhno() + "_" + MainApp.childData.getName()+ "_" + PhotoSerial);
        intent.putExtra("picID", "901001" + "_" + "A-0001-001" + "_" + "1" + "_" + PhotoSerial);

        // Provide information for which photo is being taken like ChildName
        //intent.putExtra("forInfo", MainApp.childData.getName());
        intent.putExtra("forInfo", "This Household");

        if (view.getId() == bi.btnPhoto.getId()) {
            intent.putExtra("picView", "Child".toUpperCase());
            startActivityForResult(intent, 1); // Activity is started with requestCode 1 = Front
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        // Toast.makeText(this, requestCode + "_" + resultCode, Toast.LENGTH_SHORT).show();
        if (resultCode == 1) {
            Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();

            String fileName = data.getStringExtra("FileName");
            PhotoSerial++;

            bi.fileName.setText(bi.fileName.getText() + String.valueOf(PhotoSerial) + " - " + fileName + ";\r\n");
        } else {
            Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }


}
