package com.kat4x.myebookreader.fragments.discover

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.kat4x.myebookreader.MainActivity
import com.kat4x.myebookreader.R
import com.kat4x.myebookreader.databinding.FragmentReadingBinding
import com.kat4x.myebookreader.utils.makeToast
import kotlinx.coroutines.flow.collect

class ReadingFragment : Fragment() {

    /**
     * Binding
     * */
    private var _binding: FragmentReadingBinding? = null
    private val binding get() = _binding!!

    /**/
    private val args: ReadingFragmentArgs by navArgs()
    private val viewModel: ReadingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sharedElementEnterTransition = MaterialContainerTransform().apply {
//            drawingViewId = R.id.nav_host_fragment
//            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
//            scrimColor = Color.TRANSPARENT
//            setAllContainerColors(R.attr.colorSurface)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadingBinding.inflate(layoutInflater, container, false)
//        viewModel = ViewModelProvider(this).get(ReadingViewModel::class.java)
        loadBook(viewModel.pageState.value)
        collectFlow()
        return binding.root
    }

//    private fun subscribeObserver() {
//        viewModel.currentPage.observe(viewLifecycleOwner) {
//            it?.let {
//                binding.pdfViewer.jumpTo(it)
//                Snackbar.make(binding.root, "$it", Snackbar.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomNavigation()
    }

    private fun collectFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.pageState.collect {

//                Snackbar.make(binding.root, "$it", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadBook(currentPage: Int) {
        val selectedUri = Uri.parse(args.book)
        binding.pdfViewer.fromUri(selectedUri)
            .password(null)
            .defaultPage(currentPage)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
//            .pageSnap(true)
//            .pageFling(true)
            .nightMode(false)
            .onPageScroll { page, positionOffset ->
                viewModel.setCurrentOffset(positionOffset)
            }
            .onTap {
                requireContext().makeToast("tap")
                true
            }
//                        .onDraw { canvas, pageWidth, pageHeight, displayPage ->
//
//                        }.onDrawAll { canvas, pageWidth, pageHeight, displayPage ->
//
//                        }
            .onPageChange { page, pageCount ->
                viewModel.setCurrentPage(page)
//                if (page != 0)
//                    viewModel.setCurrentPage(page)
//                requireContext().makeToast("Page: $page, PageCount: $pageCount")
            }//.onPageError { page, t ->
//                requireContext().makeToast("Error while opening page $page")
//                Timber.e(t.localizedMessage)
//            }
//            .onTap { false }
//            .onRender { nbPages ->
//                binding.pdfViewer.fitToWidth(nbPages)
//            }
            .enableAnnotationRendering(true)
//                        .invalidPageColor(Color.RED)
            .load()
//        collectFlow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}