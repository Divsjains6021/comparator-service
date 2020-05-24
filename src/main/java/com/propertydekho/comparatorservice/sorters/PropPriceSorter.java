package com.propertydekho.comparatorservice.sorters;

import com.propertydekho.comparatorservice.entity.PropFilterableSortableData;
public class PropPriceSorter implements PropSorter
{
    private boolean sortAscend;

    public PropPriceSorter(boolean sortAscend) {
        this.sortAscend = sortAscend;
    }

    @Override
    public int compare(PropFilterableSortableData o1, PropFilterableSortableData o2) {
        double diff = sortAscend ? o1.getPropPrice() - o2.getPropPrice() : o2.getPropPrice() - o1.getPropPrice();
        return (int) diff;
    }
}
