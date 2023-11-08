package com.example.facilux


data class Card(
    val id: Long, // ID único do cartão no banco de dados (pode ser gerado automaticamente)
    val title: String, // Título do cartão
    val imageResourceId: Int, // Recurso de imagem associado ao cartão
    var lightState: Int // Estado da lâmpada (por exemplo, LIGHT_ON ou LIGHT_OFF)
) {
    companion object {
        const val LIGHT_ON = 1
        const val LIGHT_OFF = 0
    }
}