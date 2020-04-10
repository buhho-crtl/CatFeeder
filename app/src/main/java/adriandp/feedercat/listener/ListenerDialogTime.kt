package adriandp.feedercat.listener

import adriandp.feedercat.model.Config
import android.content.Context
import androidx.appcompat.app.AlertDialog

interface ListenerDialogTime {
    fun acceptDialog(config: Config)
    fun deleteConfig(
        config: Config,
        context: Context,
        dialog: AlertDialog
    )
}
