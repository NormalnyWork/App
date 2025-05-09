package com.normalnywork.plants.domain.entity

data class Task(
    val id: Int,
    val careType: CareType,
    val scheduledAt: Long,
    val status: Status,
    val plantName: String,
    val plantImage: String,
) {

    enum class Status {
        PENDING,
        COMPLETED,
        OVERDUE,
    }
}
