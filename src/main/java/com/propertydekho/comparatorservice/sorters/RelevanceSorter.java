package com.propertydekho.comparatorservice.sorters;

import com.propertydekho.comparatorservice.models.PropFilterableSortableData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RelevanceSorter implements PropSorter {
    private static final Map<String, Double> areaAvgPriceMap = Collections.unmodifiableMap(new HashMap<String,
            Double>() {{
        put("Whitefield", 1000.0);
        put("Bellandur", 7184.0);
    }});

    private static final Map<String, Double> constructionAvgPriceMap = Collections.unmodifiableMap(new HashMap<String
            , Double>() {{
        put("Under Construction", 1.5);
        put("Ready To Move", 1.0);
    }});

    private static final Map<String, Double> saleAvgPriceMap = Collections.unmodifiableMap(new HashMap<String
            , Double>() {{
        put("Resale", 1.25);
        put("New", 1.0);

    }});

    @Override
    public int compare(PropFilterableSortableData prop1, PropFilterableSortableData prop2) {
        PropFilterableSortableData minProp;
        PropFilterableSortableData maxProp;

        if (prop1.getPropPrice() <= prop2.getPropPrice()) {
            minProp = prop1;
            maxProp = prop2;
        } else {
            minProp = prop2;
            maxProp = prop1;
        }

        double adjustedPriceOfMinProp = adjustPrice(minProp, maxProp);

        return (int) (minProp == prop1 ? adjustedPriceOfMinProp - maxProp.getPropPrice() :
                maxProp.getPropPrice() - adjustedPriceOfMinProp);
    }

    private double adjustPrice(PropFilterableSortableData minProp, PropFilterableSortableData maxProp) {
        double minPropPricePerSqft = minProp.getPropPrice() / minProp.getSqft();
        Double pricePerSqftOfMinArea = areaAvgPriceMap.get(minProp.getArea());
        // Step 1: Check how expensive is the current flat in its area(Can be negative)
        double expensivePercent = ((minPropPricePerSqft - pricePerSqftOfMinArea) / pricePerSqftOfMinArea);
        double costOfAvgFlatInMaxAreaWithSameSqft = areaAvgPriceMap.get(maxProp.getArea()) * maxProp.getSqft();
        // Step 2: Shift the current flat in max area flat
        //propPriceInMaxArea will account for sqft and area. This means after calculating this field, we are assuming
        // both properties are in same area. Also their sq ft areas are same
        double propPriceInMaxArea =
                costOfAvgFlatInMaxAreaWithSameSqft + (costOfAvgFlatInMaxAreaWithSameSqft * expensivePercent);


        propPriceInMaxArea += ((constructionAvgPriceMap.get(minProp.getConstructionStatus()) -
                constructionAvgPriceMap.get(maxProp.getConstructionStatus())
        ) * propPriceInMaxArea);
        propPriceInMaxArea += ((saleAvgPriceMap.get(minProp.getSaleType()) - saleAvgPriceMap.get(maxProp.getSaleType()))
                * propPriceInMaxArea);

        return propPriceInMaxArea;
    }
}
