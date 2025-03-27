package com.denprog.codefestpractice2.destinations.cake_flavor_selection.obj;

import com.denprog.codefestpractice2.base.SelectionBase;

import java.util.ArrayList;
import java.util.List;

public class CakeFlavor extends SelectionBase {

    public CakeFlavor(String name, float price) {
        super(name, price);
    }

    public static List<CakeFlavor> getListOfFlavors() {
        List<CakeFlavor> cakeFlavors = new ArrayList<>();

        cakeFlavors.add(new CakeFlavor("Chocolate", 5.99f));
        cakeFlavors.add(new CakeFlavor("Vanilla", 4.99f));
        cakeFlavors.add(new CakeFlavor("Strawberry", 6.49f));
        cakeFlavors.add(new CakeFlavor("Red Velvet", 7.99f));
        cakeFlavors.add(new CakeFlavor("Lemon", 5.49f));
        return cakeFlavors;
    }
}
