package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    TextView resultText;
    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        resultText = findViewById(R.id.number_of_clicks_text_view);
        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);

        okButton.setOnClickListener(it -> {
            setResult(RESULT_OK, null);
            finish();
        });

        cancelButton.setOnClickListener(it -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                resultText.setText("0");
            } else {
                Integer left = Integer.parseInt(String.valueOf(extras.getInt("leftText")));
                Integer right = Integer.parseInt(String.valueOf(extras.getInt("rightText")));
                resultText.setText(String.valueOf(left + right));
            }
        } else {
            resultText.setText(savedInstanceState.getString("resultText"));
        }
    }
}