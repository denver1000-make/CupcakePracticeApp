package com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.obj;

import com.denprog.codefestpractice2.base.SelectionBase;

import java.util.List;

public class WhippedCreamFlavor extends SelectionBase {
    public WhippedCreamFlavor(String name, float price) {
        super(name, price);
    }
    public static List<WhippedCreamFlavor> getWhippedCreamFlavor() {
        List<WhippedCreamFlavor> whippedCreamFlavors = List.of(
                new WhippedCreamFlavor("Vanilla", 2.99f),
                new WhippedCreamFlavor("Chocolate", 3.49f),
                new WhippedCreamFlavor("Strawberry", 3.99f),
                new WhippedCreamFlavor("Caramel", 4.29f),
                new WhippedCreamFlavor("Hazelnut", 4.49f)
        );
        return whippedCreamFlavors;
    }
}
