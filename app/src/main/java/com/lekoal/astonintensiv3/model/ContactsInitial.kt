package com.lekoal.astonintensiv3.model

object ContactsInitial {
    fun get(): List<ContactInfo> {
        val result = mutableListOf<ContactInfo>()
        for (i in 1..100) {
            result.add(
                ContactInfo(
                    id = i,
                    name = "Name $i",
                    surname = "Surname $i",
                    phone = (71111111111 + i).toString()
                )
            )
        }
        return result
    }
}