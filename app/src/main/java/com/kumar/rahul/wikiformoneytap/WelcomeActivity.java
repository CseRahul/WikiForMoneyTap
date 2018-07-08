package com.kumar.rahul.wikiformoneytap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * @author Rahul Kumar on 08.07.2018.
 * @version 1.0
 *
 **/

public class WelcomeActivity extends AppCompatActivity {
    Button searchWiki;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toast.makeText(this,"Please Make Sure Your Internet is working. ",Toast.LENGTH_SHORT).show();
        searchText=(EditText) findViewById(R.id.searchText);
        searchWiki=(Button) findViewById(R.id.searchwiki);

        searchWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                intent.putExtra("search_item",""+searchText.getText().toString());
                startActivity(intent);
            }
        });



    }
}
