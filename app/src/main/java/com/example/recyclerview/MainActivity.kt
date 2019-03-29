package com.example.recyclerview

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerViewをレイアウトから探す
        val recyclerView = findViewById<RecyclerView>(R.id.timeZoneList)

        // タイムゾーンリスト用のアダプター
        val adapter = SampleAdapter(this) {timeZone ->
            // タップされた位置にあるタイムゾーンをトースト表示する
            Toast.makeText(this, timeZone.displayName, Toast.LENGTH_SHORT).show()
        }

        // RecyclerViewにアダプターをセットする
        recyclerView.adapter = adapter

        // 縦に直線的に表示するレイアウトマネージャーをセットする
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false)
    }
}


//RecyclerView用のサンプルアダプタ
class SampleAdapter(context: Context,
                    private val onItemClicked: (TimeZone) -> Unit)
    : RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {
    // レイアウトからビューを生成するInflater
    private val inflater = LayoutInflater.from(context)
    //リサイクラービューで表示するタイムゾーンのリスト
    private val timeZones = TimeZone.getAvailableIDs().map {
        id -> TimeZone.getTimeZone(id)}


    override fun getItemCount() = timeZones.size

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        //位置に応じたタイムゾーンを得る
        val timeZone = timeZones[position]
        //表示内容を更新する
        holder.timeZone.text = timeZone.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SampleViewHolder{
        // Viewを生成する
        val view = inflater.inflate(R.layout.list_time_zone_row, parent, false)


        // ViewHolderを作る
        val viewHolder = SampleViewHolder(view)

        // viewをタップした時の処理
        view.setOnClickListener {
            //　アダプターの上の位置を得る
            val position = viewHolder.adapterPosition
            //　位置をもとに、タイムゾーンを得る
            val timeZone = timeZones[position]
            // コールバックを呼び出す
            onItemClicked(timeZone)
        }

        return viewHolder
    }

    //Viewへの参照を持っておくViewHolder
    class SampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeZone = view.findViewById<TextView>(R.id.timeZone)
    }
}
