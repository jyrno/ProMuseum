package ee.proekspert.promuseum.itemlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ee.proekspert.promuseum.R;
import ee.proekspert.promuseum.data.Item;
import ee.proekspert.promuseum.datasource.ItemProvider;

public abstract class ItemListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int numberOfItemsShown = 0;
    private List<Item> filteredByStatus = new ArrayList<>();

    public ItemListFragment() {
        List<Item> allItemsInLocation = ItemProvider.getItemProvider().findAllItemsInLocation(new Item.ItemLocation("Palo Alto / GARAGE48 Riiul"));

        for (Item item : allItemsInLocation) {
            if (itemShouldBeShown(item)) {
                filteredByStatus.add(item);
            }
        }

        numberOfItemsShown = filteredByStatus.size();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    abstract boolean itemShouldBeShown(Item item);

    abstract String getFragmentTitle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.activity_item_list, container, false);
        mRecyclerView = fragmentView.findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new ItemListAdapter(filteredByStatus);
        mRecyclerView.setAdapter(mAdapter);
        return fragmentView;
    }

    public String getFragmentTitleWithNumber() {
        return "" + numberOfItemsShown + "\n" + getFragmentTitle().toUpperCase();
    }

    public static class CheckedItemsFragment extends ItemListFragment {
        @Override
        boolean itemShouldBeShown(Item item) {
            return item.getStatus().isChecked();
        }

        @Override
        String getFragmentTitle() {
            return "CHECKED";
        }
    }

    public static class UncheckedItemsFragment extends ItemListFragment {
        @Override
        boolean itemShouldBeShown(Item item) {
            return !item.getStatus().isChecked() && !item.getStatus().isLost();
        }

        @Override
        String getFragmentTitle() {
            return "UNCHECKED";
        }
    }

    public static class LostItemsFragment extends ItemListFragment {
        @Override
        boolean itemShouldBeShown(Item item) {
            return item.getStatus().isLost();
        }

        @Override
        String getFragmentTitle() {
            return "LOST";
        }
    }


}
