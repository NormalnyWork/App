package com.normalnywork.plants.data.api.auth

import androidx.core.content.edit
import splitties.preferences.Preferences

class PrefsTokenStoreImpl : TokenStore, Preferences("com.normalnywork.plants.token-store") {

    private var mAccessToken by stringPref("access_token", "")
    private var mRefreshToken by stringPref("refresh_token", "")

    override fun getAccessToken(): String? {
        return mAccessToken.takeIf { it.isNotEmpty() }
    }

    override fun saveAccessToken(token: String) {
        mAccessToken = token
    }

    override fun getRefreshToken(): String? {
        return mRefreshToken
    }

    override fun saveRefreshToken(token: String) {
        mRefreshToken = token
    }

    override fun logout() {
        prefs.edit { clear() }
    }
}