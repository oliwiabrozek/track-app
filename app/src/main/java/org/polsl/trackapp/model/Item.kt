package org.polsl.trackapp.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Item(
    var author: String = "",
    var title: String = "",
    var year: Int? = null,
    var firebaseKey: String? = null
): Serializable
