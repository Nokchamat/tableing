package com.zerobase.zerobasetableing.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StoreDetailDto {

    Long storeId;
    String name;
    String category;

}
