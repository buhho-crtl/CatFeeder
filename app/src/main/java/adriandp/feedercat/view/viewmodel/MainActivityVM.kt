package adriandp.feedercat.view.viewmodel

import adriandp.feedercat.BR
import adriandp.feedercat.R
import adriandp.feedercat.config.Constants.CONFIG_PATH
import adriandp.feedercat.config.Constants.MEASURES_PATH
import adriandp.feedercat.listener.ListenerDialogTime
import adriandp.feedercat.model.Config
import adriandp.feedercat.model.Data
import adriandp.feedercat.model.DateError
import adriandp.feedercat.view.adapter.AdapterFeeder
import android.content.Context
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.firebase.database.*


@Suppress("IMPLICIT_CAST_TO_ANY")
class MainActivityVM(context: Context) : BaseObservable(), ListenerDialogTime {

    private var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("/")
    var adapterFeeder: AdapterFeeder = AdapterFeeder(this)

    @Bindable
    var dataError = DateError()

    init {
        initialLoading(context)
    }

    fun initialLoading(context: Context) {
        dataError.visibleProgress = true
        notifyPropertyChanged(BR.dataError)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<Any>("Configuración")
                addItemOnList(dataSnapshot.child(CONFIG_PATH).children.toList(), list, true)
                list.add(context.getString(R.string.History))
                addItemOnList(dataSnapshot.child(MEASURES_PATH).children.reversed(), list, false)
                Log.d("MainActivity", "Total: ${list.size}")
                adapterFeeder.setList(list.toList())
                dataError.visibleConstraintError = false
                dataError.visibleProgress = false
                notifyPropertyChanged(BR.dataError)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                dataError.message = databaseError.message
                dataError.visibleProgress = false
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        ref.addListenerForSingleValueEvent(postListener)
    }

    private fun addItemOnList(
        children: List<DataSnapshot>,
        list: MutableList<Any>,
        isConfiguration: Boolean
    ) {
        children.forEach {
            val element = if (isConfiguration) {
                it.getValue(Config::class.java)!!.apply {
                    this.key = it.key!!
                }
            } else {
                it.getValue(Data::class.java)!!.apply {
                    this.key = it.key!!
                }
            }
            list.add(element)
        }
    }

/*    fun addData(context: Context) {
        DialogTimePick(context, this)
    }*/

    override fun acceptDialog(config: Config) {
        val keyNewItem = (0..999999).random().toString()
        ref.child(CONFIG_PATH).child(keyNewItem)
            .setValue(Config(hour = config.hour, minutes = config.minutes, feed = config.feed))
            .addOnSuccessListener {
                adapterFeeder.addItem(config.apply { key = keyNewItem })
            }
            .addOnFailureListener {
                Log.d("onClickCard", Log.getStackTraceString(it))
            }
    }

    fun updateConfig(config: Config) {
        ref.child(CONFIG_PATH).child(config.key!!)
            .setValue(Config(hour = config.hour, minutes = config.minutes, feed = config.feed))
            .addOnSuccessListener {
                adapterFeeder.updateListItems(config, type = false)
            }
            .addOnFailureListener {
                Log.d("onClickCard", Log.getStackTraceString(it))
            }
    }


}

