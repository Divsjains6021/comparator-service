package com.propertydekho.comparatorservice.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.propertydekho.comparatorservice.models.PropMetaDataList;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AreaWisePropertiesView
{
    @JsonProperty("area_wise_properties")
    private Map<String, PropMetaDataList> areaWiseProperties;

    @JsonProperty("sorter")
    private String sorter;

    public AreaWisePropertiesView() {
    }

    public AreaWisePropertiesView(Map<String, PropMetaDataList> areaWiseProperties, String sorter) {
        this.areaWiseProperties = areaWiseProperties;
        this.sorter = sorter;
    }
}
