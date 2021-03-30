package com.ader.eventbudget20.presentation.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ader.eventbudget20.domain.model.Event
import com.ader.eventbudget20.domain.repository.EventRepository
import com.ader.eventbudget20.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventRepository: EventRepository): BaseViewModel()  {
    private val allEventsFlowList = eventRepository.getAllEventsLive()
    val allEventsLiveData = MutableLiveData<List<Event>>()

    var eventNavigation: EventNavigation? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allEventsFlowList.collect {
                allEventsLiveData.postValue(it)
            }
        }
    }

    fun createEvent(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            val id = eventRepository.createNewEvent(name)
            eventNavigation?.navigateToEventDetails(id.toInt())
        }
    }
}