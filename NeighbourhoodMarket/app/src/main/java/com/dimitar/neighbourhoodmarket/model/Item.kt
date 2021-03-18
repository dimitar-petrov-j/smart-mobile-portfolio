package com.dimitar.neighbourhoodmarket.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Item(val item: String? = "", val price: Long? = 0,  val clientInfo: String? = "", var uuid: String = "", var isTaken: Boolean = false)