package com.ader.eventbudget20.presentation.payment.createpayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ader.eventbudget20.presentation.adapter.AddParticipantAdapter
import com.ader.eventbudget20.presentation.base.BaseFragment
import com.ader.eventbudget20.presentation.model.AddParticipantAdapterModel
import com.receipt.eventbudget20.R
import com.receipt.eventbudget20.databinding.FragmentAddPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePaymentFragment: BaseFragment<CreatePaymentViewModel>(), CreatePaymentNavigation {
    override val viewModel: CreatePaymentViewModel by viewModels()
    lateinit var binding: FragmentAddPaymentBinding

    private val payerAdapter = AddParticipantAdapter()
    private val payParticipantsAdapter = AddParticipantAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.createPaymentNavigation = this
        binding = FragmentAddPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val layoutManagerPayers = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val layoutManagerPayParticipants = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvPayers.adapter = payerAdapter
            rvPayersParticipants.adapter = payParticipantsAdapter
            rvPayers.layoutManager = layoutManagerPayers
            rvPayersParticipants.layoutManager = layoutManagerPayParticipants

            payerAdapter.onCheckedCheckChange = object : AddParticipantAdapter.OnItemCheckChange {
                override fun onItemCheckChange(position: Int, checked: Boolean) {
                    viewModel.payerListChange(position, checked)
                }
            }

            payParticipantsAdapter.onCheckedCheckChange = object : AddParticipantAdapter.OnItemCheckChange {
                override fun onItemCheckChange(position: Int, checked: Boolean) {
                    viewModel.payParticipantsListChane(position, checked)
                }
            }

            viewModel.participantLiveData.observe(viewLifecycleOwner){
                payerAdapter.setData(
                    it.map { user ->
                        AddParticipantAdapterModel(user, false)
                    }
                )

                payParticipantsAdapter.setData(
                    it.map {user ->
                        AddParticipantAdapterModel(user, true)
                    }
                )
            }

            btnCreatePayment.setOnClickListener {
                if(etValue.text.toString().isEmpty()){
                    showIncorrectValueError()
                } else {
                    viewModel.createPayment(
                        etValue.text.toString().toFloat(),
                        etDescription.text.toString()
                    )
                }
            }
        }
    }

    override fun showIncorrectValueError() {
        makeToast(getString(R.string.incorrect_value_error))
    }

    override fun showEmptyPayerError() {
        makeToast(getString(R.string.empty_payer_error))
    }

    override fun showEmptyParticipantsError() {
        makeToast(getString(R.string.empty_participants_list))
    }

    override fun close() {
        view?.post {
            findNavController().popBackStack()
        }
    }
}