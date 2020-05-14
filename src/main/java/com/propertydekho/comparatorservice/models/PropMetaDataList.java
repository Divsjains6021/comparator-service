package com.propertydekho.comparatorservice.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PropMetaDataList {


    private List<PropFilterableSortableData> propFilterableSortableData;

    public PropMetaDataList() {
    }

    public PropMetaDataList(List<PropFilterableSortableData> propFilterableSortableData) {
        this.propFilterableSortableData = propFilterableSortableData;
    }
}
