package ee.proekspert.promuseum.itemlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ee.proekspert.promuseum.R;
import ee.proekspert.promuseum.data.Item;
import ee.proekspert.promuseum.datasource.ItemProvider;

public class ItemListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Item> allItemsInLocation = ItemProvider.getItemProvider().findAllItemsInLocation(new Item.ItemLocation("KUMU / Main Storage"));
        // specify an adapter (see also next example)
        mAdapter = new ItemListAdapter(allItemsInLocation);
        mRecyclerView.setAdapter(mAdapter);

    }
}
