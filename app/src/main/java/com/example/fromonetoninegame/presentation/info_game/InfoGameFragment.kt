package com.example.fromonetoninegame.presentation.info_game

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.fromonetoninegame.R
import com.example.fromonetoninegame.base.BaseFragment

class InfoGameFragment : BaseFragment<InfoGameViewModel>() {

    override val layoutId: Int = R.layout.fragment_info_game

    override fun viewModelClass() = InfoGameViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivDescription: ImageView = view.findViewById(R.id.ivDescription)
        val ivNextPage: ImageView = view.findViewById(R.id.ivNext)
        val ivPreviousPage: ImageView = view.findViewById(R.id.ivPrevious)

        viewModel.initFirstPage()

        viewModel.currentPage.observe(viewLifecycleOwner) { pageInfo ->
            tvDescription.text = getString(pageInfo.descriptionResId)
            ivDescription.setImageResource(pageInfo.imageResId)
        }
        viewModel.closeFragment.observe(viewLifecycleOwner) {
            if (it) navController.navigateUp()
        }
        ivNextPage.setOnClickListener {
            viewModel.nextPage()
        }
        ivPreviousPage.setOnClickListener {
            viewModel.previousPage()
        }
    }
}
