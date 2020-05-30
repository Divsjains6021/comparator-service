package com.propertydekho.comparatorservice;

import com.propertydekho.comparatorservice.sorters.PropPriceSorter;
import com.propertydekho.comparatorservice.sorters.PropSorter;
import com.propertydekho.comparatorservice.sorters.RelevanceSorter;

public class SorterFactory
{
    private SorterFactory() {
    }


    public static PropSorter getSorter(String sorter) {
        if ("prop-price-ascend".equalsIgnoreCase(sorter)) {
            return new PropPriceSorter(true);
        }
        if ("prop-price-descend".equalsIgnoreCase(sorter)) {
            return new PropPriceSorter(false);
        }
        if ("relevancy".equalsIgnoreCase(sorter)) {
            return new RelevanceSorter();
        }
        return null;
    }
}
