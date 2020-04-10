package adriandp.feedercat.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Config(
    @get:Exclude var key: String? = null,
    var minutes: Int = 0,
    var hour: Int = 0,
    var feed: Int = 0
)