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
    private var duplicatePersonList = mutableListOf<Person>()

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
                personsAdapter.personList = duplicatePersonList
            } else {
                personsAdapter.personList = personsAdapter.personList.filter { it: Person -> it.name.contains(keyWord) }.toMutableList()
            }
            personsAdapter.notifyDataSetChanged()
        }
        setDummyData()
        ibClearSearch.setOnClickListener {
            etSearch.setText("")
        }
        personsAdapter.setItemClickListener { person: Person, position: Int ->
            PersonDetailBottomSheetFragment.newInstance(
                adapterPosition = position,
                person = person
            ).show(requireActivity().supportFragmentManager, TAG_PERSON_DETAIL_MODAL_BOTTOM_SHEET)
        }
    }

    private fun setDummyData() {
        val hostList = listOf<Person>(
            Person(
                name = "Lelouch Lamperouge",
                rating = 5f,
                ratingCount = 3203,
                iCanAfford = "0.0001 min",
                tempImageDrawable = R.drawable.lelouch,
                profession = "Emperor",
                hourlyWorth = 99999999.99,
                skillsList = listOf("Governing Countries", "Knightmare Pilot", "War Tactics", "International Strategy", "Hacking", "Logic", "Math", "Physics"),
                servicesList = listOf("Full nation conquest in 24 hours.", "Can stage a rebellion in 2 hours.", "Hack into an xyz computer in 5 minutes.")
            ),
            Person(
                name = "Jenny",
                rating = 5f,
                ratingCount = 3203,
                iCanAfford = "1 min",
                profession = "Rapper",
                hourlyWorth = 9999.99,
                skillsList = listOf("Singer", "Rapper", "Dancer", "Actress", "TV Star", "Instagram Influencer", "Youtube Influencer")
            ),
            Person(
                name = "Lisa",
                rating = 5f,
                ratingCount = 5993,
                iCanAfford = "1 min",
                profession = "Dancer",
                hourlyWorth = 9999.99,
                skillsList = listOf("Singer", "Rapper", "Dancer", "Actress", "TV Star", "Instagram Influencer", "Youtube Influencer")
            ),
            Person(
                name = "Rose",
                rating = 5f,
                ratingCount = 4002,
                iCanAfford = "1 min",
                tempImageDrawable = R.drawable.rose,
                profession = "Singer",
                hourlyWorth = 9999.99,
                skillsList = listOf("Singer", "Rapper", "Dancer", "Actress", "TV Star", "Instagram Influencer", "Youtube Influencer")
            ),
            Person(
                name = "Jisoo",
                rating = 5f,
                ratingCount = 6729,
                iCanAfford = "1 min",
                tempImageDrawable = R.drawable.jisoo,
                profession = "Actress",
                hourlyWorth = 9999.99,
                skillsList = listOf("Singer", "Rapper", "Dancer", "Actress", "TV Star", "Instagram Influencer", "Youtube Influencer")
            ),
            Person(
                name = "Monkey on the Hill",
                rating = 1.5f,
                ratingCount = 8,
                iCanAfford = "18 hrs",
                profession = "Runner",
                hourlyWorth = 99.99
            ),
            Person(
                name = "Jack the Black",
                rating = 3f,
                ratingCount = 33,
                iCanAfford = "3 hrs",
                tempImageDrawable = R.drawable.po,
                profession = "Martial Artist",
                hourlyWorth = 9.99
            ),
            Person(
                name = "Kangaroo Boxer",
                rating = 1.5f,
                ratingCount = 8,
                iCanAfford = "6 hrs",
                profession = "Boxer",
                hourlyWorth = 0.99
            ),
        )
        personsAdapter.personList = hostList.toMutableList()
        personsAdapter.notifyDataSetChanged()
    }
}

private const val ARG_PARAM_TAB = "ARG_PARAM_TAB"
