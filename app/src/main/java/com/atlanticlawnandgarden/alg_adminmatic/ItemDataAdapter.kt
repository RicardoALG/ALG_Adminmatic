package com.atlanticlawnandgarden.alg_adminmatic

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

import java.util.ArrayList

class ItemDataAdapter(private val mArrayList: ArrayList<AndroidVersion>) : RecyclerView.Adapter<ItemDataAdapter.ViewHolder>(), Filterable {
    private var mFilteredList: ArrayList<AndroidVersion>? = null

    init {
        mFilteredList = mArrayList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemDataAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ItemDataAdapter.ViewHolder, i: Int) {

        var sItemName = mFilteredList!![i].getName()

        viewHolder.itemname.text= sItemName.toString()
//        viewHolder.tv_version.setText(mFilteredList!![i].getVer())
//        viewHolder.tv_api_level.setText(mFilteredList!![i].getApi())
    }

    override fun getItemCount(): Int {
        return mFilteredList!!.size
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList
                } else {

                    val filteredList = ArrayList<AndroidVersion>()

                    for (androidVersion in mArrayList) {

                        if (androidVersion.getApi().toLowerCase().contains(charString) || androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getVer().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion)
                        }
                    }

                    mFilteredList = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                mFilteredList = filterResults.values as ArrayList<AndroidVersion>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemname: TextView
//        private val tv_version: TextView
 //       private val tv_api_level: TextView

        init {

            itemname = view.findViewById(R.id.itemname) as TextView
//          txt_description = view.findViewById(R.id.txt_description) as TextView
//          txt_price_unit = view.findViewById(R.id.txt_price_unit) as TextView

        }
    }

}