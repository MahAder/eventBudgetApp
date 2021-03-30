package com.ader.eventbudget20.presentation.event.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ader.eventbudget20.domain.model.Payment
import com.ader.eventbudget20.presentation.adapter.ParticipantAdapter
import com.ader.eventbudget20.presentation.adapter.PaymentAdapter
import com.ader.eventbudget20.presentation.base.BaseFragment
import com.receipt.eventbudget20.R
import com.receipt.eventbudget20.databinding.FragmentEventDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetailsFragment : BaseFragment<EventDetailsViewModel>(), EventDetailsNavigation {
    override val viewModel: EventDetailsViewModel by viewModels()
    lateinit var binding: FragmentEventDetailsBinding

    private val participantAdapter = ParticipantAdapter()
    private val paymentAdapter = PaymentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        viewModel.eventDetailsNavigation = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvParticipantList.adapter = participantAdapter
            rvParticipantList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvPaymentsList.adapter = paymentAdapter
            rvPaymentsList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            viewModel.participantsLiveData.observe(viewLifecycleOwner) {
                participantAdapter.setData(it)
            }

            viewModel.paymentsLiveData.observe(viewLifecycleOwner) {
                paymentAdapter.setData(it)
            }

            paymentAdapter.onItemClick = {
                navigateToPaymentDetailsFragment(it)
            }

            viewModel.eventLiveData.observe(viewLifecycleOwner){
                tvEventName.text = it.name
            }

            viewModel.eventTotalValueLiveData.observe(viewLifecycleOwner){
                tvTotalValue.text = it.toString()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.event_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_participants -> {
                navigateToAddParticipantsFragment()
                true
            }
            R.id.action_add_payment -> {
                navigateToAddPaymentFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToAddParticipantsFragment() {
        val args: EventDetailsFragmentArgs by navArgs()
        val action =
            EventDetailsFragmentDirections.actionEventDetailsFragmentToAddParticipantFragment(
                args.eventId
            )
        findNavController().navigate(action)
    }

    private fun navigateToAddPaymentFragment() {
        val args: EventDetailsFragmentArgs by navArgs()
        val action =
            EventDetailsFragmentDirections.actionEventDetailsFragmentToCreatePaymentFragment(
                args.eventId
            )
        findNavController().navigate(action)
    }

    private fun navigateToPaymentDetailsFragment(payment: Payment) {
        val action =
            EventDetailsFragmentDirections.actionEventDetailsFragmentToPaymentDetailsFragment(
                payment.id
            )
        findNavController().navigate(action)
    }

    override fun navigateToAddParticipants() {
        view?.post {
            navigateToAddParticipantsFragment()
        }
    }
}