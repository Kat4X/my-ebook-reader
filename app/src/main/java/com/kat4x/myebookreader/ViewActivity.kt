package com.kat4x.myebookreader

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kat4x.myebookreader.databinding.ActivityViewBinding
import timber.log.Timber

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.run {
            val viewType = this.getStringExtra(Constance.VIEW_TYPE)
            if (!viewType.isNullOrEmpty()) {

                if (viewType == "assets") {
                    binding.pdfViewer.fromAsset("pdf_example.pdf")
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onDraw { canvas, pageWidth, pageHeight, displayPage ->

                        }.onDrawAll { canvas, pageWidth, pageHeight, displayPage ->

                        }
                        .onPageChange { page, pageCount ->

                        }.onPageError { page, t ->
                            Toast.makeText(
                                this@ViewActivity,
                                "Error while opening page $page",
                                Toast.LENGTH_LONG
                            ).show()
                            Timber.e(t.localizedMessage)
                        }
                        .onTap { false }
//                        .onRender { nbPages, pageWidth, pageHeight ->
//                            binding.pdfViewer.fitToWidth()
//                        }
//                        .enableAnnotationRendering(true)
//                        .invalidPageColor(Color.RED)
                        .load()
                } else if (viewType == "storage") {
                    val selectedUri = Uri.parse(intent.getStringExtra("FileUri"))
                    binding.pdfViewer.fromUri(selectedUri)
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .pageSnap(true)
                        .pageFling(true)
                        .nightMode(true)
//                        .onDraw { canvas, pageWidth, pageHeight, displayPage ->
//
//                        }.onDrawAll { canvas, pageWidth, pageHeight, displayPage ->
//
//                        }
                        .onPageChange { page, pageCount ->

                        }.onPageError { page, t ->
                            Toast.makeText(
                                this@ViewActivity,
                                "Error while opening page $page",
                                Toast.LENGTH_LONG
                            ).show()
                            Timber.e(t.localizedMessage)
                        }
                        .onTap { false }
                        .onRender { nbPages ->
                            binding.pdfViewer.fitToWidth(nbPages)
                        }
                        .enableAnnotationRendering(true)
//                        .invalidPageColor(Color.RED)
                        .load()
                }
            }

        }
    }
}