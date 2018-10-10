package com.atlanticlawnandgarden.alg_adminmatic



import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_eq_crew.*
import kotlinx.android.synthetic.main.frag_eq_crew.view.*


class FragEqCrew : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.frag_eq_crew, container, false)

        view.eq_row.setOnClickListener { view ->
            val myIntent = Intent(activity, Equipment::class.java)
            startActivity(myIntent)
        }

        return view


    }


}


