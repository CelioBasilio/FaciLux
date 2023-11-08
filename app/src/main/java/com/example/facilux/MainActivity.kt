package com.example.facilux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facilux.RecyclerAdapter
import com.example.facilux.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.addButton)
        recyclerView = findViewById(R.id.recyclerView)

        databaseHelper = DatabaseHelper(this)
        recyclerAdapter = RecyclerAdapter(this, databaseHelper)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        addButton.setOnClickListener {
            // Quando o botão de adicionar for clicado, você pode solicitar um nome para o novo cartão
            // e adicionar esse cartão ao RecyclerView
            val cardName = "Novo Comodo" // Substitua pelo nome desejado
            recyclerAdapter.addItem(cardName)
        }
    }
}
