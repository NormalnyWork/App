package com.normalnywork.plants.ui.screens.plants

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.normalnywork.plants.domain.entity.Care
import com.normalnywork.plants.domain.entity.CareInterval
import com.normalnywork.plants.domain.entity.Plant
import com.normalnywork.plants.domain.repository.PlantsRepository
import com.normalnywork.plants.ui.navigation.screen.PlantDetailsComponent
import com.normalnywork.plants.ui.navigation.screen.PlantDetailsComponent.Mode
import com.normalnywork.plants.utils.componentScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlantDetailsComponentImpl(
    componentContext: ComponentContext,
    private val editPlant: Plant?,
    private val popBackStack: () -> Unit,
) : PlantDetailsComponent, ComponentContext by componentContext, KoinComponent {

    private val plantsRepository: PlantsRepository by inject()

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val mode = if (editPlant == null) Mode.ADD else Mode.EDIT

    override val actionAvailable = MutableStateFlow(stateHolder.actionAvailable)
    override val isLoading = MutableStateFlow(false)

    override val image = MutableStateFlow(stateHolder.image)
    override val name = MutableStateFlow(stateHolder.name)

    override val watering = MutableStateFlow(stateHolder.watering)
    override val trim = MutableStateFlow(stateHolder.trim)
    override val rotation = MutableStateFlow(stateHolder.rotation)
    override val fertilization = MutableStateFlow(stateHolder.fertilization)
    override val cleaning = MutableStateFlow(stateHolder.cleaning)
    override val transplantation = MutableStateFlow(stateHolder.transplantation)

    init { if (editPlant != null) presetValuesFrom(editPlant) }

    override fun chooseImage(uri: String) {
        image.value = uri
        stateHolder.image = uri

        checkActionAvailable()
    }

    override fun onNameChanged(newName: String) {
        name.value = newName
        stateHolder.name = newName

        checkActionAvailable()
    }

    override fun toggleWatering() {
        if (stateHolder.watering == null) {
            stateHolder.watering = defaultCare()
            watering.value = stateHolder.watering
        } else {
            stateHolder.watering = null
            watering.value = null
        }

        checkActionAvailable()
    }

    override fun updateWateringInterval(interval: CareInterval) {
        stateHolder.watering = stateHolder.watering?.copy(interval = interval)
        watering.value = stateHolder.watering
    }

    override fun updateWateringCount(count: Int) {
        stateHolder.watering = stateHolder.watering?.copy(count = count)
        watering.value = stateHolder.watering
    }

    override fun toggleTrim() {
        if (stateHolder.trim == null) {
            stateHolder.trim = defaultCare()
            trim.value = stateHolder.trim
        } else {
            stateHolder.trim = null
            trim.value = null
        }

        checkActionAvailable()
    }

    override fun updateTrimInterval(interval: CareInterval) {
        stateHolder.trim = stateHolder.trim?.copy(interval = interval)
        trim.value = stateHolder.trim
    }

    override fun updateTrimCount(count: Int) {
        stateHolder.trim = stateHolder.trim?.copy(count = count)
        trim.value = stateHolder.trim
    }

    override fun toggleRotation() {
        if (stateHolder.rotation == null) {
            stateHolder.rotation = defaultCare()
            rotation.value = stateHolder.rotation
        } else {
            stateHolder.rotation = null
            rotation.value = null
        }

        checkActionAvailable()
    }

    override fun updateRotationInterval(interval: CareInterval) {
        stateHolder.rotation = stateHolder.rotation?.copy(interval = interval)
        rotation.value = stateHolder.rotation
    }

    override fun updateRotationCount(count: Int) {
        stateHolder.rotation = stateHolder.rotation?.copy(count = count)
        rotation.value = stateHolder.rotation
    }

    override fun toggleFertilization() {
        if (stateHolder.fertilization == null) {
            stateHolder.fertilization = defaultCare()
            fertilization.value = stateHolder.fertilization
        } else {
            stateHolder.fertilization = null
            fertilization.value = null
        }

        checkActionAvailable()
    }

    override fun updateFertilizationInterval(interval: CareInterval) {
        stateHolder.fertilization = stateHolder.fertilization?.copy(interval = interval)
        fertilization.value = stateHolder.fertilization
    }

    override fun updateFertilizationCount(count: Int) {
        stateHolder.fertilization = stateHolder.fertilization?.copy(count = count)
        fertilization.value = stateHolder.fertilization
    }

    override fun toggleCleaning() {
        if (stateHolder.cleaning == null) {
            stateHolder.cleaning = defaultCare()
            cleaning.value = stateHolder.cleaning
        } else {
            stateHolder.cleaning = null
            cleaning.value = null
        }

        checkActionAvailable()
    }

    override fun updateCleaningInterval(interval: CareInterval) {
        stateHolder.cleaning = stateHolder.cleaning?.copy(interval = interval)
        cleaning.value = stateHolder.cleaning
    }

    override fun updateCleaningCount(count: Int) {
        stateHolder.cleaning = stateHolder.cleaning?.copy(count = count)
        cleaning.value = stateHolder.cleaning
    }

    override fun toggleTransplantation() {
        if (stateHolder.transplantation == null) {
            stateHolder.transplantation = defaultCare()
            transplantation.value = stateHolder.transplantation
        } else {
            stateHolder.transplantation = null
            transplantation.value = null
        }

        checkActionAvailable()
    }

    override fun updateTransplantationInterval(interval: CareInterval) {
        stateHolder.transplantation = stateHolder.transplantation?.copy(interval = interval)
        transplantation.value = stateHolder.transplantation
    }

    override fun updateTransplantationCount(count: Int) {
        stateHolder.transplantation = stateHolder.transplantation?.copy(count = count)
        transplantation.value = stateHolder.transplantation
    }

    override fun done() {
        componentScope.launch {
            runCatching {
                isLoading.value = true

                val plant = Plant(
                    id = editPlant?.id ?: -1,
                    name = name.value,
                    image = image.value!!,
                    watering = watering.value,
                    trim = trim.value,
                    rotation = rotation.value,
                    fertilization = fertilization.value,
                    cleaning = cleaning.value,
                    transplantation = transplantation.value,
                )

                when (mode) {
                    Mode.ADD -> plantsRepository.createPlant(plant)
                    Mode.EDIT -> plantsRepository.editPlant(plant)
                }
            }.onSuccess {
                popBackStack()
            }.onFailure {
                Log.e("PlantDetailsComponentImpl", "Failed to add/edit plant:", it)
            }.also {
                isLoading.value = false
            }
        }
    }

    override fun navigateBack() = popBackStack()

    private fun checkActionAvailable() {
        stateHolder.actionAvailable = name.value.isNotBlank()
                && image.value != null
                && (watering.value != null
                    || trim.value != null
                    || rotation.value != null
                    || fertilization.value != null
                    || cleaning.value != null
                    || transplantation.value != null)
        actionAvailable.value = stateHolder.actionAvailable
    }

    private fun defaultCare() = Care(
        interval = CareInterval.DAY,
        count = 1
    )

    private fun presetValuesFrom(editPlant: Plant) {
        stateHolder.image = editPlant.image
        stateHolder.name = editPlant.name
        stateHolder.watering = editPlant.watering
        stateHolder.trim = editPlant.trim
        stateHolder.rotation = editPlant.rotation
        stateHolder.fertilization = editPlant.fertilization
        stateHolder.cleaning = editPlant.cleaning
        stateHolder.transplantation = editPlant.transplantation

        image.value = editPlant.image
        name.value = editPlant.name
        watering.value = editPlant.watering
        trim.value = editPlant.trim
        rotation.value = editPlant.rotation
        fertilization.value = editPlant.fertilization
        cleaning.value = editPlant.cleaning
        transplantation.value = editPlant.transplantation

        checkActionAvailable()
    }

    class StateHolder : InstanceKeeper.Instance {

        var actionAvailable = false

        var image: String? = null
        var name = ""

        var watering: Care? = null
        var trim: Care? = null
        var rotation: Care? = null
        var fertilization: Care? = null
        var cleaning: Care? = null
        var transplantation: Care? = null
    }
}