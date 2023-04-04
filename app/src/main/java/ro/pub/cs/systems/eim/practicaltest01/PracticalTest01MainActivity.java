package ro.pub.cs.systems.eim.practicaltest01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Button pressMeButton;
    Button pressMeTooButton;
    EditText leftEditText;
    EditText rightEditText;
    Integer leftClicks = 0;
    Integer rightClicks = 0;

    Button navigateToSecondaryActivityButton;


    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_EXTRA, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        pressMeButton = findViewById(R.id.press_me_button);
        pressMeTooButton = findViewById(R.id.press_me_too_button);
        leftEditText = findViewById(R.id.left_edit_text);
        rightEditText = findViewById(R.id.right_edit_text);
        navigateToSecondaryActivityButton = findViewById(R.id.navigate_to_another_activity_button);

        pressMeButton.setOnClickListener(it -> {
            leftClicks++;
            startPracticalService();
            leftEditText.setText(String.valueOf(leftClicks));
        });

        pressMeTooButton.setOnClickListener(it -> {
            rightClicks++;
            startPracticalService();
            rightEditText.setText(String.valueOf(rightClicks));
        });

        navigateToSecondaryActivityButton.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            intent.putExtra(Constants.LEFT_TEXT, leftClicks);
            intent.putExtra(Constants.RIGHT_TEXT, rightClicks);
            startActivityForResult(intent, Constants.REQUEST_CODE);
        });

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
        getApplicationContext().stopService(intent);
    }

    private void startPracticalService() {
        if (leftClicks + rightClicks > 5) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
            intent.putExtra("leftText", leftClicks);
            intent.putExtra(Constants.RIGHT_TEXT, rightClicks);
            getApplicationContext().startService(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Constants.LEFT_TEXT, leftClicks);
        outState.putInt(Constants.RIGHT_TEXT, rightClicks);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        leftClicks = savedInstanceState.getInt(Constants.LEFT_TEXT);
        rightClicks = savedInstanceState.getInt(Constants.RIGHT_TEXT);

        leftEditText.setText(String.valueOf(leftClicks));
        rightEditText.setText(String.valueOf(rightClicks));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Resultat oke", Toast.LENGTH_LONG).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Resultat not oke", Toast.LENGTH_LONG).show();
            }
        }
    }


}