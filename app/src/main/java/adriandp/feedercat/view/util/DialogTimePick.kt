package adriandp.feedercat.view.util

import adriandp.feedercat.R
import adriandp.feedercat.listener.ListenerDialogTime
import adriandp.feedercat.model.Config
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.time_picker_dialog.view.*

class DialogTimePick(
    context: Context,
    private val listenerDialogTime: ListenerDialogTime,
    config: Config? = null
) {
    val view: View = LayoutInflater.from(context).inflate(R.layout.time_picker_dialog, null)

    init {
        view.enableFeed.isChecked = config?.enable ?: false
        view.textFeed.setText(config?.feed?.toString() ?: "0")

        view.timePicker.apply {
            setIs24HourView(true)
            if (config != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = config.hour
                    minute = config.minutes
                } else {
                    currentHour = config.hour
                    currentMinute = config.minutes
                }
            }
        }

        val dialog: AlertDialog

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(view)
            .setPositiveButton(android.R.string.ok) { _, _ ->

                val feed = view.textFeed.text.toString().toInt()
                val hour: Int
                val minutes: Int

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = view.timePicker.hour
                    minutes = view.timePicker.minute
                } else {
                    hour = view.timePicker.currentHour
                    minutes = view.timePicker.currentMinute
                }
                listenerDialogTime.acceptDialog(
                    Config(
                        hour = hour,
                        minutes = minutes,
                        feed = feed,
                        enable = view.enableFeed.isChecked
                    )
                )

            }
            .setNegativeButton(android.R.string.cancel, null)
            .setCancelable(false)


        dialog = dialogBuilder.create()
        dialog.show()
    }
}