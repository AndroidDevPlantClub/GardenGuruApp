package com.example.gardenguru

class Message {

    companion object {
        const val SENT_BY_ME = "me"
        const val SENT_BY_BOT = "Garden Guru"
    }

    var message: String = "Me"
        get() = field
        set(value) {
            field = value
        }

    var sentBy: String = "Garden Guru"
        get() = field
        set(value) {
            field = value
        }

    constructor(message: String, sentBy: String) {
        this.message = message
        this.sentBy = sentBy
    }
}
