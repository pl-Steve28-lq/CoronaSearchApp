package com.steve28.coronasearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var coronaList: ListView
    private lateinit var search: Button
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var region: EditText
    @Inject lateinit var corona: CoronaClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val comp: CoronaClientComponent = DaggerCoronaClientComponent.create()
        comp.inject(this)

        coronaList = listView
        region = regionText
        search = searchButton
        adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            ArrayList<String>()
        )
        coronaList.adapter = adapter

        search.setOnClickListener {
            val text = region.text.toString()
            if (text == "") Snackbar.make(it, "내용이 비어있습니다.", Snackbar.LENGTH_SHORT).show()
            else {
                var i = 1
                adapter.clear()
                val _disposable = CoronaClient().getAPI().getDatas(region.text.toString())
                    .flatMapIterable { x -> x }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            run {
                                val confirmed = result!!.confirmed.toString()
                                val deaths = result!!.deaths.toString()
                                val recovered = result!!.recovered.toString()
                                adapter.add("${i}일째 | ${confirmed}명 확진, ${deaths}명 사망, ${recovered}명 완치")
                                i += 1
                            }
                        },
                        { error ->
                            run {
                                Log.e("Error...", error.localizedMessage)
                                Snackbar.make(it, "내용을 불러올 수 없습니다.", Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
            }
        }
    }
}