package adriandp.feedercat.view.binding

import adriandp.feedercat.model.Data
import adriandp.feedercat.view.adapter.AdapterFeeder
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class BindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("dividerDrawable")
        fun setDividerDrawable(recyclerView: RecyclerView, adapter: AdapterFeeder) {
            var layoutManager =
                LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)

            /* val dividerItemDecoration = DividerItemDecoration(
                 recyclerView.context,
                 layoutManager.orientation
             )

             recyclerView.addItemDecoration(dividerItemDecoration)*/

            layoutManager = GridLayoutManager(recyclerView.context, 2)
            layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {

                    val element = adapter.listData[position]

                    val HEADER = 2
                    val CONFIG = 1

                    return if (element is String || (element as? Data)?.config == false) {
                        HEADER
                    } else {
                        CONFIG
                    }


                }
            }
            recyclerView.layoutManager = layoutManager
        }

        @JvmStatic
        @BindingAdapter("bind:customHeight")
        fun setLayoutHeight(view: View, height: Float) {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(height.toInt(), height.toInt(), height.toInt(), height.toInt())

            view.layoutParams = layoutParams


        }
    }
}