package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.RequestQueue

class ItemListAdapter(private val list: ArrayList<ItemCard>,
                          private val context: Context): RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindView(list[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_row,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    var volleyRequest: RequestQueue? = null
    var itemsList: ArrayList<ItemCard>? = null
    var itemAdapter: ItemListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null





    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemname = itemView.findViewById<TextView>(R.id.itemname)
        var type = itemView.findViewById<TextView>(R.id.txt_description)
        var price = itemView.findViewById<TextView>(R.id.txt_price_unit)
        var linkButton = itemView.findViewById<CardView>(R.id.itemRow)

        fun bindView(itemCard: ItemCard) {
            itemname.text = itemCard.itemname
            type.text = itemCard.type
            price.text = itemCard.price+" / "+itemCard.unit



            linkButton.setOnClickListener {
                var intent = Intent(context, Item::class.java)
                intent.putExtra("ID",itemCard.itemID.toString())
                intent.putExtra("TYPE",itemCard.type.toString())

                context.startActivity(intent)
            }
        }
    }



}