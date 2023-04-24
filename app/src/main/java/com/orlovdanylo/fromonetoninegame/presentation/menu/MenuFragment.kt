package com.orlovdanylo.fromonetoninegame.presentation.menu

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.base.BaseFragment

class MenuFragment : BaseFragment<MenuViewModel>() {

    override val layoutId: Int = R.layout.fragment_menu

    override fun viewModelClass() = MenuViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkStoredGame()

        view.findViewById<AppCompatButton>(R.id.btnContinue).apply {
            setOnClickListener {
                val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(false)
                navController.navigate(action)
            }
            viewModel.hasStoredGame.observe(viewLifecycleOwner) {
                isEnabled = it
            }
        }
        view.findViewById<AppCompatButton>(R.id.btnNewGame).setOnClickListener {
            viewModel.deleteStoredGame()
        }
        view.findViewById<AppCompatImageButton>(R.id.btnInfo).setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToInfoFragment()
            navController.navigate(action)
        }
        viewModel.isGameDeleted.observe(viewLifecycleOwner) { isGameDeleted ->
            if (isGameDeleted) {
                val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(true)
                navController.navigate(action)
                viewModel.isGameDeleted.value = false
            }
        }
        view.findViewById<TextView>(R.id.tvVersion).text =
            requireActivity().application.packageManager.getPackageInfo(
                requireActivity().application.packageName, 0
            ).versionName
    }
}