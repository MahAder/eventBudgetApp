package com.ader.eventbudget20.presentation.payment.createpayment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ader.eventbudget20.data.dao.ParticipantDao
import com.ader.eventbudget20.data.dao.PaymentDao
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.domain.repository.ParticipantRepository
import com.ader.eventbudget20.domain.repository.PaymentRepository
import com.ader.eventbudget20.domain.utils.DBMapUtils.toModel
import com.ader.eventbudget20.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val participantRepository: ParticipantRepository,
    private val state: SavedStateHandle
) : BaseViewModel() {
    private val eventId = state.get<Int>("eventId") ?: -1
    private var allParticipantList: List<User>? = null
    val participantLiveData = MutableLiveData<List<User>>()
    private val payersList = ArrayList<User>()
    private val paymentParticipantList = ArrayList<User>()

    var createPaymentNavigation: CreatePaymentNavigation? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allParticipantList = participantRepository.getAllParticipants(eventId)
            participantLiveData.postValue(allParticipantList)
            allParticipantList?.let {
                paymentParticipantList.addAll(it)
            }
        }
    }

    fun createPayment(value: Float, description: String){
        if(value <= 0){
            createPaymentNavigation?.showIncorrectValueError()
            return
        }
        if(payersList.isEmpty()){
            createPaymentNavigation?.showEmptyPayerError()
            return
        }
        if(paymentParticipantList.isEmpty()){
            createPaymentNavigation?.showEmptyParticipantsError()
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            paymentRepository.createPayment(
                eventId,
                value,
                payersList,
                description,
                paymentParticipantList
            )

            createPaymentNavigation?.close()
        }
    }

    fun payerListChange(position: Int, isChecked: Boolean){
        allParticipantList?.let {
            val user = it[position]
            if(isChecked) addPayer(user) else removePayer(user)
        }
    }

    fun payParticipantsListChane(position: Int, isChecked: Boolean){
        allParticipantList?.let {
            val user = it[position]
            if(isChecked) addPayParticipant(user) else removePayParticipantList(user)
        }
    }

    private fun addPayer(user: User){
        payersList.add(user)
    }

    private fun removePayer(user: User){
        payersList.remove(user)
    }

    private fun addPayParticipant(user: User){
        paymentParticipantList.add(user)
    }

    private fun removePayParticipantList(user: User){
        paymentParticipantList.remove(user)
    }
}