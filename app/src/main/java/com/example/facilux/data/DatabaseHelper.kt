package com.example.facilux.data


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.facilux.Card

// Contrato de tabela para a tabela de cômodos (rooms)
object RoomEntry {
    const val TABLE_NAME = "rooms"
    const val _ID = "_id"
    const val COLUMN_NAME = "name"
    const val COLUMN_IMAGE = "image"
    const val COLUMN_LIGHT_STATE = "light_state"
}

// Contrato de tabela para a tabela de cartões (cards)
object CardEntry {
    const val TABLE_NAME = "cards"
    const val _ID = "_id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_IMAGE = "image"
    const val COLUMN_LIGHT_STATE = "light_state"
    const val COLUMN_ROOM_ID = "room_id"
}

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Nome do banco de dados
    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Cria a tabela de cômodos (rooms)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS ${RoomEntry.TABLE_NAME} (
                ${RoomEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${RoomEntry.COLUMN_NAME} TEXT,
                ${RoomEntry.COLUMN_IMAGE} TEXT,
                ${RoomEntry.COLUMN_LIGHT_STATE} INTEGER
            );
        """)

        // Cria a tabela de cartões (cards)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS ${CardEntry.TABLE_NAME} (
                ${CardEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${CardEntry.COLUMN_TITLE} TEXT,
                ${CardEntry.COLUMN_IMAGE} TEXT,
                ${CardEntry.COLUMN_ROOM_ID} INTEGER
            );
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Se você precisar atualizar o banco de dados, faça isso aqui
    }

    // Insere um novo cartão na tabela de cartões (cards) e retorna o ID do cartão inserido
    fun insertCard(card: Card): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(CardEntry.COLUMN_TITLE, card.title)
        values.put(CardEntry.COLUMN_IMAGE, card.imageUriString.toString()) // Salva a URI da imagem como String
        values.put(CardEntry.COLUMN_LIGHT_STATE, card.lightState)

        val insertedId = db.insert(CardEntry.TABLE_NAME, null, values)
        db.close()

        return insertedId
    }

    // Atualiza o estado da lâmpada de um cartão na tabela de cartões (cards)
    fun updateCardLightState(card: Card) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(CardEntry.COLUMN_ROOM_ID, card.lightState)

        db.update(
            CardEntry.TABLE_NAME,
            values,
            "${CardEntry._ID} = ?",
            arrayOf(card.id.toString())
        )
        db.close()
    }
}
