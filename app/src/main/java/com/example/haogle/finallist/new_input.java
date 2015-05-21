package com.example.haogle.finallist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gc.materialdesign.views.Button;


public class new_input extends Activity {

    public final static int RESULT_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_input);
        setContentView(R.layout.myedit);

        Button add_it=(Button)findViewById(R.id.add_it);
        final EditText myedittext=(EditText)findViewById(R.id.myedittext);


        add_it.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //setContentView(R.layout.myedit);
                Intent intent = new Intent();
                final String three = (myedittext.getText().toString()); //获取用户输入的结果值
                intent.putExtra("value", three);
                setResult(RESULT_CODE , intent);
                finish();

            }
        });


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_new_input, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
