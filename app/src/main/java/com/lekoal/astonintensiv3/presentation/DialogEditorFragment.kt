package com.lekoal.astonintensiv3.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.lekoal.astonintensiv3.R
import com.lekoal.astonintensiv3.databinding.FragmentDialogEditorBinding

const val EDITOR_CONTACT_ID = "EDITOR_CONTACT_ID"
const val EDITOR_CONTACT_NAME = "EDITOR_CONTACT_NAME"
const val EDITOR_CONTACT_SURNAME = "EDITOR_CONTACT_SURNAME"
const val EDITOR_CONTACT_PHONE = "EDITOR_CONTACT_PHONE"

class DialogEditorFragment : DialogFragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private var id = 0
    private var name = ""
    private var surname = ""
    private var phone = ""

    private var _binding: FragmentDialogEditorBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val args = requireArguments()
        if (!args.isEmpty) {
            id = args.getInt(EDITOR_CONTACT_ID, 0)
            name = args.getString(EDITOR_CONTACT_NAME, "")
            surname = args.getString(EDITOR_CONTACT_SURNAME, "")
            phone = args.getString(EDITOR_CONTACT_PHONE, "")
        }

        binding.editorTitle.text = if (isContactEdit) getString(
            R.string.editor_edit_contact_title,
            id
        ) else getString(R.string.editor_add_contact_title, id)
        binding.editorNameEdit.setText(name)
        binding.editorSurnameEdit.setText(surname)
        binding.editorPhoneEdit.setText(phone)

        binding.editorBtnCancel.setOnClickListener {
            dismiss()
        }

        binding.editorBtnConfirm.setOnClickListener {
            name = binding.editorNameEdit.text.toString()
            surname = binding.editorSurnameEdit.text.toString()
            phone = binding.editorPhoneEdit.text.toString()

            if (isContactEdit) {
                sharedViewModel.sendEditContact(id, name, surname, phone)
            } else {
                sharedViewModel.sendAddContact(id, name, surname, phone)
            }
            dismiss()
        }
    }

    companion object {
        var isContactEdit = false

        @JvmStatic
        fun newInstance(id: Int, name: String, surname: String, phone: String) =
            DialogEditorFragment().apply {
                isContactEdit = name.isNotEmpty()
                arguments = Bundle().apply {
                    putInt(EDITOR_CONTACT_ID, id)
                    putString(EDITOR_CONTACT_NAME, name)
                    putString(EDITOR_CONTACT_SURNAME, surname)
                    putString(EDITOR_CONTACT_PHONE, phone)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}