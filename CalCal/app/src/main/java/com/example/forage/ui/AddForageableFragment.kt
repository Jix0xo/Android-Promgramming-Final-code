package com.example.forage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.forage.BaseApplication
import com.example.forage.R
import com.example.forage.databinding.FragmentAddForageableBinding
import com.example.forage.model.Forageable
import com.example.forage.ui.viewmodel.ForageableViewModel
import com.example.forage.ui.viewmodel.ForageableViewModelFactory

class AddForageableFragment : Fragment() {
    private val navigationArgs: AddForageableFragmentArgs by navArgs()
    private var _binding: FragmentAddForageableBinding? = null
    private lateinit var forageable: Forageable
    private val binding get() = _binding!!
    private val viewModel: ForageableViewModel by activityViewModels {
        ForageableViewModelFactory((activity?.application as BaseApplication).database.forageableDao())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddForageableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.getForageable(id).observe(viewLifecycleOwner) {
                forageable = it
                bindForageable(it)
            }

            binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteForageable(forageable)
            }
        } else {
            binding.saveBtn.setOnClickListener {
                addForageable()
            }
        }
    }

    // Forageable을 삭제하는 함수입니다.
    private fun deleteForageable(forageable: Forageable) {
        viewModel.deleteForageable(forageable)
        findNavController().navigate(R.id.action_addForageableFragment_to_forageableListFragment)
    }

    // Forageable을 추가하는 함수입니다.
    private fun addForageable() {
        if (isValidEntry()) {
            viewModel.addForageable(
                binding.dateInput.text.toString(),
                binding.morningInput.text.toString(),
                binding.lunchInput.text.toString(),
                binding.dinnerInput.text.toString(),
                binding.morningKcalInput.text.toString(),
                binding.lunchKcalInput.text.toString(),
                binding.dinnerKcalInput.text.toString(),
                binding.dinnerKcalInput.text.toString()
            )
            findNavController().navigate(R.id.action_addForageableFragment_to_forageableListFragment)
        }
    }

    // Forageable을 업데이트하는 함수입니다.
    private fun updateForageable() {
        if (isValidEntry()) {
            viewModel.updateForageable(
                id = navigationArgs.id,
                date = binding.dateInput.text.toString(),
                morning = binding.morningInput.text.toString(),
                lunch = binding.lunchInput.text.toString(),
                dinner = binding.dinnerInput.text.toString(),
                morning_kcal = binding.morningKcalInput.text.toString(),
                lunch_kcal = binding.lunchKcalInput.text.toString(),
                dinner_kcal = binding.dinnerKcalInput.text.toString(),
                kcal_total = binding.dinnerKcalInput.text.toString()
            )
            findNavController().navigate(R.id.action_addForageableFragment_to_forageableListFragment)
        }
    }

    // Forageable 객체를 UI에 바인딩하는 함수입니다.
    private fun bindForageable(forageable: Forageable) {
        binding.apply {
            dateInput.setText(forageable.date, TextView.BufferType.SPANNABLE)
            morningInput.setText(forageable.morning, TextView.BufferType.SPANNABLE)
            lunchInput.setText(forageable.lunch, TextView.BufferType.SPANNABLE)
            dinnerInput.setText(forageable.dinner, TextView.BufferType.SPANNABLE)
            morningKcalInput.setText(forageable.morning_kcal.toString(), TextView.BufferType.SPANNABLE)
            lunchKcalInput.setText(forageable.lunch_kcal.toString(), TextView.BufferType.SPANNABLE)
            dinnerKcalInput.setText(forageable.dinner_kcal.toString(), TextView.BufferType.SPANNABLE)

            saveBtn.setOnClickListener {
                updateForageable()
            }
        }
    }

    // 입력된 값들이 유효한지 확인하는 함수입니다.
    private fun isValidEntry() = viewModel.isValidEntry(
        binding.dateInput.text.toString(),
        binding.morningInput.text.toString(),
        binding.lunchInput.text.toString(),
        binding.dinnerInput.text.toString(),
        binding.morningKcalInput.text.toString(),
        binding.lunchKcalInput.text.toString(),
        binding.dinnerKcalInput.text.toString(),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
