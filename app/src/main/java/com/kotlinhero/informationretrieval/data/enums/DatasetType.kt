package com.kotlinhero.informationretrieval.data.enums

enum class DatasetType(val key: String) {
    ANTIQUE("antique"),
    LIFESTYLE("lifestyle"),
    LIFESTYLE_QUERIES("lifestyle-queries"),
    ANTIQUE_QUERIES("antique-queries"),
    ANTIQUE_CRAWLED("antique-crawled"),
    LIFESTYLE_CRAWLED("lifestyle-crawled");

    override fun toString(): String {
        return this.key
    }
}