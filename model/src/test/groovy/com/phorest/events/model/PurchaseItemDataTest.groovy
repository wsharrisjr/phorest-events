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
    def "should return correct boolean value  for isX for #itemType"(ItemType itemType, boolean expectedIsService, boolean expectedIsProduct, boolean expectedIsCourseSession) {
        given:
        PurchaseItemData purchaseItemData = new PurchaseItemData(itemType: itemType)

        when:
        boolean actualIsService = purchaseItemData.isService()
        boolean actualIsProduct = purchaseItemData.isProduct()
        boolean actualIsCourseSession = purchaseItemData.isCourseSession()

        then:
        expectedIsService == actualIsService
        expectedIsProduct == actualIsProduct
        expectedIsCourseSession == actualIsCourseSession

        where:
        itemType                | expectedIsService | expectedIsProduct | expectedIsCourseSession
        ItemType.PRODUCT        | false             | true              | false
        ItemType.SERVICE        | true              | false             | false
        ItemType.COURSE_SESSION | false             | false             | true
        ItemType.UNKNOWN        | false             | false             | false
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
