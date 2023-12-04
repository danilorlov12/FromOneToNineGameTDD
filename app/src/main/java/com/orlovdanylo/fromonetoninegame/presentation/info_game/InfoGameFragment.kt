package com.orlovdanylo.fromonetoninegame.presentation.info_game

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.analytics.AnalyticsButton
import com.orlovdanylo.fromonetoninegame.analytics.logEventClickListener
import com.orlovdanylo.fromonetoninegame.base.BaseFragment
import com.orlovdanylo.fromonetoninegame.presentation.info_game.adapter.InfoGameAdapter

class InfoGameFragment : BaseFragment<InfoGameViewModel>() {

    override val layoutId: Int = R.layout.fragment_info_game
    override val viewModel: InfoGameViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivNextPage: ImageView = view.findViewById(R.id.ivNext)
        val ivPreviousPage: ImageView = view.findViewById(R.id.ivPrevious)

        val rvDescription: RecyclerView = view.findViewById(R.id.rvDescription)
        val infoAdapter = InfoGameAdapter()
        rvDescription.apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = infoAdapter
            itemAnimator = null
        }
        viewModel.initFirstPage()

        viewModel.currentPage.observe(viewLifecycleOwner) { pageInfo ->
            tvDescription.text = getString(pageInfo.descriptionResId)
            if (pageInfo.listOfModels.isNotEmpty()) {
                infoAdapter.submitList(pageInfo.listOfModels)
                rvDescription.visibility = View.VISIBLE
            } else {
                rvDescription.visibility = View.GONE
            }
        }
        viewModel.closeFragment.observe(viewLifecycleOwner) {
            if (it) navController.navigateUp()
        }
        ivNextPage.logEventClickListener(requireActivity(), AnalyticsButton.NEXT_PAGE) {
            viewModel.nextPage()
        }
        ivPreviousPage.logEventClickListener(requireActivity(), AnalyticsButton.PREVIOUS_PAGE) {
            viewModel.previousPage()
        }
    }
}
