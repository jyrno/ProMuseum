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
        Item.ItemLocation garage48Shelve = new Item.ItemLocation("Palo Alto / GARAGE48 Riiul");

        Item item = new Item("0201472125", "TKM 38951 L34", "https://i.imgur.com/9LyTkfP.jpg"/*"e0ce0449-8156-413b-afb1-b934ee78e888"*/, "Estonia kontserdisaali lühter", "Arhitekt Alar Kotli", "Kahjustusi ei ole", Item.ItemStatus.UNCHECKED, Item.ItemState.SATISFACTORY, garage48Shelve);
        Log.w("ItemProvider", "InitAllItems: item " + item);
        allItems.add(item);

        item = new Item("0201699391", "TKM 38952 L34", "https://i.imgur.com/IazFfGD.jpg" /*"3ccfa93f-322d-4fc6-b40b-22cc6d9a490f"*/, "Liv-, Est- und Curlaendisches Privatrecht", "pealkirja keel: saksa keel , ainese keel: saksa keel", "Veidi mõranenud külgede pealt", Item.ItemStatus.UNCHECKED, Item.ItemState.SATISFACTORY, garage48Shelve);
        Log.w("ItemProvider", "InitAllItems: item " + item);
        allItems.add(item);

        item = new Item("0202444499", "TKM 38953 L34", "https://i.imgur.com/XimSgfK.jpg" /*"23f854dd-fe23-46e7-9156-b6de07889e2a"*/, "Tass. KALEVIPOJA-ainelise portselanmaaliga", "This is very cool stuff", "", Item.ItemStatus.UNCHECKED, Item.ItemState.GOOD, garage48Shelve);
        Log.w("ItemProvider", "InitAllItems: item " + item);
        allItems.add(item);

        item = new Item("0201198362", "TKM 38953 L34", "feef0e32-5109-473d-a84d-6a044465b58f", "Taldrik", "Carl Cristian Ficki fajansimanufaktuur", "", Item.ItemStatus.LOST, Item.ItemState.SATISFACTORY, garage48Shelve);
        Log.w("ItemProvider", "InitAllItems: item " + item);
        allItems.add(item);

        item = new Item("0212345", "TKM 38954 L34", "f4ba9620-3dba-4471-94bb-671e4cc2dc13", "Very Cool Painting", "This is very cool stuff", "Even no damage", Item.ItemStatus.UNCHECKED, Item.ItemState.VERY_GOOD, locationMuseumKumu);
        Log.w("ItemProvider", "InitAllItems: item " + item);
        allItems.add(item);

        item = new Item("0223456", "TKM 38955 L34", "613eae22-47fc-495d-b031-c539b78b7c67", "Very Bad Painting", "This is very bad stuff", "Sooo damaged", Item.ItemStatus.UNCHECKED, Item.ItemState.VERY_BAD, locationMuseumKumu);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("0234567", "TKM 38956 L34", "8984c4e4-3829-4c5d-a887-4962ced71ca0","Okeish Paining", "This is quite okeish stuff", "Just old", Item.ItemStatus.UNCHECKED, Item.ItemState.SATISFACTORY, locationMuseumKumu);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("0245678", "TKM 38957 L34", "a7468f01-1a1e-48d5-9739-d4408c9ef500","Lost Painting", "This was very cool but got lost", "No idea as it is lost", Item.ItemStatus.LOST, Item.ItemState.VERY_GOOD, locationMuseumKumu);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("0256789", "TKM 38958 L34", "c5946e2a-d971-4149-b01e-1c28e6c67826","Dummy Painting", "no description", "no damage", Item.ItemStatus.CHECKED, Item.ItemState.GOOD, locationMuseumKumu);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("0201472", "TKM 38959 L34", "ec9afd14-f8ec-423c-958c-5c166652a305","Old USSR Poster", "From the 90s", "gonna tear soon", Item.ItemStatus.CHECKED, Item.ItemState.BAD, locationMuseumKumu);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("0202585", "TKM 38960 L34", "d5646dbc-46f8-4c2e-b715-12d63674a5f8","Portrey of one Unknown Italian landlord ", "Unknown author", "no damage", Item.ItemStatus.UNCHECKED, Item.ItemState.GOOD, locationMuseumKumu);
        Log.w("ItemProvider", "InitAllItems: item " + item);

        allItems.add(item);
        item = new Item("0203698", "TKM 38961 L34", "cb74814c-cf8c-44ff-b6ca-15ed41ffac15","Estonian Landscapes", "Journal from the exhibition", "", Item.ItemStatus.UNCHECKED, Item.ItemState.VERY_GOOD, locationMuseumKumu);
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
