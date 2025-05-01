package com.normalnywork.plants.utils

import splitties.preferences.Preferences

object Prefs : Preferences("com.normalnywork.plants.prefs") {

    var onboardingShown by boolPref("onboarding_shown", false)
}