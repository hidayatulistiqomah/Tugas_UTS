package com.example.tugasuts.helpers

class Config {
    companion object {
        const val BASE_URL = "https://dummyjson.com/"
        const val LIMIT = 25
        const val SELECT_FIELDS =
            "title,description,category,price,stock,availabilityStatus,tags,dimensions,reviews,rating,images,thumbnail"
        const val EXTRA_PRODUCT = "extra_product"
    }
}
