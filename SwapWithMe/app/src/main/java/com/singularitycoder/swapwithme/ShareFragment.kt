package com.singularitycoder.swapwithme

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.singularitycoder.swapwithme.databinding.FragmentShareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(tab: String) = ShareFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM_TAB, tab)
            }
        }
    }

    private var shareState: String? = null

    private lateinit var binding: FragmentShareBinding

    private val personsAdapter = PersonsAdapter()
    private var duplicateSwapItemList = mutableListOf<SwapItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareState = arguments?.getString(ARG_PARAM_TAB, "") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentShareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupUI()
        binding.setupUserActionListeners()
    }

    private fun FragmentShareBinding.setupUI() {
        if (shareState == Tab.HOME.value) cardSearch.isVisible = false
        rvFlukes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = personsAdapter
        }
    }

    private fun FragmentShareBinding.setupUserActionListeners() {
        etSearch.doAfterTextChanged { keyWord: Editable? ->
            ibClearSearch.isVisible = keyWord.isNullOrBlank().not()
            if (keyWord.isNullOrBlank()) {
                personsAdapter.swapItemList = duplicateSwapItemList
            } else {
                personsAdapter.swapItemList = personsAdapter.swapItemList.filter { it: SwapItem -> it.name.contains(keyWord) }.toMutableList()
            }
            personsAdapter.notifyDataSetChanged()
        }
        setDummyData()
        ibClearSearch.setOnClickListener {
            etSearch.setText("")
        }
        personsAdapter.setItemClickListener { swapItem: SwapItem, position: Int ->
            PersonDetailBottomSheetFragment.newInstance(
                adapterPosition = position,
                swapItem = swapItem
            ).show(requireActivity().supportFragmentManager, TAG_PERSON_DETAIL_MODAL_BOTTOM_SHEET)
        }
    }

    private fun setDummyData() {
        val hostList = listOf<SwapItem>(
            SwapItem(
                name = "Lelouch Lamperouge",
                rating = 5f,
                ratingCount = 3203,
                tempImageDrawable = R.drawable.lelouch,
            ),
            SwapItem(
                name = "Jenny",
                rating = 5f,
                ratingCount = 3203,
            ),
            SwapItem(
                name = "Lisa",
                rating = 5f,
                ratingCount = 5993,
            ),
            SwapItem(
                name = "Rose",
                rating = 5f,
                ratingCount = 4002,
                tempImageDrawable = R.drawable.rose,
            ),
            SwapItem(
                name = "Jisoo",
                rating = 5f,
                ratingCount = 6729,
                tempImageDrawable = R.drawable.jisoo,
            ),
            SwapItem(
                name = "Monkey on the Hill",
                rating = 1.5f,
                ratingCount = 8,
            ),
            SwapItem(
                name = "Jack the Black",
                rating = 3f,
                ratingCount = 33,
                tempImageDrawable = R.drawable.po,
            ),
            SwapItem(
                name = "Kangaroo Boxer",
                rating = 1.5f,
                ratingCount = 8,
            ),
        )
        personsAdapter.swapItemList = hostList.toMutableList()
        personsAdapter.notifyDataSetChanged()
    }
}

private const val ARG_PARAM_TAB = "ARG_PARAM_TAB"
