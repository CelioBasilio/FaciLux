package com.example.facilux

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facilux.data.DatabaseHelper

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var dialogContainer: View
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerAdapter = RecyclerAdapter(this, databaseHelper)
        recyclerView.adapter = recyclerAdapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            showAddCard()
        }

        dialogContainer = findViewById(R.id.dialogContainer)

        val selectImageButton: Button = findViewById(R.id.selectImageButton)
        val imagePreviewDialog: ImageView = findViewById(R.id.imagePreviewDialog)
        val confirmButton: Button = findViewById(R.id.confirmButton)

        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        confirmButton.setOnClickListener {
            val editTextName: EditText = findViewById(R.id.editTextName)
            handleConfirmButtonClick(editTextName.text.toString())
        }
    }

    private fun showAddCard() {
        // Mostra o FrameLayout
        dialogContainer.visibility = View.VISIBLE
    }

    private fun handleConfirmButtonClick(roomName: String) {
        if (imageUri != null) {
            recyclerAdapter.addNewCard(roomName, imageUri!!)
            // Lógica adicional após a confirmação, se necessário
        } else {
            Toast.makeText(this, "Selecione uma imagem", Toast.LENGTH_SHORT).show()
        }

        // Oculta o FrameLayout
        dialogContainer.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            val imagePreviewDialog: ImageView = findViewById(R.id.imagePreviewDialog)
            imagePreviewDialog.visibility = View.VISIBLE
            imagePreviewDialog.setImageURI(imageUri)
        }
    }
}

