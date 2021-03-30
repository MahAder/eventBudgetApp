package com.ader.eventbudget20.presentation.participant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.domain.repository.ParticipantRepository
import com.ader.eventbudget20.domain.repository.UserRepository
import com.ader.eventbudget20.domain.utils.MapUtils.toParticipantDBModel
import com.ader.eventbudget20.presentation.base.BaseViewModel
import com.ader.eventbudget20.presentation.model.AddParticipantAdapterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddParticipantsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val participantRepository: ParticipantRepository,
    private val state: SavedStateHandle
): BaseViewModel() {
    private val eventId = state.get<Int>("eventId") ?: -1
    private lateinit var allUserList: List<User>
    private lateinit var allParticipants: List<User>
    val participantLiveData = MutableLiveData<MutableList<AddParticipantAdapterModel>>()
    private val listToSubmit = ArrayList<User>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allUserList = userRepository.getAllUsers()
            allParticipants = participantRepository.getAllParticipants(eventId)

            val users = allUserList.filter { user ->
                !allParticipants.contains(user)
            }.map {
                AddParticipantAdapterModel(it, false)
            }

            participantLiveData.postValue(users.toMutableList())
        }
    }

    fun createUser(userName: String){
        viewModelScope.launch(Dispatchers.IO){
            val user = userRepository.createUser(userName)
            participantLiveData.value?.add(AddParticipantAdapterModel(user, true))
            participantLiveData.postValue(participantLiveData.value)
            listToSubmit.add(user)
        }
    }

    fun createParticipants(onFinish: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            val participants = listToSubmit.map {
                it.toParticipantDBModel(eventId)
            }

            participantRepository.createParticipants(participants)
            onFinish()
        }
    }

    fun onUserCheckedChange(position: Int, isChecked: Boolean){
        if(isChecked){
            addUserToParticipants(position)
        } else {
            removeUserFromParticipants(position)
        }
    }

    private fun addUserToParticipants(position: Int){
        participantLiveData.value?.let {
            it[position].isParticipant = true
            listToSubmit.add(it[position].user)
        }
    }

    private fun removeUserFromParticipants(position: Int){
        participantLiveData.value?.let {
            listToSubmit.remove(it[position].user)
            it[position].isParticipant = false
        }
    }
}