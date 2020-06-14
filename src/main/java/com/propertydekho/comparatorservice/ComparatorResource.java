package com.propertydekho.comparatorservice;

import com.propertydekho.comparatorservice.models.AreaPropertiesList;
import com.propertydekho.comparatorservice.models.PropFilterableSortableData;
import com.propertydekho.comparatorservice.models.PropMetaDataList;
import com.propertydekho.comparatorservice.models.PropsComparatorInput;
import com.propertydekho.comparatorservice.views.AreaWisePropertiesView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ComparatorResource
{

    public static final String DEFAULT_SORTER = "prop-price-ascend";

    @PostMapping("/compare-properties")
    public PropMetaDataList compareProperties(@RequestBody PropsComparatorInput propsComparatorInput) {
        List<PropFilterableSortableData> properties =
                propsComparatorInput.getPropMetaDataList().getPropFilterableSortableData();
        properties.sort(SorterFactory.getSorter(propsComparatorInput.getSorter()));
        return PropMetaDataList.builder().propFilterableSortableData(properties).build();
    }

    @RequestMapping("/merge-sort-props")
    public PropMetaDataList mergeProperties(@RequestBody AreaPropertiesList propertiesList) {

        // Step 1: Sort all non-indexed properties
        List<PropFilterableSortableData> sortedNonIndexedProperties =
                propertiesList.getNonIndexedProperties().getPropFilterableSortableData();
        sortedNonIndexedProperties.sort(SorterFactory.getSorter(DEFAULT_SORTER));

        // Step 2: Put all non-indexed(new) properties in the area wise sorted map
        Map<String, PropMetaDataList> indexedProperties = propertiesList.getIndexedProperties();
//        indexedProperties = sortInitProps(indexedProperties);
        if (!sortedNonIndexedProperties.isEmpty()) {
            indexedProperties.put("newProperties",
                    PropMetaDataList.builder().propFilterableSortableData(sortedNonIndexedProperties).build());
        }


        Map<String, PropMetaDataList> areaWiseSortedProperties = new HashMap<>(indexedProperties);

        AreaWisePropertiesView view = AreaWisePropertiesView.builder()
                .areaWiseProperties(areaWiseSortedProperties)
                .sorter(DEFAULT_SORTER)
                .build();


        return SortHelper.sortProperties(view);
    }

    // This method will not be used
//    private Map<String, PropMetaDataList> sortInitProps(Map<String, PropMetaDataList> indexedProperties) {
//        Map<String, PropMetaDataList> sortedIndexedProps = new HashMap<>();
//        indexedProperties.forEach((area, props) -> {
//            List<PropFilterableSortableData> propFilterableSortableData = props.getPropFilterableSortableData();
//            propFilterableSortableData.sort(SorterFactory.getSorter(DEFAULT_SORTER));
//            sortedIndexedProps.put(area,
//                    PropMetaDataList.builder().propFilterableSortableData(propFilterableSortableData).build());
//        });
//        return sortedIndexedProps;
//    }


    public PropMetaDataList mergeProperties(@RequestBody AreaWisePropertiesView view) {
        return SortHelper.sortProperties(view);
    }
}
