package com.knowyourself.quote.model

data class Quotes1(
        val text: String,
        val author: String,
)  {
    override fun toString(): String{
        if (! text.contains(author, ignoreCase = true)) {
            "$text -- $author"
        } else text
    }
}
