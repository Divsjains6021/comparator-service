package com.propertydekho.comparatorservice;

import com.propertydekho.comparatorservice.models.PropFilterableSortableData;
import com.propertydekho.comparatorservice.models.PropMetaDataList;
import com.propertydekho.comparatorservice.models.PropsComparatorInput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
