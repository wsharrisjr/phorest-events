package com.phorest.events.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ItemType {
    OUTSTANDING_BALANCE_PMT,
    COURSE_SESSION,
    COURSE,
    SERVICE_REWARD,
    PRODUCT_REWARD,
    PRODUCT,
    PACKAGE_ITEM,
    SPECIAL_OFFER_ITEM,
    SERVICE,
    VOUCHER,
    MEMBERSHIP,
    OPEN_SALE,
    // todo annotate with @JsonEnumDefaultValue when jackson 2.8.0 will be released
    UNKNOWN;

    private static Map<String, ItemType> itemTypeMapping;

    static {
        itemTypeMapping = Arrays.asList(ItemType.values())
                .stream()
                .collect(Collectors.toMap(ItemType::name, Function.identity()));
    }

    @JsonCreator
    public static ItemType fromString(String itemTypeName) {
        return itemTypeMapping.getOrDefault(itemTypeName, UNKNOWN);
    }

    public boolean isService() {
        return this == SERVICE;
    }

    public boolean isProduct() {
        return this == PRODUCT;
    }
}
