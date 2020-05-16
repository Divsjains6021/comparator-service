package com.propertydekho.comparatorservice;

import com.propertydekho.comparatorservice.models.AreaPropertiesList;
import com.propertydekho.comparatorservice.models.PropFilterableSortableData;
import com.propertydekho.comparatorservice.models.PropMetaDataList;
import com.propertydekho.comparatorservice.models.PropsComparatorInput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ComparatorResource
{

    @PostMapping("/compare-properties")
    public PropMetaDataList compareProperties(@RequestBody PropsComparatorInput propsComparatorInput) {
        List<PropFilterableSortableData> properties =
                propsComparatorInput.getPropMetaDataList().getPropFilterableSortableData();
        properties.sort(SorterFactory.getSorter(propsComparatorInput.getSorter()));
        return PropMetaDataList.builder().propFilterableSortableData(properties).build();
    }

    @PostMapping("/merge-sort-props")
    public PropMetaDataList mergeProperties(@RequestBody AreaPropertiesList propertiesList) {
        List<PropFilterableSortableData> indexedProperties =
                propertiesList.getIndexedProperties().getPropFilterableSortableData();
        List<PropFilterableSortableData> nonIndexedProperties =
                propertiesList.getNonIndexedProperties().getPropFilterableSortableData();
        nonIndexedProperties.sort(SorterFactory.getSorter("prop-price-ascend"));

        List<PropFilterableSortableData> allProperties = new ArrayList<>();
        allProperties.addAll(indexedProperties);
        allProperties.addAll(nonIndexedProperties);
        allProperties.sort(SorterFactory.getSorter("prop-price-ascend"));
        return PropMetaDataList.builder().propFilterableSortableData(allProperties).build();
    }
}
