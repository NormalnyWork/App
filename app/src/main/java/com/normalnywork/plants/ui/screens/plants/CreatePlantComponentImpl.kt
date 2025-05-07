package com.normalnywork.plants.ui.screens.plants

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.normalnywork.plants.domain.entity.Care
import com.normalnywork.plants.domain.entity.CareInterval
import com.normalnywork.plants.domain.entity.Plant
import com.normalnywork.plants.domain.repository.PlantsRepository
import com.normalnywork.plants.ui.navigation.screen.CreatePlantComponent
import com.normalnywork.plants.utils.componentScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreatePlantComponentImpl(
    componentContext: ComponentContext,
    private val popBackStack: () -> Unit,
) : CreatePlantComponent, ComponentContext by componentContext, KoinComponent {

    private val plantsRepository: PlantsRepository by inject()

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val canCreate = MutableStateFlow(stateHolder.canCreate)
    override val isLoading = MutableStateFlow(false)

    override val image = MutableStateFlow(stateHolder.image)
    override val name = MutableStateFlow(stateHolder.name)

    override val watering = MutableStateFlow(stateHolder.watering)
    override val trim = MutableStateFlow(stateHolder.trim)
    override val rotation = MutableStateFlow(stateHolder.rotation)
    override val fertilization = MutableStateFlow(stateHolder.fertilization)
    override val cleaning = MutableStateFlow(stateHolder.cleaning)
    override val transplantation = MutableStateFlow(stateHolder.transplantation)

    override fun chooseImage(uri: String) {
        image.value = uri
        stateHolder.image = uri

        checkCanCreate()
    }

    override fun onNameChanged(newName: String) {
        name.value = newName
        stateHolder.name = newName

        checkCanCreate()
    }

    override fun toggleWatering() {
        if (stateHolder.watering == null) {
            stateHolder.watering = defaultCare()
            watering.value = stateHolder.watering
        } else {
            stateHolder.watering = null
            watering.value = null
        }

        checkCanCreate()
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

        checkCanCreate()
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

        checkCanCreate()
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

        checkCanCreate()
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

        checkCanCreate()
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

        checkCanCreate()
    }

    override fun updateTransplantationInterval(interval: CareInterval) {
        stateHolder.transplantation = stateHolder.transplantation?.copy(interval = interval)
        transplantation.value = stateHolder.transplantation
    }

    override fun updateTransplantationCount(count: Int) {
        stateHolder.transplantation = stateHolder.transplantation?.copy(count = count)
        transplantation.value = stateHolder.transplantation
    }

    override fun create() {
        componentScope.launch {
            runCatching {
                isLoading.value = true

                plantsRepository.createPlant(
                    Plant(
                        id = -1,
                        name = name.value,
                        image = image.value!!,
                        watering = watering.value,
                        trim = trim.value,
                        rotation = rotation.value,
                        fertilization = fertilization.value,
                        cleaning = cleaning.value,
                        transplantation = transplantation.value,
                    )
                )
            }.onSuccess {
                popBackStack()
            }.onFailure {
                it.printStackTrace()
            }.also {
                isLoading.value = false
            }
        }
    }

    override fun navigateBack() = popBackStack()

    private fun checkCanCreate() {
        stateHolder.canCreate = name.value.isNotBlank()
                && image.value != null
                && (watering.value != null
                    || trim.value != null
                    || rotation.value != null
                    || fertilization.value != null
                    || cleaning.value != null
                    || transplantation.value != null)
        canCreate.value = stateHolder.canCreate
    }

    private fun defaultCare() = Care(
        interval = CareInterval.DAY,
        count = 1
    )

    class StateHolder : InstanceKeeper.Instance {

        var canCreate = false

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