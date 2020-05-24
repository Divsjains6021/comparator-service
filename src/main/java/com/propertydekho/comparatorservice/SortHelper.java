package com.propertydekho.comparatorservice;

import com.propertydekho.comparatorservice.entity.PropFilterableSortableData;
import com.propertydekho.comparatorservice.models.PropMetaDataList;
import com.propertydekho.comparatorservice.sorters.PropSorter;
import com.propertydekho.comparatorservice.views.AreaWisePropertiesView;

import java.util.*;

public class SortHelper
{
    private SortHelper() {
    }



    public static PropMetaDataList sortProperties(AreaWisePropertiesView view) {
        PriorityQueue<PropFilterableSortableData> sortedAllQueue = new PriorityQueue<>(2,
                SorterFactory.getSorter(view.getSorter()));
        Map<String, PropMetaDataList> areaWiseProperties = view.getAreaWiseProperties();
        Map<String, Queue<PropFilterableSortableData>> areaWiseSortedProps = getSortedQueues(view);

        // Adding head of all area queues to the sorted all queue.
        areaWiseProperties.keySet()
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
            if (!nextQueue.isEmpty()) {
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
        areaWiseProperties.forEach((key, value) -> sortAreaList(sorter, value, sortedPropQueues, key));
        return sortedPropQueues;
    }

//    @Async
    private static void sortAreaList(PropSorter sorter, PropMetaDataList value, Map<String,
            Queue<PropFilterableSortableData>> sortedPropQueues, String area) {
        List<PropFilterableSortableData> properties = value.getPropFilterableSortableData();
        properties.sort(sorter);
        sortedPropQueues.put(area, new LinkedList<>(properties));
    }
}
