package com.propertydekho.comparatorservice.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.propertydekho.comparatorservice.models.PropMetaDataList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AreaWisePropertiesView
{
    @JsonProperty("area_wise_properties")
    private Map<String, PropMetaDataList> areaWiseProperties;

    @JsonProperty("sorter")
    private String sorter;
}
