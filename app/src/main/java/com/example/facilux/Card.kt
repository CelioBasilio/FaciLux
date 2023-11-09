package com.example.facilux


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val title: String,
    val imageUriString: String? = null, // Pode ser uma String em vez de Uri
    var lightState: Int = LIGHT_OFF
) {
    companion object {
        const val LIGHT_ON = 1
        const val LIGHT_OFF = 0
    }
}