package com.example.facilux

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

object RoomEntry {
    const val TABLE_NAME = "rooms"
    const val _ID = "_id"
    const val COLUMN_NAME = "name"
    const val COLUMN_IMAGE = "image"
    const val COLUMN_LIGHT_STATE = "light_state"
}

object CardEntry {
    const val TABLE_NAME = "cards"
    const val _ID = "_id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_IMAGE = "image"
    const val COLUMN_ROOM_ID = "room_id"
}


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Nome do banco de dados
    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1
    }

    // Tabela de cômodos
    private val CREATE_TABLE_ROOMS = """
        CREATE TABLE IF NOT EXISTS ${RoomEntry.TABLE_NAME} (
            ${RoomEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${RoomEntry.COLUMN_NAME} TEXT,
            ${RoomEntry.COLUMN_IMAGE} TEXT,
            ${RoomEntry.COLUMN_LIGHT_STATE} INTEGER
        );
    """

    // Tabela de cartões
    private val CREATE_TABLE_CARDS = """
        CREATE TABLE IF NOT EXISTS ${CardEntry.TABLE_NAME} (
            ${CardEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${CardEntry.COLUMN_TITLE} TEXT,
            ${CardEntry.COLUMN_IMAGE} TEXT,
            ${CardEntry.COLUMN_ROOM_ID} INTEGER
        );
    """

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_ROOMS)
        db.execSQL(CREATE_TABLE_CARDS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Se você precisar atualizar o banco de dados, faça isso aqui
    }

    fun insertCard(card: Card) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CardEntry.COLUMN_TITLE, card.title)
        values.put(CardEntry.COLUMN_IMAGE, card.imageResourceId)
        values.put(CardEntry.COLUMN_ROOM_ID, card.lightState)

        db.insert(CardEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun updateCardLightState(card: Card) {
        val db = this.writableDatabase
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
