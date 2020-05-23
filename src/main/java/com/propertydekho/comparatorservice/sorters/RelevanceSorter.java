package com.propertydekho.comparatorservice.sorters;

import com.propertydekho.comparatorservice.models.PropFilterableSortableData;

public class RelevanceSorter implements PropSorter
{
    @Override
    public int compare(PropFilterableSortableData prop1, PropFilterableSortableData prop2) {

        double relativePrice = Math.min(prop1.getPropPrice(), prop2.getPropPrice());
        // Update relative price
        return (int) (prop1.getPropPrice() <= prop2.getPropPrice() ? relativePrice - prop2.getPropPrice() :
                prop2.getPropPrice() - relativePrice);
    }
}
