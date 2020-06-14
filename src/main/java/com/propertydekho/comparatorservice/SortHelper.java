package com.propertydekho.comparatorservice;

import com.propertydekho.comparatorservice.models.PropFilterableSortableData;
import com.propertydekho.comparatorservice.models.PropMetaDataList;
import com.propertydekho.comparatorservice.sorters.PropSorter;
import com.propertydekho.comparatorservice.views.AreaWisePropertiesView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class SortHelper
{
    private SortHelper() {
    }



    public static PropMetaDataList sortProperties(AreaWisePropertiesView view) {
        PriorityQueue<PropFilterableSortableData> sortedAllQueue = new PriorityQueue<>(2,
                SorterFactory.getSorter(view.getSorter()));
        Map<String, Queue<PropFilterableSortableData>> areaWiseSortedProps = getSortedQueues(view);

        // Adding head of all area queues to the sorted all queue.
        areaWiseSortedProps.keySet()
                .forEach(area -> {
                    Queue<PropFilterableSortableData> areaQueue = areaWiseSortedProps.get(area);
                    if (!areaQueue.isEmpty()) {
                        sortedAllQueue.add(areaQueue.remove());
                    }
                });


        List<PropFilterableSortableData> sortedAllProperties = new ArrayList<>();

        while (!sortedAllQueue.isEmpty()) {
            PropFilterableSortableData removedProperty = sortedAllQueue.remove();
            sortedAllProperties.add(removedProperty);

            Queue<PropFilterableSortableData> nextQueue = areaWiseSortedProps.get(removedProperty.getArea());
            if (nextQueue != null && !nextQueue.isEmpty()) {
                sortedAllQueue.add(nextQueue.remove());
            }

        }
        return PropMetaDataList.builder()
                .propFilterableSortableData(sortedAllProperties)
                .build();
    }

    private static Map<String, Queue<PropFilterableSortableData>> getSortedQueues(AreaWisePropertiesView view) {

        Map<String, PropMetaDataList> areaWiseProperties = view.getAreaWiseProperties();
        PropSorter sorter = SorterFactory.getSorter(view.getSorter());
        Map<String, Queue<PropFilterableSortableData>> sortedPropQueues = new HashMap<>();
        areaWiseProperties.entrySet()
                .parallelStream()
                .forEach(entry -> sortAreaList(sorter, entry.getValue(), sortedPropQueues, entry.getKey()));
        return sortedPropQueues;
    }

    private static void sortAreaList(PropSorter sorter, PropMetaDataList value, Map<String,
            Queue<PropFilterableSortableData>> sortedPropQueues, String area) {
        List<PropFilterableSortableData> properties = value.getPropFilterableSortableData();
        properties.sort(sorter);
        sortedPropQueues.put(area, new LinkedList<>(properties));
    }
}
