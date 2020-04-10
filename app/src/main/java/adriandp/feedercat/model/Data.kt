package adriandp.feedercat.model

import androidx.databinding.BaseObservable
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Data(
    var key: String = "",
    var config: Boolean = false,
    var minutes: Int = 0,
    var hour: Int = 0,
    var time: Int = 0,
    var limit: Int = 0,
    var feed: Int = 0
) : BaseObservable()