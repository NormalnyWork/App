package com.normalnywork.plants.domain.entity

enum class AuthError {
    UsernameEmpty,
    UsernameTaken,
    EmailEmpty,
    EmailTaken,
    InvalidEmail,
    PasswordEmpty,
    WeakPassword,
    InvalidCredentials,
    Unknown,
}