package org.polsl.trackapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Item(
    var author: String = "",
    var title: String = "",
    var year: Int? = null
)
