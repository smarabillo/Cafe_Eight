package com.example.cafeeight;

import java.util.HashMap;
import java.util.Map;

public class Class_ItemImageMapper {

    private static final Map<String, Integer> itemImageMap = new HashMap<>();

    static {
        // Add your item-image mappings here

        // Hot Coffee
        itemImageMap.put("Brewed", R.drawable.prodcut_brewed);
        itemImageMap.put("Latte", R.drawable.product_latte);
        itemImageMap.put("Cappuccino", R.drawable.product_cappuccino);

        // Iced Coffee
        itemImageMap.put("Vietnamese Coffee", R.drawable.product_vietnamesecoffee);
        itemImageMap.put("Iced Dark Mocha", R.drawable.product_iceddarkmocha);
        itemImageMap.put("Iced Vanilla Cream Latte", R.drawable.product_vanillacreamlatte);
        itemImageMap.put("Iced Hazelnut Latte", R.drawable.product_icedhazelnutlatte);

        // Frappe Drinks
        itemImageMap.put("Vanilla Bean Frappe", R.drawable.product_vanillabeanfrappe);
        itemImageMap.put("Matcha Frappe", R.drawable.product_matchafrappe);
        itemImageMap.put("Strawberry Frappe", R.drawable.product_strawberryfrappe);
        itemImageMap.put("Blueberry Frappe", R.drawable.product_blueberryfrappe);

        // Non-coffee Drinks
        itemImageMap.put("House Blend Iced Tea", R.drawable.product_houseblendicedtea);
        itemImageMap.put("Matcha Latte", R.drawable.product_matchalatte);
        itemImageMap.put("Cucumber Lemonade", R.drawable.product_cucumberemonade);
        itemImageMap.put("Strawberry Soda", R.drawable.product_strawberrysoda);

        // Protein Shake
        itemImageMap.put("Vanilla Protein Shake", R.drawable.product_vanillaproteinshake);
        itemImageMap.put("Strawberry Protein Shake", R.drawable.product_strawberryproteinshake);
        itemImageMap.put("Protein Fusion Fuel", R.drawable.product_proteinfusionfuel);
    }

    public static int getImageResourceIdForItem(String itemName) {
        int defaultImageResourceId = R.drawable.ic_launcher_foreground;
        Integer imageResourceId = itemImageMap.get(itemName);
        return imageResourceId != null ? imageResourceId : defaultImageResourceId;
    }
}
