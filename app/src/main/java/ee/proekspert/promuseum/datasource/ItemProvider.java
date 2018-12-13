package ee.proekspert.promuseum.datasource;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ee.proekspert.promuseum.data.Item;

public class ItemProvider {

    private static final ItemProvider provider = new ItemProvider();

    public static ItemProvider getItemProvider() {
        return provider;
    }

    private List<Item> allItems = new ArrayList<>();

    private ItemProvider() {
        initAllItems();
    }

    private void initAllItems() {
        Item.ItemLocation locationMuseumKumu = new Item.ItemLocation("KUMU");
        Item.ItemLocation locationStorageKumuMainStorage = new Item.ItemLocation("KUMU / Main Storage");

        Item item = new Item("12345", "TKM 38954 L34", "Very Cool Painting", "This is very cool stuff", "Even no damage", Item.ItemStatus.UNCHECKED, Item.ItemState.VERY_GOOD, locationMuseumKumu);
        Log.w("ItemProvider", "InitAllItems: item " + item);
        allItems.add(item);

        item = new Item("23456", "TKM 38955 L34", "Very Bad Painting", "This is very bad stuff", "Sooo damaged", Item.ItemStatus.UNCHECKED, Item.ItemState.VERY_BAD, locationStorageKumuMainStorage);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("34567", "TKM 38956 L34", "Okeish Paining", "This is quite okeish stuff", "Just old", Item.ItemStatus.UNCHECKED, Item.ItemState.SATISFACTORY, locationStorageKumuMainStorage);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("45678", "TKM 38957 L34", "Lost Painting", "This was very cool but got lost", "No idea as it is lost", Item.ItemStatus.LOST, Item.ItemState.VERY_GOOD, locationStorageKumuMainStorage);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("56789", "TKM 38958 L34", "Dummy Painting", "no description", "no damage", Item.ItemStatus.CHECKED, Item.ItemState.GOOD, locationStorageKumuMainStorage);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("01472", "TKM 38959 L34", "Old USSR Poster", "From the 90s", "gonna tear soon", Item.ItemStatus.CHECKED, Item.ItemState.BAD, locationStorageKumuMainStorage);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("02585", "TKM 38960 L34", "Portrey of one Unknown Italian landlord ", "Unknown author", "no damage", Item.ItemStatus.UNCHECKED, Item.ItemState.GOOD, locationStorageKumuMainStorage);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("03698", "TKM 38961 L34", "Estonian Landscapes", "Journal from the exhibition", "", Item.ItemStatus.UNCHECKED, Item.ItemState.VERY_GOOD, locationStorageKumuMainStorage);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);

        Log.w("ItemProvider", "Put all the items!!! total count: " + allItems.size());
    }

    public List<Item> getAllItems() {
        return Collections.unmodifiableList(allItems);
    }

    public Item findById(String id) {
        for (Item item : allItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> findAllItemsInLocation(Item.ItemLocation location) {
        List<Item> filteredItems = new ArrayList<>();

        Log.w("ItemProvider", "findAllItemsInLocation: location for filtering : " + location);

        for (Item item : allItems) {
            Log.w("ItemProvider", "findAllItemsInLocation: Cheking : " + item.getLocation());
            if (location.equals(item.getLocation())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
