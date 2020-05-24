package com.propertydekho.comparatorservice.sorters;

import com.propertydekho.comparatorservice.models.PropFilterableSortableData;

public class RelevanceSorter implements PropSorter
{
    @Override
    public int compare(PropFilterableSortableData prop1, PropFilterableSortableData prop2) {

        double relativePrice = Math.min(prop1.getPropPrice(), prop2.getPropPrice());
        // Update relative price
        return (int) (relativePrice - Math.max(prop1.getPropPrice(), prop2.getPropPrice()));
    }
}
