package ee.proekspert.promuseum.itemlist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ee.proekspert.promuseum.R;
import ee.proekspert.promuseum.data.Item;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ListItemHolder> {
    private List<Item> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ListItemHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mMuseumId;
        public TextView mName;
        public CheckBox mChecked;
        public ListItemHolder(LinearLayout linearLayout) {
            super(linearLayout);
            mMuseumId = linearLayout.findViewById(R.id.list_item_museum_id);
            mName = linearLayout.findViewById(R.id.list_item_name);
            mChecked = linearLayout.findViewById(R.id.list_item_checked);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemListAdapter(List<Item> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        // create a new view
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ListItemHolder vh = new ListItemHolder(linearLayout);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListItemHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.i("ItemsListAdapter", "onBindViewHolder: position " + position + "; name: " + mDataset.get(position).getName());
        Item selectedItem = mDataset.get(position);
        holder.mMuseumId.setText(selectedItem.getMuseumId());
        holder.mName.setText(selectedItem.getName());
        holder.mChecked.setChecked(selectedItem.getStatus().isChecked());
        if (selectedItem.getStatus().isLost()) {
            holder.mChecked.setVisibility(View.INVISIBLE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.i("ItemsListAdapter", "getItemCount: " + mDataset.size());
        return mDataset.size();
    }
}