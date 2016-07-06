package com.phorest.events.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import spock.lang.Unroll

import static com.phorest.events.model.ItemType.COURSE_SESSION
import static com.phorest.events.model.ItemType.PRODUCT
import static com.phorest.events.model.ItemType.SERVICE
import static com.phorest.events.model.ItemType.UNKNOWN

@Unroll
class PurchaseItemDataTest extends Specification {

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

    def "should return correct boolean value  for isService for #itemType"() {
        given:
        PurchaseItemData purchaseItemData = new PurchaseItemData(itemType: itemType)

        when:
        boolean actualIsService = purchaseItemData.isService()

        then:
        expectedIsService == actualIsService

        where:
        itemType | expectedIsService
        PRODUCT  | false
        SERVICE  | true
        UNKNOWN  | false
    }

    def "should return correct boolean value  for isProduct for #itemType"() {
        given:
        PurchaseItemData purchaseItemData = new PurchaseItemData(itemType: itemType)

        when:
        boolean actualIsService = purchaseItemData.isProduct()

        then:
        expectedIsService == actualIsService

        where:
        itemType | expectedIsService
        PRODUCT  | true
        SERVICE  | false
        UNKNOWN  | false
    }

    def "should return correct boolean value  for isCourseSession for #itemType"() {
        given:
        PurchaseItemData purchaseItemData = new PurchaseItemData(itemType: itemType)

        when:
        boolean actualIsService = purchaseItemData.isCourseSession()

        then:
        expectedIsService == actualIsService

        where:
        itemType       | expectedIsService
        SERVICE        | false
        COURSE_SESSION | true
        UNKNOWN        | false
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
