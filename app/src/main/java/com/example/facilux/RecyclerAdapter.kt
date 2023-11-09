package com.example.facilux

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.facilux.data.DatabaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerAdapter(
    private val context: Context,
    private val databaseHelper: DatabaseHelper
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var cards: List<Card> = listOf()

    fun setCards(newCards: List<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemState: ImageView = itemView.findViewById(R.id.item_state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]

        with(holder) {
            itemTitle.text = card.title
            itemImage.setImageURI(Uri.parse(card.imageUriString))
            itemState.setImageResource(if (card.lightState == Card.LIGHT_ON) R.drawable.ic_lamp_on else R.drawable.ic_lamp_off)

            itemView.setOnClickListener {
                toggleLightState(card)
            }
        }
    }

    override fun getItemCount(): Int = cards.size

    private fun toggleLightState(card: Card) {
        val newLightState = if (card.lightState == Card.LIGHT_ON) Card.LIGHT_OFF else Card.LIGHT_ON

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                databaseHelper.updateCardLightState(card.copy(lightState = newLightState))
            }
            val cardIndex = cards.indexOf(card)
            if (cardIndex != -1) {
                cards = cards.toMutableList().apply { set(cardIndex, card.copy(lightState = newLightState)) }
                notifyItemChanged(cardIndex)
            }
        }
    }

    fun addNewCard(title: String, imageUri: Uri) {
        val imageUriString = Utils.uriToString(imageUri)
        val newCard = Card(
            title = title,
            imageUriString = imageUriString
        )
        val insertedCardId = databaseHelper.insertCard(newCard)
        newCard.id = insertedCardId

        val mutableList = cards.toMutableList()
        mutableList.add(newCard)

        cards = mutableList.toList()
        notifyDataSetChanged()
    }

}
