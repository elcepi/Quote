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
    fun toShareString(): String{
        val START = ""
        val END   = "\n============================================"
        return "$START $this $END"
    }

    companion object {
        @kotlin.jvm.JvmField
        val QUOTE_ID = "quote"
    }
}
