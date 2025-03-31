package com.denprog.codefestpractice2.destinations.design;

import com.denprog.codefestpractice2.R;

import java.util.ArrayList;
import java.util.List;

public class PreMadeDesigns {

    public int resId;
    public String name;
    public float price;

    public PreMadeDesigns(int resId, String name, float price) {
        this.resId = resId;
        this.name = name;
        this.price = price;
    }

    public static List<PreMadeDesigns> getPreMadeDesigns() {
        List<PreMadeDesigns> preMadeDesignsList = new ArrayList<>();
        preMadeDesignsList.add(new PreMadeDesigns(R.drawable.white_image, "Plain", 50f));
        preMadeDesignsList.add(new PreMadeDesigns(R.drawable.bday_cake, "Bday", 100f));
        preMadeDesignsList.add(new PreMadeDesigns(R.drawable.bday_cake, "Bday", 100f));
        preMadeDesignsList.add(new PreMadeDesigns(R.drawable.bday_cake, "Bday", 100f));
        return preMadeDesignsList;

    }

}
