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
public class PropFilter {

    @JsonProperty("filter_type")
    private String filterType;

    @JsonProperty("filter_value")
    private String filterValue;
}
