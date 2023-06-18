package com.example.forage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.forage.BaseApplication
import com.example.forage.R
import com.example.forage.databinding.FragmentForageableListBinding
import com.example.forage.ui.adapter.ForageableListAdapter
import com.example.forage.ui.viewmodel.ForageableViewModel
import com.example.forage.ui.viewmodel.ForageableViewModelFactory

class ForageableListFragment : Fragment() {
    private val viewModel: ForageableViewModel by activityViewModels {
        ForageableViewModelFactory((activity?.application as BaseApplication).database.forageableDao())
    }

    private var _binding: FragmentForageableListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Fragment의 UI를 inflate하여 View를 생성합니다.
        _binding = FragmentForageableListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ForageableListAdapter를 생성하고 클릭 리스너를 설정합니다.
        val adapter = ForageableListAdapter { forageable ->
            // 클릭된 Forageable의 상세 정보를 보기 위해 ForageableDetailFragment로 이동합니다.
            val action = ForageableListFragmentDirections.actionForageableListFragmentToForageableDetailFragment(forageable.id)
            findNavController().navigate(action)
        }

        // ForageableViewModel에서 LiveData인 forageables를 관찰하고 어댑터에 데이터를 전달합니다.
        viewModel.forageables.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            // RecyclerView에 어댑터를 설정합니다.
            recyclerView.adapter = adapter

            // 새로운 Forageable을 추가하기 위해 AddForageableFragment로 이동하는 버튼의 동작을 설정합니다.
            addForageableFab.setOnClickListener {
                findNavController().navigate(R.id.action_forageableListFragment_to_addForageableFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
