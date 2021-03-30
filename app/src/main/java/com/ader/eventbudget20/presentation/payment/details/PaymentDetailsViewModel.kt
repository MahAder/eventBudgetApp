package com.ader.eventbudget20.presentation.payment.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ader.eventbudget20.domain.model.Payment
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.domain.repository.ParticipantRepository
import com.ader.eventbudget20.domain.repository.PaymentRepository
import com.ader.eventbudget20.presentation.base.BaseViewModel
import com.ader.eventbudget20.presentation.model.AddParticipantAdapterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentDetailsViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val participantRepository: ParticipantRepository,
    private val state: SavedStateHandle
): BaseViewModel() {
    private val paymentId = state.get<Int>("paymentId") ?: -1
    private var eventId: Int = 0

    private lateinit var payment: Payment
    private val allParticipantsList = ArrayList<User>()

    val paymentLiveData = MutableLiveData<Payment>()
    val payerListLiveData = MutableLiveData<List<AddParticipantAdapterModel>>()
    val paymentParticipantListLiveData = MutableLiveData<List<AddParticipantAdapterModel>>()

    private val payerList = ArrayList<User>()
    private val paymentParticipantList = ArrayList<User>()

    var paymentDetailsNavigation: PaymentDetailsNavigation? = null

    init {
        viewModelScope.launch(Dispatchers.IO){
            payment = paymentRepository.getPaymentById(paymentId)
            eventId = payment.eventId
            paymentLiveData.postValue(payment)

            allParticipantsList.addAll(participantRepository.getAllParticipants(eventId))
            payerList.addAll(payment.payersList)
            paymentParticipantList.addAll(payment.paymentParticipants)

            payerListLiveData.postValue(allParticipantsList.map {
                AddParticipantAdapterModel(it, payerList.contains(it))
            })
            paymentParticipantListLiveData.postValue(allParticipantsList.map {
                AddParticipantAdapterModel(it, paymentParticipantList.contains(it))
            })
        }
    }

    fun payerListChange(position: Int, isChecked: Boolean){
        allParticipantsList?.let {
            val user = it[position]
            if(isChecked) addPayer(user) else removePayer(user)
        }
    }

    fun payParticipantsListChane(position: Int, isChecked: Boolean){
        allParticipantsList?.let {
            val user = it[position]
            if(isChecked) addPayParticipant(user) else removePayParticipantList(user)
        }
    }

    fun deletePayment(){
        viewModelScope.launch(Dispatchers.IO){
            paymentRepository.deletePayment(payment)
            paymentDetailsNavigation?.back()
        }
    }

    fun updateEvent(description: String, value: Float){
        viewModelScope.launch(Dispatchers.IO){
            paymentRepository.deletePayment(payment)
            paymentRepository.createPayment(
                eventId,
                value,
                payerList,
                description,
                paymentParticipantList
            )

            paymentDetailsNavigation?.back()
        }
    }

    private fun addPayer(user: User){
        payerList.add(user)
    }

    private fun removePayer(user: User){
        payerList.remove(user)
    }

    private fun addPayParticipant(user: User){
        paymentParticipantList.add(user)
    }

    private fun removePayParticipantList(user: User){
        paymentParticipantList.remove(user)
    }
}