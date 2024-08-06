package com.tenutz.kiosksim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object SocialProfile: KotprefModel() {
    var accessToken by stringPref()
    var name by nullableStringPref()
    var email by nullableStringPref()
    var profileImageUrl by nullableStringPref()
    var type by stringPref()

    fun save(
        accessToken: String,
        name: String?,
        email: String?,
        profileImageUrl: String?,
        type: String,
    ) {
        this.accessToken = accessToken
        this.name = name
        this.email = email
        this.profileImageUrl = profileImageUrl
        this.type = type
    }
}