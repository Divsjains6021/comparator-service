package com.propertydekho.comparatorservice.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PropsComparatorInput
{
    @JsonProperty("prop_metadata_list")
    private PropMetaDataList propMetaDataList;

    @JsonProperty("sorter")
    private String sorter;
}
