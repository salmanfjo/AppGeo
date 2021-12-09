package com.example.foyjoo.appgeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeActivity(View view) {

        Button mButton = (Button) findViewById(R.id.button);
        EditText editText = (EditText) findViewById(R.id.editText);
        String str = editText.getText().toString();

        Intent communeActivity = new Intent(this, CommuneActivity.class);

        communeActivity.putExtra("editText", str);
        startActivity(communeActivity);
    }
}
