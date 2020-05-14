package com.propertydekho.comparatorservice;

import com.propertydekho.comparatorservice.models.PropFilterableSortableData;
import com.propertydekho.comparatorservice.models.PropMetaDataList;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class ComparatorResource
{

    @RequestMapping("/compare-properties")
    public PropMetaDataList compareProperties(@RequestBody PropMetaDataList propMetaDataList,
                                              @RequestBody String sorter) {
        List<PropFilterableSortableData> properties = propMetaDataList.getPropFilterableSortableData();
        properties.sort(SorterFactory.getSorter(sorter));
        return PropMetaDataList.builder().propFilterableSortableData(properties).build();
    }
}
