package acceptance.common

import static org.hamcrest.CoreMatchers.nullValue
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.is

static void assertValues(def t, Map<String, String> row) {

    String field = addQuotesIfNeeded(row.field)

    String result = Eval.me('t', t, "t.${field}")
    def fieldValue = row.fieldValue ?: null

    if (!fieldValue) {
        assertThat "t.${field}", result, nullValue()
    } else {
        assertThat "t.${field}", result, is(fieldValue)
    }
}

static String addQuotesIfNeeded(String field) {
    field.contains('-') ? addQuotes(field) : field
}

static String addQuotes(String field) {
    field.split("\\.").collect { String word ->
        word.contains("-") || word.isNumber() ? "'" + word + "'" : word
    }.join(".")
}