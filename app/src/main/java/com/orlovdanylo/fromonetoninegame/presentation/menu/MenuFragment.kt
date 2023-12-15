package com.orlovdanylo.fromonetoninegame.presentation.menu

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.activityViewModels
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.analytics.AnalyticsButton
import com.orlovdanylo.fromonetoninegame.analytics.logEventClickListener
import com.orlovdanylo.fromonetoninegame.base.BaseFragment
import com.orlovdanylo.fromonetoninegame.utils.getPackageInfoCompat

class MenuFragment : BaseFragment<MenuViewModel>() {

    override val layoutId: Int = R.layout.fragment_menu
    override val viewModel: MenuViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkStoredGame()

        view.findViewById<AppCompatButton>(R.id.btnContinue).apply {
            logEventClickListener(requireActivity(), AnalyticsButton.CONTINUE) {
                val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(false)
                navController.navigate(action)
            }
            viewModel.hasStoredGame.observe(viewLifecycleOwner) {
                isEnabled = it
            }
        }

        view.findViewById<AppCompatButton>(R.id.btnNewGame)
            .logEventClickListener(requireActivity(), AnalyticsButton.NEW_GAME) {
                val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(true)
                navController.navigate(action)
            }

        view.findViewById<AppCompatImageButton>(R.id.btnInfo)
            .logEventClickListener(requireActivity(), AnalyticsButton.INFO) {
                val action = MenuFragmentDirections.actionMenuFragmentToInfoFragment()
                navController.navigate(action)
            }

        view.findViewById<AppCompatButton>(R.id.btnStatistics)
            .logEventClickListener(requireActivity(), AnalyticsButton.STATISTICS) {
                val action = MenuFragmentDirections.actionMenuFragmentToStatisticsFragment()
                navController.navigate(action)
            }

        view.findViewById<TextView>(R.id.tvVersion).text =
            requireActivity().application.packageManager
                .getPackageInfoCompat(requireActivity().application.packageName, 0)
                .versionName
    }

    override fun clear() {

    }
}