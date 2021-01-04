package com.kat4x.myebookreader

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.kat4x.myebookreader.databinding.ActivityMainBinding
import com.kat4x.myebookreader.db.AppDatabase
import com.kat4x.myebookreader.utils.Constants
import java.util.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    val currentNavigationFragment: Fragment?
        get() =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                ?.childFragmentManager
                ?.fragments
                ?.first()

    lateinit var db: AppDatabase

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
                this.applicationContext,
        AppDatabase::class.java, "database-name"
        ).build()

        val repositoryBook = RepositoryBook(db.booksDao())
        val factory = MainViewModelFactory(repositoryBook)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

//        readingViewModel = ViewModelProvider(this).get(ReadingViewModel::class.java)

//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
//                // Set the content to appear under the system bars so that the
//                // content doesn't resize when the system bars hide and show.
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                // Hide the nav bar and status bar
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : BaseMultiplePermissionsListener() {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    super.onPermissionsChecked(p0)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    super.onPermissionRationaleShouldBeShown(p0, p1)
                }
            })

        setSupportActionBar(binding.toolbar)
//        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        supportActionBar?.setCustomView(R.layout.item_discovery_book)

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.discovery, R.id.bookmark, R.id.favorite))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupBottomNavMenu(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == Constance.PICK_PDF_CODE && resultCode == RESULT_OK && data != null) {
//            val selectPdf = data.data
//
//            val intent = Intent(this@MainActivity, ViewActivity::class.java)
//            intent.putExtra(Constance.VIEW_TYPE, "storage")
//            intent.putExtra("FileUri", selectPdf.toString())
//            startActivity(intent)
//
//        }
//    }

    fun provideBooksDatabase(app: Context
    ) = Room.databaseBuilder(app, AppDatabase::class.java, Constants.BOOKS_DATABASE_NAME).build()

    fun provideBooksDao(db: AppDatabase) = db.booksDao()

    private fun setupBottomNavMenu(navController: NavController) {
//        binding.run {
//            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(this@MainActivity)
//        }
        binding.bottomNav.setupWithNavController(navController)

    }

    private fun setPaddingFromBottom() {
//        val params = CoordinatorLayout.LayoutParams(
//            CoordinatorLayout.LayoutParams.MATCH_PARENT,
//            CoordinatorLayout.LayoutParams.MATCH_PARENT
//        )
//        params.setMargins(
//            0,
//            binding.bottomNav.layoutParams.height,
//            0,
//            binding.bottomNav.layoutParams.height
//        )// - 10)
//        binding.navHostFragment.layoutParams = params
        binding.navHostFragment.setPadding(
            0,
            0,
            0,
            binding.bottomNav.layoutParams.height,
        )
    }

    private fun fun1() {
        val viewActivity = Intent(this@MainActivity, ViewActivity::class.java)
        viewActivity.putExtra(Constance.VIEW_TYPE, "assets")
        startActivity(viewActivity)
    }

//    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        // Handle the return Uri
//    }

    private fun fun2() {
//        getContent.launch("application/pdf")
//        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
//        pdfIntent.type = "application/pdf"
//        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
//        startActivity(Intent.createChooser(pdfIntent, "Выберите PDF file"),
//            Constance.PICK_PDF_CODE)
//        registerForActivityResult()
//        startActivityForResult(
//            Intent.createChooser(pdfIntent, "Выберите PDF file"),
//            Constance.PICK_PDF_CODE
//        )
    }

    private fun fun3() {
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }

    fun hideBottomNavigation() {
        binding.bottomNav.run {
            animate()
                .translationY(height.toFloat())
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        isVisible = true
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        isVisible = false
                    }

                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                })
                .duration = 200
        }
    }

    fun showBottomNavigation() {
        binding.bottomNav.run {
            animate()
                .translationY(0f)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        isVisible = true
                    }

                    override fun onAnimationEnd(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}

                })
                .duration = 200
        }
    }

    fun hideToolbar() {
        binding.appBarLayout.run {
            animate()
                .translationY(-height.toFloat())
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        isVisible = true
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        isVisible = false
                    }

                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                })
                .duration = 200
        }
    }

    fun showToolbar() {
        binding.appBarLayout.run {
            animate()
                .translationY(0f)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        isVisible = true
                    }

                    override fun onAnimationEnd(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}

                })
                .duration = 200
        }
    }
}





















