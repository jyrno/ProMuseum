package ee.proekspert.promuseum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import ee.proekspert.promuseum.R;

public final class ItemActivity extends AppCompatActivity {

    private static final String TAG = "item";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.item);
        TextView code = findViewById(R.id.item_code5);
        Log.i("aa", code.toString());
        code.setText("lkasj dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka jdlask");
        code.setTextColor(2378468);
    }

}
