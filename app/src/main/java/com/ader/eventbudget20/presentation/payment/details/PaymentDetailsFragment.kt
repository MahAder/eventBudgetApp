package com.ader.eventbudget20.presentation.payment.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ader.eventbudget20.presentation.adapter.AddParticipantAdapter
import com.ader.eventbudget20.presentation.base.BaseFragment
import com.ader.eventbudget20.presentation.model.AddParticipantAdapterModel
import com.receipt.eventbudget20.R
import com.receipt.eventbudget20.databinding.FragmentPaymentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentDetailsFragment : BaseFragment<PaymentDetailsViewModel>(), PaymentDetailsNavigation {
    override val viewModel: PaymentDetailsViewModel by viewModels()
    lateinit var binding: FragmentPaymentDetailsBinding

    private val payerAdapter = AddParticipantAdapter()
    private val payParticipantsAdapter = AddParticipantAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentPaymentDetailsBinding.inflate(inflater, container, false)
        viewModel.paymentDetailsNavigation = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val layoutManagerPayers =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val layoutManagerPayParticipants =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvPayers.adapter = payerAdapter
            rvPayersParticipants.adapter = payParticipantsAdapter
            rvPayers.layoutManager = layoutManagerPayers
            rvPayersParticipants.layoutManager = layoutManagerPayParticipants

            payerAdapter.onCheckedCheckChange = object : AddParticipantAdapter.OnItemCheckChange {
                override fun onItemCheckChange(position: Int, checked: Boolean) {
                    viewModel.payerListChange(position, checked)
                }
            }

            payParticipantsAdapter.onCheckedCheckChange =
                object : AddParticipantAdapter.OnItemCheckChange {
                    override fun onItemCheckChange(position: Int, checked: Boolean) {
                        viewModel.payParticipantsListChane(position, checked)
                    }
                }

            viewModel.payerListLiveData.observe(viewLifecycleOwner) {
                payerAdapter.setData(it)
            }

            viewModel.paymentParticipantListLiveData.observe(viewLifecycleOwner) {
                payParticipantsAdapter.setData(it)
            }

            viewModel.paymentLiveData.observe(viewLifecycleOwner) {
                etDescription.setText(it.description)
                etValue.setText(it.value.toString())
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.payment_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_payment -> {
                viewModel.deletePayment()
                true
            }
            R.id.action_save_changes -> {
                viewModel.updateEvent(
                    binding.etDescription.text.toString(),
                    binding.etValue.text.toString().toFloat()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun back() {
        view?.post {
            findNavController().popBackStack()
        }
    }
}