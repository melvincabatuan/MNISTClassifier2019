package ph.edu.dlsu.ece626;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = Classifier.class.getSimpleName();

    private CustomView customView;
    private TextView resultTextView;
    private TextView confidenceTextView;
    private Classifier classifier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_clear).setOnClickListener(this);
        findViewById(R.id.button_classify).setOnClickListener(this);

        customView = findViewById(R.id.customView);
        resultTextView = findViewById(R.id.result);
        confidenceTextView = findViewById(R.id.confidence);

        try {
            classifier = new Classifier(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_classify:
                Bitmap scaledBitmap = customView.getBitmap(classifier.DIM_IMG_SIZE_X, classifier.DIM_IMG_SIZE_Y);
                float[] output = classifier.classify(scaledBitmap);
                int digit = (int) output[0];
                String confidence = String.format("%.04f", output[1]);
                Log.i(LOG_TAG, String.valueOf(digit) + ", " + confidence);
                resultTextView.setText(String.valueOf(digit));
                confidenceTextView.setText(confidence);
                break;
            case R.id.button_clear:
                customView.clear();
                resultTextView.setText("");
                confidenceTextView.setText("");
                break;
        }
    }
}
