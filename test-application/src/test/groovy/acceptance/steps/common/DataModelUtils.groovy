package acceptance.steps.common

import acceptance.common.AssertUtils

static def addPropertyToModel(String propertyKey, String propertyValue, def model) {
    def convertedPropertyValue = convertPropertyValue(propertyValue)
    // Handle creation of nested properties within model object
    propertyKey.split("\\.").inject(model) { object, property ->
        if (!object[property]) {
            object[property] = [:]
        }
        object[property]
    }

    def convertedPropertyKey = AssertUtils.addQuotesIfNeeded(propertyKey)
    // Now that the (potentially nested) property exists on the model object, its value can be set
    Eval.xy(model, convertedPropertyValue, "x.${convertedPropertyKey}=y")
}

static def convertPropertyValue(String propertyValue) {
    propertyValue.contains(',') ? propertyValue.split(',') : propertyValue
}