package com.example.forage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.forage.BaseApplication
import com.example.forage.MainActivity
import com.example.forage.R
import com.example.forage.databinding.FragmentForageableDetailBinding
import com.example.forage.model.Forageable
import com.example.forage.ui.viewmodel.ForageableViewModel
import com.example.forage.ui.viewmodel.ForageableViewModelFactory

class ForageableDetailFragment : Fragment() {
    private val navigationArgs: ForageableDetailFragmentArgs by navArgs()
    private val viewModel: ForageableViewModel by activityViewModels {
        ForageableViewModelFactory((activity?.application as BaseApplication).database.forageableDao())
    }
    private var _binding: FragmentForageableDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Fragment의 UI를 inflate하여 View를 생성합니다.
        _binding = FragmentForageableDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigation Arguments에서 Forageable의 ID를 가져옵니다.
        val id = navigationArgs.id
        // ForageableViewModel을 사용하여 해당 ID의 Forageable을 가져와서 Observer를 통해 UI에 바인딩합니다.
        viewModel.getForageable(id).observe(viewLifecycleOwner) { forageable ->
            if (forageable != null) {
                bindForageable(forageable)
            }
        }
    }

    private fun bindForageable(forageable: Forageable) {
        binding.apply {
            // Forageable 객체의 값을 UI에 바인딩합니다.
            date.text = forageable.date
            morning.text = forageable.morning
            lunch.text = forageable.lunch
            dinner.text = forageable.dinner
            morningKcal.text = forageable.morning_kcal.toString()
            lunchKcal.text = forageable.lunch_kcal.toString()
            dinnerKcal.text = forageable.dinner_kcal.toString()
            kcalTotal.text = forageable.kcal_total.toString()

            // 성별 옵션과 선택된 성별 값을 기반으로 칼로리 범위를 설정합니다.
            val genderOptions = arrayOf("Male", "Female")
            val gender = genderOptions[(requireActivity() as MainActivity).selectedGender]

            val isGoodRange = when (gender) {
                "Male" -> forageable.kcal_total in 2000..3000
                "Female" -> forageable.kcal_total in 1600..2400
                else -> false
            }

            // 칼로리 범위에 따라 평가 문구를 설정합니다.
            evaluation.text = when {
                isGoodRange -> getString(R.string.good)
                gender == "Male" && forageable.kcal_total > 3000 -> getString(R.string.less)
                gender == "Female" && forageable.kcal_total > 2400 -> getString(R.string.less)
                else -> getString(R.string.more)
            }

            // Forageable을 편집하기 위해 AddForageableFragment로 이동하는 버튼의 동작을 설정합니다.
            editForageableFab.setOnClickListener {
                val action = ForageableDetailFragmentDirections.actionForageableDetailFragmentToAddForageableFragment(forageable.id)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
