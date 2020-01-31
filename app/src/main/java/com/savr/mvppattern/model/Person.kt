package com.savr.mvppattern.model

class Person {
    var id: Int = 0
    var name: String? = null
    var email: String? = null
    var phone: String? = null

    constructor()

    constructor(id: Int, name: String, email: String, phone: String) {
        this.id = id
        this.name = name
        this.email = email
        this.phone = phone
    }

}