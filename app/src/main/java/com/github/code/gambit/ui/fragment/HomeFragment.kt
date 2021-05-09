package com.github.code.gambit.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.code.gambit.PreferenceManager
import com.github.code.gambit.R
import com.github.code.gambit.databinding.FilterLayoutBinding
import com.github.code.gambit.databinding.FragmentHomeBinding
import com.github.code.gambit.databinding.SearchLayoutBinding
import com.github.code.gambit.ui.activity.main.MainActivity
import com.github.code.gambit.utility.exitFullscreen
import com.github.code.gambit.utility.hide
import com.github.code.gambit.utility.show
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.Circle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private lateinit var _filterBinding: FilterLayoutBinding
    private val filterBinding get() = _filterBinding

    private lateinit var _searchBinding: SearchLayoutBinding
    private val searchBinding get() = _searchBinding

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        _filterBinding = binding.filterLayout
        _searchBinding = binding.searchLayout
        activity?.window?.exitFullscreen()
        registerFilterComponents()

        binding.filterButton.setOnClickListener {
            showFilter()
        }

        binding.searchButton.setOnClickListener {
            showSearch()
        }

        binding.overlay.setOnClickListener {
            closeFilter()
        }

        searchBinding.homeButton.setOnClickListener {
            requireMainActivity().onBackPressed()
        }

        Handler().postDelayed(
            {
                requireMainActivity().showBottomNav()
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.hide()
                binding.topContainer.show()
                binding.swipeRefresh.show()
                binding.noFileIllustrationContainer.show()
            },
            5000
        )

        // Handler().postDelayed({ spotlight() }, 6000)
    }

    private fun registerFilterComponents() {
        val bottomSheetBehavior = BottomSheetBehavior.from(filterBinding.bottomSheetContainer)
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    binding.overlay.hide()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.overlay.animate().alpha(slideOffset).setDuration(0).start()
                requireMainActivity().animateBottomNav(1 - slideOffset)
            }
        })
    }

    private fun spotlight() {
        val targets = ArrayList<Target>()

        val firstRoot = FrameLayout(requireContext())
        val first = layoutInflater.inflate(R.layout.home_first_spot, firstRoot)
        val firstTarget = Target.Builder()
            .setAnchor(requireMainActivity().getAddFab())
            .setShape(Circle(120f))
            .setOverlay(first)
            .setOnTargetListener(object : OnTargetListener { override fun onEnded() {} override fun onStarted() {} })
            .build()

        val secondRoot = FrameLayout(requireContext())
        val second = layoutInflater.inflate(R.layout.home_second_spot, secondRoot)
        val secondTarget = Target.Builder()
            .setAnchor(binding.searchButton)
            .setShape(Circle(50f))
            .setOverlay(second)
            .setOnTargetListener(object : OnTargetListener { override fun onEnded() {} override fun onStarted() {} }).build()

        val thirdTarget = Target.Builder()
            .setAnchor(binding.filterButton)
            .setShape(Circle(50f))
            .setOverlay(second)
            .setOnTargetListener(object : OnTargetListener { override fun onEnded() {} override fun onStarted() {} }).build()

        targets.add(firstTarget)
        targets.add(secondTarget)
        targets.add(thirdTarget)

        val spotlight = Spotlight.Builder(requireActivity())
            .setTargets(targets)
            .setBackgroundColorRes(R.color.spotlightBackground)
            .setDuration(1000L)
            .setAnimation(DecelerateInterpolator(2f))
            .setOnSpotlightListener(object : OnSpotlightListener { override fun onStarted() {} override fun onEnded() {} }).build()

        spotlight.start()

        val nextTarget = View.OnClickListener { spotlight.next() }

        // val closeSpotlight = View.OnClickListener { spotlight.finish() }

        first.findViewById<View>(R.id.next_button).setOnClickListener(nextTarget)
        second.findViewById<View>(R.id.next_button).setOnClickListener {
            spotlight.next()
            second.findViewById<TextView>(R.id.title_text).text = getString(R.string.use_filters)
            second.findViewById<TextView>(R.id.info_text).text = getString(R.string.or_you_can_filter_files_based_on_dates_they_were_uploaded)
            second.findViewById<View>(R.id.arrow).animate().rotation(105f).setDuration(500).start()
        }
    }

    private fun showFilter() {
        requireMainActivity().animateBottomNav(0f)
        binding.overlay.show()
        filterBinding.root.show()
        setState(true)
    }

    fun closeFilter() {
        setState(false)
        requireMainActivity().animateBottomNav(1f)
        binding.overlay.hide()
        filterBinding.root.hide()
    }

    private fun showSearch() {
        requireMainActivity().hideBottomNav()
        searchBinding.root.show()
    }

    fun closeSearch() {
        searchBinding.root.hide()
        requireMainActivity().showBottomNav()
    }

    private fun setState(show: Boolean) {
        val state = if (show) {
            BottomSheetBehavior.STATE_EXPANDED
        } else {
            BottomSheetBehavior.STATE_COLLAPSED
        }
        BottomSheetBehavior.from(filterBinding.bottomSheetContainer).state = state
    }

    private fun requireMainActivity() = (activity as MainActivity)

    fun isFilterEnable() =
        BottomSheetBehavior.from(filterBinding.bottomSheetContainer).state == BottomSheetBehavior.STATE_EXPANDED

    fun isSearchEnable() = searchBinding.root.isVisible
}
