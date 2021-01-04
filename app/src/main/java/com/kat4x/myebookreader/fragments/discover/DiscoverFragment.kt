package com.kat4x.myebookreader.fragments.discover

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kat4x.myebookreader.BaseApplication
import com.kat4x.myebookreader.Constance
import com.kat4x.myebookreader.MainActivity
import com.kat4x.myebookreader.MainViewModel
import com.kat4x.myebookreader.adapter.RecentlyBooksAdapter
import com.kat4x.myebookreader.databinding.FragmentDiscoverBinding
import com.kat4x.myebookreader.model.rv.Book
import com.kat4x.myebookreader.utils.BookState
import com.kat4x.myebookreader.utils.makeToast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var recentlyBooksAdapter: RecentlyBooksAdapter
    private val viewModel: MainViewModel by lazy { (activity as MainActivity).mainViewModel }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(layoutInflater, container, false)
        recentlyBooksAdapter = RecentlyBooksAdapter()
        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlow()

    }

    private fun setupUI() {
        binding.run {
            recentBooksCollection.apply {
                adapter = recentlyBooksAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                recentlyBooksAdapter.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
//            getDummyData()

            recentlyBooksAdapter.setOnItemClickListener { view, book ->
//                navigateToReading(book.booksUri)
            }

            button.setOnClickListener {
                getNewBook()
            }

            var isShow = false
            floatingActionButton.setOnClickListener {
                viewModel.getRecentBooks()
//                val directions = DiscoverFragmentDirections.actionDiscoveryToReadingSettingsBottomSheet()
//                findNavController().navigate(directions)
                viewModel.setCurrentPage(66)
                isShow = if (isShow) {
                    (activity as MainActivity).hideBottomNavigation()
                    (activity as MainActivity).hideToolbar()
                    false
                } else {
                    (activity as MainActivity).showBottomNavigation()
                    (activity as MainActivity).showToolbar()
                    true
                }
            }
        }
    }

//    private fun getDummyData() {
//        val list = mutableListOf<Book>()
//        for (i in 0..12) {
//            list.add(Book(i))
//        }
//        recentlyBooksAdapter.differ.submitList(list)
//    }

    private val mStartForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val pdfFile = it.data?.data
            Timber.e("$pdfFile")
            context?.makeToast("$pdfFile")
            if (pdfFile != null) {
//                var index: Int? = null
//                val job1 =
                lifecycleScope.launch {
                    val index = BaseApplication.readBookIndex(Constance.BOOK_INDEX)
                    val book = viewModel.books.value
//                    Toast.makeText(requireContext(), "Job done $index", Toast.LENGTH_SHORT).show()
                    viewModel.insetBook(
                        Book(
                            index, 0, pdfFile.toString(), getFileName(pdfFile),
                            "https://i.pinimg.com/originals/a4/d4/50/a4d45069eec33b4885c00ee2378c6d79.jpg"
                        )
                    )
                    BaseApplication.saveBookIndex(Constance.BOOK_INDEX, index + 1)
                    viewModel.getRecentBooks()
                }
                navigateToReading(pdfFile.toString())
            }
        }
    }

    private fun getNewBook() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        mStartForResult.launch(intent)
    }

    private fun navigateToReading(book: String) {
//        val item = ItemDiscoveryBookBinding.bind(view)
//        val extras = FragmentNavigatorExtras(item.card as View to "iv_book")
        val directions = DiscoverFragmentDirections.actionDiscoverFragmentToReadingFragment(book)
        findNavController().navigate(directions)

//        exitTransition = MaterialElevationScale(false).apply {
//            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
//        }
//        reenterTransition = MaterialElevationScale(true).apply {
//            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
//        }
    }

    private fun collectFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.books.collect { response ->
//                requireContext().makeToast("$response")
                when (response) {
                    is BookState.Success -> {
                        val list = response.data?.sortedByDescending { it.id }
                        recentlyBooksAdapter.differ.submitList(list).also {
                            lifecycleScope.launch {
//                                delay(2000)
                                binding.recentBooksCollection.smoothScrollToPosition(
                                    recentlyBooksAdapter.itemCount
                                )
                                requireContext().makeToast("Scroll")
                            }
                        }
                    }
                    is BookState.Error -> {
                    }
                    is BookState.Loading -> {
                    }
                    is BookState.Empty -> {
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.pageState.collect {
//                requireContext().makeToast("$it")
            }
        }
    }

    @SuppressLint("Recycle")
    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme.equals("content")) {
            val cursor = activity?.contentResolver?.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


















