package com.example.haogle.finallist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;


public class Setting_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SnackBar(Setting_activity.this,"you can contact with me huhao1996@gmail.com","miao",new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                }).show();
            }
        });
        findViewById(R.id.aboutbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View flatButton) {
                Dialog dialog = new Dialog(Setting_activity.this, "About", "Thanks to ListView Animations and MaterialDesign library on Github.com");
                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                      //  Toast.makeText(Setting_activity.this, "Click accept button", 1).show();
                    }
                });
                dialog.setOnCancelButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(Setting_activity.this, "Click cancel button", 1).show();
                    }
                });
                dialog.show();


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting_activity, menu);
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
}
