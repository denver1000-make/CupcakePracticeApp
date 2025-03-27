package com.denprog.codefestpractice2.destinations.filling_flavor_selection;

import com.denprog.codefestpractice2.base.SelectionBase;

import java.util.ArrayList;
import java.util.List;

public class FillingFlavor extends SelectionBase {
    public FillingFlavor(String name, float price) {
        super(name, price);
    }

    public static List<FillingFlavor> getAllFillingsFlavors() {
        List<FillingFlavor> fillingFlavors = new ArrayList<>();

        // Adding items to the list
        fillingFlavors.add(new FillingFlavor("Chocolate", 1.50f));
        fillingFlavors.add(new FillingFlavor("Vanilla", 1.25f));
        fillingFlavors.add(new FillingFlavor("Strawberry", 1.75f));
        fillingFlavors.add(new FillingFlavor("Caramel", 2.00f));
        return fillingFlavors;
    }
}
