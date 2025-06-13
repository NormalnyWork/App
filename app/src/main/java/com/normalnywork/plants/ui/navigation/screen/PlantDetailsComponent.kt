package com.normalnywork.plants.ui.navigation.screen

import com.normalnywork.plants.domain.entity.Care
import com.normalnywork.plants.domain.entity.CareInterval
import kotlinx.coroutines.flow.StateFlow

interface PlantDetailsComponent {

    val mode: Mode

    val actionAvailable: StateFlow<Boolean>
    val isLoading: StateFlow<Boolean>

    val image: StateFlow<String?>
    val name: StateFlow<String>

    val watering: StateFlow<Care?>
    val trim: StateFlow<Care?>
    val rotation: StateFlow<Care?>
    val fertilization: StateFlow<Care?>
    val cleaning: StateFlow<Care?>
    val transplantation: StateFlow<Care?>

    val presetName: StateFlow<String?>
    val presetsComponent: HandbookListComponent

    fun chooseImage(uri: String)

    fun onNameChanged(newName: String)

    fun toggleWatering()
    fun updateWateringInterval(interval: CareInterval)
    fun updateWateringCount(count: Int)

    fun toggleTrim()
    fun updateTrimInterval(interval: CareInterval)
    fun updateTrimCount(count: Int)

    fun toggleRotation()
    fun updateRotationInterval(interval: CareInterval)
    fun updateRotationCount(count: Int)

    fun toggleFertilization()
    fun updateFertilizationInterval(interval: CareInterval)
    fun updateFertilizationCount(count: Int)

    fun toggleCleaning()
    fun updateCleaningInterval(interval: CareInterval)
    fun updateCleaningCount(count: Int)

    fun toggleTransplantation()
    fun updateTransplantationInterval(interval: CareInterval)
    fun updateTransplantationCount(count: Int)

    fun done()

    fun navigateBack()

    enum class Mode {
        ADD,
        EDIT,
    }
}