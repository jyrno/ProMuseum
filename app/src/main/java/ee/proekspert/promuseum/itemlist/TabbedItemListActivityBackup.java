package ee.proekspert.promuseum.itemlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ee.proekspert.promuseum.R;
import ee.proekspert.promuseum.search.SearchActivity;

public class TabbedItemListActivityBackup extends AppCompatActivity {

    //    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_item_list);

        ((AppCompatActivity)this).getSupportActionBar().setTitle("Palo Alto / GARAGE48 Riiul");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.start_scanning_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ItemListFragment uncheckedItemsFragment = new ItemListFragment.UncheckedItemsFragment();
        adapter.addFragment(uncheckedItemsFragment);
        ItemListFragment checkedItemsFragment = new ItemListFragment.CheckedItemsFragment();
        adapter.addFragment(checkedItemsFragment);
        ItemListFragment lostItemsFragment = new ItemListFragment.LostItemsFragment();
        adapter.addFragment(lostItemsFragment);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<ItemListFragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(ItemListFragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentList.get(position).getFragmentTitleWithNumber();
        }
    }
}
