package ee.proekspert.promuseum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import ee.proekspert.promuseum.R;

public final class ItemActivity extends AppCompatActivity {

    private static final String TAG = "item";

    private static int i = 1;
    @Nullable
    public static String item_code;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.item);
        TextView code = findViewById(R.id.item_code5);
        code.setText("lkasj dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka dlka jdlask " + i);
        code.setText(code.getText() + "\n" + item_code);
        i++;
    }

}
