package adriandp.feedercat.model

import androidx.databinding.BaseObservable

class DateError(
    var visibleConstraintError: Boolean = true,
    var message: String = "Loading...",
    var visibleProgress: Boolean = true
) : BaseObservable()