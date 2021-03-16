package com.dimitar.neighbourhoodmarket

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Item(val item: String? = "", val price: Long? = 0, var uuid: String = "")