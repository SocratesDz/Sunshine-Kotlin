package com.sunshine.socratesdiaz.sunshine_kotlin

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


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

    class FetchWeatherTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            var urlConnection : HttpURLConnection? = null
            var reader : BufferedReader? = null
            var forecastJsonStr : String? = null

            try {
                val url = URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7")

                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                val inputStream = urlConnection.inputStream
                val buffer = StringBuffer()

                reader = BufferedReader(InputStreamReader(inputStream))

                reader.forEachLine { buffer.append(it).append("\n") }

                forecastJsonStr = buffer.toString()
            } catch (e : IOException) {
                Log.e("ForecastFragment", "Error", e)
            } finally {
                urlConnection?.disconnect()
                try { reader?.close() }
                catch (e: IOException) {
                    Log.e("ForecastFragment", "Error closing stream", e)
                }
            }
            return null;

        }

    }

}
