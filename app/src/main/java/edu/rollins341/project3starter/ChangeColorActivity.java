package edu.rollins341.project3starter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeColorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView image;
    private View view;
    private TextView text;



    public static final String EXTRA_COLOR = "c";
    public int colorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);
        Spinner spinner = findViewById(R.id.colors_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

       @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           String item = (String)parent.getItemAtPosition(position);
           onColorSelected(item);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });

    }
    public void onColorSelected(String text) {
        Intent intent = new Intent();
        view = findViewById(R.id.color_box);
        //String text = spinner.getSelectedItem().toString();
        if (text.equals("Red")) {
            view.setBackgroundColor(getResources().getColor(R.color.red_900));
            colorId = R.color.red_900;
            intent.putExtra(EXTRA_COLOR, colorId);
            setResult(RESULT_OK, intent);
            //finish();
        }
        else if (text.equals("Magenta")) {
            view.setBackgroundColor(getResources().getColor(R.color.magenta));
            colorId = R.color.magenta;
            intent.putExtra(EXTRA_COLOR, colorId);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (text.equals("Very Peri")) {
            view.setBackgroundColor(getResources().getColor(R.color.very_peri));
            colorId = R.color.very_peri;
            intent.putExtra(EXTRA_COLOR, colorId);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (text.equals("Lavender Blossom")) {
            view.setBackgroundColor(getResources().getColor(R.color.lavender_blossom));
            colorId = R.color.lavender_blossom;
            intent.putExtra(EXTRA_COLOR, colorId);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (text.equals("Oyster Gray")) {
            view.setBackgroundColor(getResources().getColor(R.color.oyster_gray));
            colorId = R.color.oyster_gray;
            intent.putExtra(EXTRA_COLOR, colorId);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (text.equals("Tiffany Blue")) {
            view.setBackgroundColor(getResources().getColor(R.color.tiffany_blue));
            colorId = R.color.tiffany_blue;
            intent.putExtra(EXTRA_COLOR, colorId);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (text.equals("Marigold")) {
            view.setBackgroundColor(getResources().getColor(R.color.marigold));
            colorId = R.color.marigold;
            intent.putExtra(EXTRA_COLOR, colorId);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}

