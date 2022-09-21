package ru.netology.recipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipebook.R
import ru.netology.recipebook.activity.NewRecipeFragment.Companion.REQUEST_KEY
import ru.netology.recipebook.databinding.FragmentFilterSwBinding
import ru.netology.recipebook.databinding.FragmentNewRecipeBinding
import ru.netology.recipebook.viewModel.RecipeViewModel

class FiltersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilterSwBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

//        binding.switchAmerican.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                println("Standard Switch is on")
//            } else {
//                println("Standard Switch is off")
//            }
//        }


        binding.ok.setOnClickListener {
            with(binding) {
                if (!(switchAmerican.isChecked || switchAsian.isChecked || switchEastern.isChecked || switchEuropean.isChecked ||
                            switchPanasian.isChecked || switchRussian.isChecked || switchMediterranean.isChecked)
                ) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.filterError),
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
            }
          var category = ""
           if ( binding.switchPanasian.isChecked) {
               category = binding.switchPanasian.text.toString()
           }

            //viewModel.onFilterClicked(category)
            setFragmentResult(REQUEST_KEY, Bundle())
            findNavController().navigateUp()
        }





        return binding.root
    }
    companion object {
        const val REQUEST_KEY = "filterFragmentRequestKey"
    }
}