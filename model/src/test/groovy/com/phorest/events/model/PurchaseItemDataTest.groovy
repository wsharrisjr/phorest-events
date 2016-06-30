package com.phorest.events.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import spock.lang.Unroll

class PurchaseItemDataTest extends Specification {

    @Unroll
    def "should deserialize json to expected type"() {
        given:
        ObjectMapper objectMapper = new ObjectMapper()

        when:
        PurchaseItemData result = objectMapper.readValue(json, PurchaseItemData)

        then:
        result.class == expectedType

        where:
        json                          | expectedType
        productPurchaseItemDataJson() | ProductPurchaseItemData
        voucherPurchaseItemDataJson() | VoucherPurchaseItemData
        otherPurchaseItemDataJson()   | PurchaseItemData
    }

    @Unroll
    def "should return correct boolean value  for isX for #itemType"(ItemType itemType, boolean expectedIsService, boolean expectedIsProduct) {
        given:
        PurchaseItemData purchaseItemData = new PurchaseItemData(itemType: itemType)

        when:
        boolean actualIsService = purchaseItemData.isService()
        boolean actualIsProduct = purchaseItemData.isProduct()

        then:
        expectedIsService == actualIsService
        expectedIsProduct == actualIsProduct

        where:
        itemType         | expectedIsService | expectedIsProduct
        ItemType.PRODUCT | false             | true
        ItemType.SERVICE | true              | false
        ItemType.UNKNOWN | false             | false
    }

    private static String productPurchaseItemDataJson() {
        '{"itemType":"PRODUCT","productCategory":{"id":"category1"}}'
    }

    private static String voucherPurchaseItemDataJson() {
        '{"itemType":"VOUCHER","voucherData":{"id":"category1"}}'
    }

    private static String otherPurchaseItemDataJson() {
        '{"itemType":"UNKNOWN","name": "some item"}'
    }

}
