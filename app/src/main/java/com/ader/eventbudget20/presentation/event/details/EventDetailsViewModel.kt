package com.ader.eventbudget20.presentation.event.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.ader.eventbudget20.domain.model.Event
import com.ader.eventbudget20.domain.model.Payment
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.domain.repository.EventRepository
import com.ader.eventbudget20.domain.repository.ParticipantRepository
import com.ader.eventbudget20.domain.repository.PaymentRepository
import com.ader.eventbudget20.presentation.base.BaseViewModel
import com.ader.eventbudget20.presentation.model.ParticipantUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    participantRepository: ParticipantRepository,
    private val paymentRepository: PaymentRepository,
    private val eventRepository: EventRepository,
    state: SavedStateHandle
) : BaseViewModel() {
    private val eventId = state.get<Int>("eventId") ?: -1
    private val allParticipantFlow = participantRepository.getAllParticipantsLive(eventId)
    private val allPaymentsFlow = paymentRepository.getAllEventPaymentsLive(eventId)
    val participantsLiveData = MutableLiveData<List<ParticipantUIModel>>()
    val paymentsLiveData = MutableLiveData<List<Payment>>()
    val eventLiveData = MutableLiveData<Event>()
    val eventTotalValueLiveData = MutableLiveData<Float>()
    private lateinit var event: Event

    var eventDetailsNavigation: EventDetailsNavigation? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            event = eventRepository.getEventById(eventId)
            eventLiveData.postValue(event)
            val data = allPaymentsFlow.flatMapLatest {payments->
                paymentsLiveData.postValue(payments)
                eventTotalValueLiveData.postValue(calculateEventTotal(payments))
                allParticipantFlow.map {users ->
                    if(users == null) return@map
                    if(users.isEmpty()){
                        eventDetailsNavigation?.navigateToAddParticipants()
                    } else {
                        participantsLiveData.postValue(
                            users.map { user ->
                                ParticipantUIModel(user, calculateUserTotal(user))
                            }
                        )
                    }
                }
            }

            data.collect {}
        }
    }

    fun deleteEvent(){
        viewModelScope.launch(Dispatchers.IO){
            eventRepository.deleteEvent(event)
            eventDetailsNavigation?.back()
        }
    }

    private fun calculateUserTotal(user: User): Float {
        val totalPayed = paymentRepository.getAllUserEventPayment(eventId, user.id).map {
            it.value / it.payersList.size
        }.sum()

        val totalNeedToPay = paymentRepository.getAllUserEventPaymentParticipation(eventId, user.id).map {
            it.value / it.paymentParticipants.size
        }.sum()

        return totalNeedToPay - totalPayed
    }

    private fun calculateEventTotal(payments: List<Payment>): Float{
        return payments.map {
            it.value
        }.sum()
    }
}