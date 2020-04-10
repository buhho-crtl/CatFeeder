package adriandp.feedercat.view.adapter

import adriandp.feedercat.BR
import adriandp.feedercat.R
import adriandp.feedercat.databinding.ItemFeedBinding
import adriandp.feedercat.databinding.ItemHeaderBinding
import adriandp.feedercat.model.Config
import adriandp.feedercat.model.Data
import adriandp.feedercat.view.viewmodel.MainActivityVM
import adriandp.feedercat.view.viewmodel.item.ItemDataVM
import adriandp.feedercat.view.viewmodel.item.ItemHeaderVM
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class AdapterFeeder(val mainActivityVM: MainActivityVM) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: MutableList<Any> = mutableListOf()
    private val HEADER = 0
    private val CONFIG = 1
    private val ITEM = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> ItemHeader(
                LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            )
            CONFIG -> ItemHolderConfig(
                LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
            )
            else -> ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
            )
        }
        /* return if ( == HEADER) {
             ItemHeader(
                 LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
             )
         } else {
             ItemHolder(
                 LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
             )
         }*/
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listData[position]) {
            is String -> HEADER
            is Config -> CONFIG
            else -> ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemHeader -> {
                holder.mBinding.setVariable(BR.model, ItemHeaderVM(listData[position] as String))
                holder.mBinding.executePendingBindings()
            }
            is ItemHolderConfig -> {
                holder.mBinding.setVariable(BR.presenter, mainActivityVM)
                holder.mBinding.setVariable(
                    BR.model,
                    ItemDataVM(config = listData[position] as Config)
                )
                holder.mBinding.executePendingBindings()
            }
            is ItemHolder -> {
                holder.mBinding.setVariable(BR.presenter, mainActivityVM)
                holder.mBinding.setVariable(BR.model, ItemDataVM(data = listData[position] as Data))
                holder.mBinding.executePendingBindings()
            }
        }
    }

    fun setList(listDataAux: List<Any>) {
        if (listData != listDataAux) {
            listData = listDataAux.toMutableList()
            notifyDataSetChanged()
        }
    }

    fun updateListItems(config: Config, type: Boolean) {
        val position = listData.indexOfFirst { it is Config && it.key == config.key }
        if (type) {
            listData.removeAt(position)
            notifyItemRemoved(position)
        } else {
            notifyItemChanged(position)
        }
    }

    fun addItem(config: Config) {
        val position = listData.indexOfLast { it is Config } + 1
        listData.add(position, config)
        notifyItemInserted(position)
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding: ItemFeedBinding = DataBindingUtil.bind(itemView)!!
    }

    inner class ItemHolderConfig(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding: ItemFeedBinding = DataBindingUtil.bind(itemView)!!
    }

    inner class ItemHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding: ItemHeaderBinding = DataBindingUtil.bind(itemView)!!
    }

}