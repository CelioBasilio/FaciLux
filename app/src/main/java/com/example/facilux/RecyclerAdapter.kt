package com.example.facilux


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.facilux.Card.Companion.LIGHT_OFF
import com.example.facilux.Card.Companion.LIGHT_ON

class RecyclerAdapter(private val context: Context, private val databaseHelper: DatabaseHelper) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val cards: MutableList<Card> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]

        holder.itemTitle.text = card.title
        holder.itemImage.setImageResource(card.imageResourceId)

        if (card.lightState == LIGHT_ON) {
            holder.itemState.setImageResource(R.drawable.ic_lamp_on)
        } else {
            holder.itemState.setImageResource(R.drawable.ic_lamp_off)
        }

        holder.itemView.setOnClickListener {
            toggleLightState(card)
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    private fun createNewCard(name: String): Card {
        // Aqui, você pode criar um novo objeto Card com os dados apropriados
        // Por exemplo, defina o ID, título, imagem e estado da lâmpada
        val newCard = Card(
            id = 0, // Pode gerar um ID único ou definir como 0 se for autoincrement no banco de dados
            title = name,
            imageResourceId = R.drawable.ic_sala, // Defina o recurso de imagem padrão
            lightState = Card.LIGHT_OFF // Defina o estado inicial da lâmpada
        )
        return newCard
    }

    fun addItem(name: String) {
        val newCard = createNewCard(name)
        databaseHelper.insertCard(newCard)
        cards.add(newCard)
        notifyDataSetChanged()
    }

    private fun toggleLightState(card: Card) {
        if (card.lightState == LIGHT_ON) {
            card.lightState = LIGHT_OFF
        } else {
            card.lightState = LIGHT_ON
        }
        databaseHelper.updateCardLightState(card)
        notifyDataSetChanged()
    }

    fun setCards(newCards: List<Card>) {
        cards.clear()
        cards.addAll(newCards)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemState: ImageView = itemView.findViewById(R.id.item_state)
    }
}

