package com.singularitycoder.swapwithme

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.singularitycoder.swapwithme.databinding.FragmentPersonDetailBottomSheetBinding
import com.singularitycoder.swapwithme.databinding.FragmentSwapItemDetailBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SwapItemDetailBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance(
            adapterPosition: Int,
            swapItem: SwapItem
        ) = SwapItemDetailBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM_ADAPTER_POSITION, adapterPosition)
                putParcelable(ARG_PARAM_PERSON, swapItem)
            }
        }
    }

    private lateinit var binding: FragmentSwapItemDetailBottomSheetBinding

    private var adapterPosition: Int = 0
    private var swapItem: SwapItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapterPosition = arguments?.getInt(ARG_PARAM_ADAPTER_POSITION) ?: 0
        swapItem = arguments?.getParcelable<SwapItem>(ARG_PARAM_PERSON)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSwapItemDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransparentBackground()
        binding.setupUI()
        binding.setupUserActionListeners()
        binding.observeForData()
    }

    // https://stackoverflow.com/questions/42301845/android-bottom-sheet-after-state-changed
    // https://stackoverflow.com/questions/35937453/set-state-of-bottomsheetdialogfragment-to-expanded
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface: DialogInterface? ->
            // FIXME not working
        }
        return dialog
    }

    // https://stackoverflow.com/questions/40616833/bottomsheetdialogfragment-listen-to-dismissed-by-user-event
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
//        viewModel.contactAccidentBackupLiveData.value = Contact(
//            name = binding.etName.editText?.text.toString(),
//            mobileNumber = binding.etPhoneNumber.editText?.text.toString(),
//            dateAdded = timeNow,
//            imagePath = "",
//            videoPath = ""
//        )
    }

    private fun FragmentSwapItemDetailBottomSheetBinding.setupUI() {
        setBottomSheetBehaviour()
        ivImageExpanded.layoutParams.height = deviceWidth()
        ivImageExpanded.load(swapItem?.tempImageDrawable) {
            placeholder(R.drawable.ic_placeholder_big)
        }
        tvPersonName.text = swapItem?.name
//        swapItem?.skillsList?.forEach { it: String ->
//            val chip = Chip(requireContext()).apply {
//                text = it
//                isCheckable = false
//                isClickable = false
//                chipBackgroundColor = ColorStateList.valueOf(requireContext().color(R.color.black_50))
////                setTextColor(nnContext.color(R.color.purple_500))
//                elevation = 0f
//                setOnClickListener {
//                }
//            }
//            chipGroupSkills.addView(chip)
//        }
//        val itemBinding: ItemSmsBinding = ItemSmsBinding.inflate(LayoutInflater.from(requireContext()), binding.llServices, false)
//        itemBinding.tvNumber.text = sms.number
        /**
         * https://stackoverflow.com/questions/3429546/how-do-i-add-a-bullet-symbol-in-textview
         * • = \u2022,   ● = \u25CF,   ○ = \u25CB,   ▪ = \u25AA,   ■ = \u25A0,   □ = \u25A1,   ► = \u25BA
         * */
//        swapItem?.servicesList?.forEach { it: String ->
//            val serviceTextView = TextView(context).apply {
//                text = "\u25CF  $it"
//                setTextColor(requireContext().color(R.color.title_color))
//                setMargins(start = 0, top = 8.dpToPx(), end = 0, bottom = 0)
//                setPadding(0, 8.dpToPx(), 0, 0)
//            }
//            binding.llServices.addView(serviceTextView)
//        }
    }

    private fun FragmentSwapItemDetailBottomSheetBinding.setupUserActionListeners() {
        ivImageExpanded.setOnClickListener {
            ivImageExpanded.isVisible = false
        }
    }

    private fun setBottomSheetBehaviour() {
        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout? ?: return
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
//        bottomSheet.layoutParams.height = deviceHeight()
//        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        var oldState = BottomSheetBehavior.STATE_HIDDEN
        behavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                println("bottom sheet state: ${behavior.state}")
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        behavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> Unit
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        oldState = BottomSheetBehavior.STATE_EXPANDED
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> Unit
                    BottomSheetBehavior.STATE_HIDDEN -> Unit
                    BottomSheetBehavior.STATE_SETTLING -> {
                        if (oldState == BottomSheetBehavior.STATE_EXPANDED) {
                            behavior.state = BottomSheetBehavior.STATE_HIDDEN
                        }
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })
    }

    private fun FragmentSwapItemDetailBottomSheetBinding.observeForData() {

    }

    private fun TextInputLayout.doTextFieldEmptyValidation() {
        editText?.doAfterTextChanged { it: Editable? ->
            if (editText?.text.isNullOrBlank()) {
                error = "This is required!"
            } else {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun TextInputLayout.setBoxStrokeOnFocusChange() {
        editText?.setOnFocusChangeListener { view, isFocused ->
            boxStrokeWidth = if (isFocused) 2.dpToPx() else 0
        }
    }
}

private const val ARG_PARAM_ADAPTER_POSITION = "ARG_PARAM_ADAPTER_POSITION"
private const val ARG_PARAM_PERSON = "ARG_PARAM_PERSON"