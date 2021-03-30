package com.ader.eventbudget20.presentation.participant

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.presentation.adapter.AddParticipantAdapter
import com.ader.eventbudget20.presentation.base.BaseDialogFragment
import com.ader.eventbudget20.presentation.base.BaseFragment
import com.receipt.eventbudget20.R
import com.receipt.eventbudget20.databinding.FragmentAddParticipantsBinding
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import java.text.FieldPosition

@AndroidEntryPoint
class AddParticipantFragment: BaseDialogFragment<AddParticipantsViewModel>() {
    private lateinit var binding: FragmentAddParticipantsBinding

    override val viewModel: AddParticipantsViewModel by viewModels()

    private val usersAdapter = AddParticipantAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentAddParticipantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvUsers.adapter = usersAdapter
            rvUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            viewModel.participantLiveData.observe(viewLifecycleOwner){
                usersAdapter.setData(it)
            }

            btnCreateParticipant.setOnClickListener {
                openCreateUserDialog()
            }

            usersAdapter.onCheckedCheckChange = object : AddParticipantAdapter.OnItemCheckChange {
                override fun onItemCheckChange(position: Int, checked: Boolean) {
                    viewModel.onUserCheckedChange(position, checked)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_participant_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_done -> {
                viewModel.createParticipants {
                    view?.post {
                        findNavController().popBackStack()
                    }
                }
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openCreateUserDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val editText = EditText(requireContext())
        editText.setHint(R.string.user_name)
        builder.setView(editText)
        builder.setTitle(R.string.create_participant)
        builder.setPositiveButton(R.string.create) { p0, p1 ->
            viewModel.createUser(editText.text.toString())
        }
        builder.show()
    }
}