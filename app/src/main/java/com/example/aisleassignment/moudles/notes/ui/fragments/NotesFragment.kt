package com.example.aisleassignment.moudles.notes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aisleassignment.databinding.FragmentNotesBinding
import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.core.utils.Utils
import com.example.aisleassignment.moudles.notes.ui.adapter.NotesAdapter
import com.example.aisleassignment.moudles.notes.viewModel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NotesViewModel>()

    private val notesAdapter = NotesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.notesData.collectLatest {
                if (it == null) return@collectLatest
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        notesAdapter.submitList(it.data)
                    }

                    Resource.Status.ERROR -> {
                        Utils.showToast(requireContext(), "Something went wrong")
                    }

                    else -> {
                        Utils.showToast(requireContext(), "Something went wrong")
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvNote.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()
    }
}