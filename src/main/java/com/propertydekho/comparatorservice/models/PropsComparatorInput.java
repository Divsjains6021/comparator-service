package com.propertydekho.comparatorservice.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropsComparatorInput
{
    @JsonProperty("prop_metadata_list")
    private PropMetaDataList propMetaDataList;
    @JsonProperty("sorter")
    private String sorter;

    public PropsComparatorInput() {
    }

    public PropsComparatorInput(PropMetaDataList propMetaDataList, String sorter) {
        this.propMetaDataList = propMetaDataList;
        this.sorter = sorter;
    }
}
