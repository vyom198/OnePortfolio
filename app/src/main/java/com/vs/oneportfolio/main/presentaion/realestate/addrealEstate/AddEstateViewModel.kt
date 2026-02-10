package com.vs.oneportfolio.main.presentaion.realestate.addrealEstate

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vs.oneportfolio.app.navigation.AppRoute
import com.vs.oneportfolio.core.AlarmManager.AlarmScheduler
import com.vs.oneportfolio.core.database.realestate.RealEstateDao
import com.vs.oneportfolio.core.database.realestate.RealEstateEntity
import com.vs.oneportfolio.core.database.realestate.history.SoldEstateDao
import com.vs.oneportfolio.core.database.realestate.history.SoldEstateEntity
import com.vs.oneportfolio.main.presentaion.realestate.addrealEstate.AddEstateEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.io.File
import java.io.FileOutputStream

class AddEstateViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val realEstateDao: RealEstateDao,
    private val context: Context,
    private val alarmScheduler: AlarmScheduler,
    private val  soldEstateDao: SoldEstateDao,
    private val scope : CoroutineScope
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val route = savedStateHandle.toRoute<AppRoute.AddEstate>()
    private val screenTitle = route.title
    private val id = route.id ?: -1

    private val eventChannel = Channel<AddEstateEvent>()
    val events = eventChannel.receiveAsFlow()



    private val _state = MutableStateFlow(AddEstateState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                 loadData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AddEstateState()
        )

    private fun saveData(){
        viewModelScope.launch {
            val item = RealEstateEntity(
                propertyName = _state.value.propertyName,
                address = _state.value.address,
                currentMarketValue =  if(_state.value.purchasePrice.isEmpty())0.0 else _state.value.purchasePrice.toDouble(),
                propertyType = _state.value.propertyType,
                properImg = _state.value.properImg,
                purchasePrice = if(_state.value.purchasePrice.isEmpty())0.0 else _state.value.purchasePrice.toDouble(),
                yieldRate = if(_state.value.yieldRate.isEmpty())0.0 else _state.value.yieldRate.toDouble(),
                monthlyRent = if(_state.value.monthlyRent.isEmpty())0.0 else  _state.value.monthlyRent.toDouble(),
                isRented = _state.value.isRented,
                hasMortgage = _state.value.hasMortgage,
                mortgageBalance = if(_state.value.mortgageBalance.isEmpty())0.0 else _state.value.mortgageBalance.toDouble(),
                mortgagePayment = if(_state.value.mortgagePayment.isEmpty())0.0 else _state.value.mortgagePayment.toDouble(),
                taxDueDate = _state.value.taxDueDate ?: 0L ,
                purchaseDate = _state.value.purchaseDate ?: 0L ,
            )
            if(screenTitle == _state.value.propertyName){
                val item = realEstateDao.getRealEstateById(id)
               val newitem = realEstateDao.getRealEstateById(id)?.copy(
                   propertyName = _state.value.propertyName,
                   address = _state.value.address,
                   propertyType = _state.value.propertyType,
                   properImg = _state.value.properImg,
                   purchasePrice = if(_state.value.purchasePrice.isEmpty())0.0 else _state.value.purchasePrice.toDouble(),
                   yieldRate = if(_state.value.yieldRate.isEmpty())0.0 else _state.value.yieldRate.toDouble(),
                   monthlyRent = if(_state.value.monthlyRent.isEmpty())0.0 else  _state.value.monthlyRent.toDouble(),
                   isRented = _state.value.isRented,
                   hasMortgage = _state.value.hasMortgage,
                   mortgageBalance = if(_state.value.mortgageBalance.isEmpty())0.0 else _state.value.mortgageBalance.toDouble(),
                   mortgagePayment = if(_state.value.mortgagePayment.isEmpty())0.0 else _state.value.mortgagePayment.toDouble(),
                   taxDueDate = _state.value.taxDueDate ?: 0L ,
                   purchaseDate = _state.value.purchaseDate ?: 0L ,
               )
                if(item != null){
                    alarmScheduler.cancelYield(item)
                    newitem?.let { realEstateDao.insertRealEstate(it) }
                    newitem?.let { alarmScheduler.scheduleYield(it) }
                }
            }else{
                realEstateDao.insertRealEstate(
                    item
                )
                val insertedItem = realEstateDao.getLastItem()
                insertedItem?.let { alarmScheduler.scheduleYield(it) }
            }

            eventChannel.send(AddEstateEvent.onAddEvent)
        }
    }
    private fun loadData (){
        viewModelScope.launch {
            if(id != -1){
                val item = realEstateDao.getRealEstateById(id)!!
                 _state.update {
                     it.copy(
                         propertyName = item.propertyName,
                         address = item.address?:"",
                         propertyType = item.propertyType,
                         properImg = item.properImg,
                         purchasePrice = item.purchasePrice.toString(),
                         yieldRate = item.yieldRate.toString(),
                         monthlyRent = item.monthlyRent.toString(),
                         isRented = item.isRented,
                         hasMortgage = item.hasMortgage,
                         mortgageBalance = item.mortgageBalance.toString(),
                         mortgagePayment = item.mortgagePayment.toString(),
                         taxDueDate = item.taxDueDate,
                         purchaseDate = item.purchaseDate
                     )
                 }
            }
            _state.update {
                it.copy(
                    screenTitle = screenTitle
                )
            }
        }

    }
    fun onAction(action: AddEstateAction) {
        when (action) {
            is AddEstateAction.OnFieldUpdate -> {
                _state.update { currentState ->
                    when (action.field) {
                        EstateField.NAME -> currentState.copy(propertyName = action.value)
                        EstateField.ADDRESS -> currentState.copy(address = action.value)
                        EstateField.TYPE -> currentState.copy(propertyType = action.value)
                        EstateField.PRICE -> currentState.copy(purchasePrice = action.value)
                        EstateField.YIELD -> currentState.copy(yieldRate = action.value)
                        EstateField.RENT -> currentState.copy(monthlyRent = action.value)
                        EstateField.MORTGAGE_BAL -> currentState.copy(mortgageBalance = action.value)
                        EstateField.MORTGAGE_PAY -> currentState.copy(mortgagePayment = action.value)
                    }
                }
            }
            AddEstateAction.OnSave -> saveData()
            is AddEstateAction.OnFieldCancel -> {
                _state.update { currentState ->
                    when (action.field) {
                        EstateField.NAME -> currentState.copy(propertyName = "")
                        EstateField.ADDRESS -> currentState.copy(address =  "")
                        EstateField.TYPE -> currentState.copy(propertyType =  "")
                        EstateField.PRICE -> currentState.copy(purchasePrice =  "")
                        EstateField.YIELD -> currentState.copy(yieldRate =  "")
                        EstateField.RENT -> currentState.copy(monthlyRent = "")
                        EstateField.MORTGAGE_BAL -> currentState.copy(mortgageBalance =  "")
                        EstateField.MORTGAGE_PAY -> currentState.copy(mortgagePayment =  "")
                    }
                }
            }

            is AddEstateAction.onMortgageChange -> {
                _state.update {
                    it.copy(
                        hasMortgage = !action.value
                    )
                }
            }
            is AddEstateAction.onRentedChange -> {
                _state.update {
                    it.copy(
                        isRented = !action.value
                    )
                }
            }

            is AddEstateAction.onPurchaseDateChange -> {
                _state.update {
                    it.copy(
                        purchaseDate = action.value
                    )
                }
            }
            is AddEstateAction.onTaxDateChange -> {
                _state.update {
                    it.copy(
                        taxDueDate = action.value
                    )
                }

            }

            AddEstateAction.onChangeAvtar -> {
                viewModelScope.launch {
                    eventChannel.send(AddEstateEvent.LaunchPicker)
                }
            }
            is AddEstateAction.onUriGet -> {
                viewModelScope.launch {
                    val oldPath = _state.value.properImg
                    deleteOldFile(oldPath)
                    val path = saveUriToFile(context, action.uri)
                    _state.update {
                        it.copy(
                            properImg = path
                        )

                    }
                }

            }

            AddEstateAction.OnCancelDelete -> {
                _state.update {
                    it.copy(
                        isDeleting = false
                    )
                }
            }
            AddEstateAction.OnDelete -> {
                _state.update {
                    it.copy(
                        isDeleting = true
                    )
                }
            }
            AddEstateAction.OnDeleteConfirm -> {
                viewModelScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main){
                        _state.update {
                            it.copy(
                                isDeleting = false
                            )

                        }
                    }

                    val item = realEstateDao.getRealEstateById(id)
                    deleteAlarmsAsynchrously(item!!)
                    realEstateDao.deleteRealEstateById(item.id)

                    withContext(Dispatchers.Main){

                        eventChannel.send(AddEstateEvent.onDelete)
                    }


                }
            }
            AddEstateAction.OnSold -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val item = realEstateDao.getRealEstateById(id)
                    val soldItem = SoldEstateEntity(
                        propertyName = item!!.propertyName,
                        address = item.address ?: "",
                        currentMarketValue = item.currentMarketValue,
                        properImg = item.properImg,
                        purchasePrice = item.purchasePrice
                    )
                    deleteAlarmsAsynchrously(item)
                    soldEstateDao.insertSoldEstate(soldItem)
                    realEstateDao.deleteRealEstateById(item.id)

                    withContext(Dispatchers.Main){
                        eventChannel.send(AddEstateEvent.OnSold)
                    }

                }
            }
        }
    }
    private fun deleteAlarmsAsynchrously(item: RealEstateEntity) {
        scope.launch(Dispatchers.Default) {
            try {
                // Launch all alarm cancellations in parallel
                val jobs = mutableListOf<Job>()

                jobs.add(launch { alarmScheduler.cancelYield(item) })

                if (item.mortgageReminder) {
                    jobs.add(launch { alarmScheduler.cancelRepeatingMortgage(item) })
                }
                if (item.rentReminder) {
                    jobs.add(launch { alarmScheduler.cancelRepeatingRent(item) })
                }
                if (item.taxReminder) {
                    jobs.add(launch { alarmScheduler.cancelRepeatingTax(item) })
                }

                // Wait for all alarm cancellations to complete
                jobs.joinAll()
            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }
    private fun deleteOldFile(path: String?) {
        if (path.isNullOrEmpty()) return
        try {
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun saveUriToFile(context: Context, uri: Uri): String? {
        val contentResolver = context.contentResolver
        // Create a unique filename using a timestamp or UUID
        val fileName = "estate_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            file.absolutePath // This is what you store in the DB
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

