package com.normalnywork.plants.domain.models

enum class AuthError {
    UsernameEmpty,
    UsernameTaken,
    EmailEmpty,
    EmailTaken,
    InvalidEmail,
    PasswordEmpty,
    PasswordInvalid,
    WeakPassword,
    Unknown,
}