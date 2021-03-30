package com.ader.eventbudget20.presentation.event

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ader.eventbudget20.presentation.adapter.EventAdapter
import com.ader.eventbudget20.presentation.base.BaseFragment
import com.receipt.eventbudget20.R
import com.receipt.eventbudget20.databinding.FragmentEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : BaseFragment<EventViewModel>(), EventNavigation {
    private lateinit var binding: FragmentEventBinding

    override val viewModel: EventViewModel by viewModels()

    private val eventAdapter = EventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        viewModel.eventNavigation = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvEventList.adapter = eventAdapter
            rvEventList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            viewModel.allEventsLiveData.observe(viewLifecycleOwner) {
                eventAdapter.setData(it)
            }

            btnCreateEvent.setOnClickListener {
                openCreateEventDialog()
            }

            eventAdapter.onItemClick = {
                navigateToEventDetailsFragment(it.id)
            }
        }
    }

    private fun openCreateEventDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val editText = EditText(requireContext())
        builder.setTitle(R.string.create_event)
        editText.setHint(R.string.event_name)
        builder.setView(editText)
        builder.setPositiveButton(R.string.create) { p0, p1 ->
            viewModel.createEvent(editText.text.toString())
        }
        builder.show()
    }

    private fun navigateToEventDetailsFragment(eventId: Int){
        val action = EventFragmentDirections.actionEventFragmentToEventDetailsFragment(eventId)
        findNavController().navigate(action)
    }

    override fun navigateToEventDetails(eventId: Int) {
        view?.post {
            navigateToEventDetailsFragment(eventId)
        }
    }
}