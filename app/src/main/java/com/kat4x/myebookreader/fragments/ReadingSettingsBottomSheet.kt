package com.kat4x.myebookreader.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kat4x.myebookreader.adapter.PageBackgroundAdapter
import com.kat4x.myebookreader.databinding.DialogBottomReadingSettingsBinding
import com.kat4x.myebookreader.model.rv.Book

class ReadingSettingsBottomSheet : BottomSheetDialogFragment() {
    private var _binding: DialogBottomReadingSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageBackgroundAdapter: PageBackgroundAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBottomReadingSettingsBinding.inflate(inflater, container, false)
        pageBackgroundAdapter = PageBackgroundAdapter()
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        getDummyData()
        binding.run {
            backgroundCollection.apply {
                adapter = pageBackgroundAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
        }
    }

    private fun getDummyData() {
        val list = mutableListOf<Book>()
        for (i in 0..12) {
            list.add(Book(i))
        }
        pageBackgroundAdapter.differ.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}