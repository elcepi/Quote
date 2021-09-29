package com.knowyourself.quote.model

data class Quotes(
        val text: String,
        val author: String,
)  {
    override fun toString(): String{
        return if ( ! text.contains(author, ignoreCase = true)) {
            "$text -- $author"
        } else text
    }
}
