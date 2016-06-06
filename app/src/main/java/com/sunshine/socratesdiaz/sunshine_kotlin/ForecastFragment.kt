package com.sunshine.socratesdiaz.sunshine_kotlin

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView


class ForecastFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fakeData = listOf(
                "Today - Sunny - 88 / 63",
                "Tomorrow - Foggy - 70 / 46",
                "Weds - Cloudy - 72 / 63",
                "Thurs - Rainy - 64 / 51",
                "Fri - Foggy - 70 / 46",
                "Sat - Sunny - 76 / 83"
        )

        val adapter = ArrayAdapter<String>(
                context,
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                fakeData
        )

        val rootView : View? = inflater?.inflate(R.layout.fragment_forecast, container, false)
        val listView : ListView = rootView?.findViewById(R.id.listview_forecast) as ListView
        listView.adapter = adapter

        // Inflate the layout for this fragment
        return rootView
    }

}
