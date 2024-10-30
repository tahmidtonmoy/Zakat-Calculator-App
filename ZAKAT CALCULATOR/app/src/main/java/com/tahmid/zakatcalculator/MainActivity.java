package com.tahmid.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    AdView mAdView;
    EditText gold, silver;
    TextView text;
    private InterstitialAd mInterstitialAd;
    ImageButton button;
    TextToSpeech textToSpeech;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gold = findViewById(R.id.gold);
        silver = findViewById(R.id.silver);
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);

        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gold.length() > 0 && silver.length() > 0) {
                    String sgold = gold.getText().toString();
                    float fgold = Float.parseFloat(sgold);
                    String ssilver = silver.getText().toString();
                    float fsilver = Float.parseFloat(ssilver);
                    float zakat = 0;

                    if (fgold >= 600000 && fsilver < 50000) {
                        zakat = (float) ((fgold * 2.5) / 100);
                    } else if (fgold < 600000 && fsilver >= 50000) {
                        zakat = (float) ((fsilver * 2.5) / 100);
                    } else if (fgold >= 600000 && fsilver >= 50000) {
                        zakat = (float) ((fgold + fsilver) * 2.5 / 100);
                    } else {
                        zakat = 0;

                    }
                    text.setText("Your Zakat is:" + zakat);
                    Toast.makeText(MainActivity.this, "Your Zakat is:"+zakat, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Your zakat is" + zakat, TextToSpeech.QUEUE_FLUSH, null, null);
                }

                else {
                    if (gold.length() <= 0) {
                        gold.setError("Enter Gold Amount");
                        Toast.makeText(MainActivity.this, "Enter Gold Amount", Toast.LENGTH_SHORT).show();
                        textToSpeech.speak("Enter Gold Amount", TextToSpeech.QUEUE_FLUSH, null, null);
                    } else if (silver.length() <= 0) {
                        silver.setError("Enter Silver Amount");
                        Toast.makeText(MainActivity.this, "Enter Silver Amount", Toast.LENGTH_SHORT).show();
                        textToSpeech.speak("Enter Silver Amount", TextToSpeech.QUEUE_FLUSH, null, null);
                    } else {
                        Toast.makeText(MainActivity.this, "Something Is Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit")
                .setMessage("Do You Want to Exit?")
                .setIcon(R.drawable.exit)
                .setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Yes Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finishAndRemoveTask();
                    }
                })
                .show();

    }
}