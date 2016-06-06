package com.sunshine.socratesdiaz.sunshine_kotlin

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ForecastFragment : Fragment() {

    var mArrayAdapter : ArrayAdapter<String>? = null
    val LOG_TAG = ForecastFragment::class.simpleName

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

        fakeData.filter { it == "" }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.forecastfragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when(item?.itemId) {
            R.id.action_refresh -> {
                val task = FetchWeatherTask()
                task.execute("94043")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    class FetchWeatherTask : AsyncTask<String?, Void, List<String>?>() {

        val LOG_TAG = FetchWeatherTask::class.simpleName

        override fun doInBackground(vararg params: String?): List<String>? {
            var urlConnection : HttpURLConnection? = null
            var reader : BufferedReader? = null
            var forecastJsonStr : String? = null
            val forecastList: List<String>?

            val postalCode : String?
            val mode = "json"
            val units = "metric"
            val numDays = 7
            val appId = "528261aeca93cd9665a551b47eb80f41";

            try {
                postalCode = params?.get(0)
                val url = URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7")

                val builder = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily").buildUpon()
                    .appendQueryParameter("q", postalCode)
                    .appendQueryParameter("mode", mode)
                    .appendQueryParameter("units", units)
                    .appendQueryParameter("cnt", numDays.toString())
                    .appendQueryParameter("APPID", appId)

                urlConnection = URL(builder.build().toString()).openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                val inputStream = urlConnection.inputStream
                val buffer = StringBuffer()

                reader = BufferedReader(InputStreamReader(inputStream))

                reader.forEachLine { buffer.append(it).append("\n") }

                forecastJsonStr = buffer.toString()

                try {
                    forecastList = getWeatherDataFromJson(forecastJsonStr, numDays)
                }
                catch (ex: JSONException) {
                    Log.e(LOG_TAG, "JSON Error", ex)
                }
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

        private fun getWeatherDataFromJson(forecastJsonStr : String, numDays: Int) : List<String>? {
            // TODO: getWeatherMethod
            return null
        }

    }

}
