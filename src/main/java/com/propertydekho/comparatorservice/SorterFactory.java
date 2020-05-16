package com.propertydekho.comparatorservice;

public class SorterFactory
{
    private SorterFactory() {
    }


    public static PropSorter getSorter(String sorter) {
        if ("prop-price-ascend".equalsIgnoreCase(sorter)) {
            return new PropPriceSorter(true);
        }
        return null;
    }
}
