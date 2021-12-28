package com.codelab.pagging3.ui.charcters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.codelab.pagging3.R
import com.codelab.pagging3.base.BaseFragment
import com.codelab.pagging3.data.model.Characters
import com.codelab.pagging3.databinding.FragmentCharctersBinding
import com.codelab.pagging3.databinding.ItemCharacterBinding
import com.codelab.pagging3.utils.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class CharctersFragment : BaseFragment<FragmentCharctersBinding,CharctersViewModel>() , CharacterAdapter.CharacterClickListener {

      val  charctersViewModel by viewModels<CharctersViewModel>()

   /* private val charctersViewModel by lazy {
        ViewModelProvider(this).get(CharctersViewModel::class.java)
    }
*/
    @Inject
    lateinit var characterAdapter: CharacterAdapter
    override val layoutId: Int
        get() = R.layout.fragment_charcters

    override fun getVM() = charctersViewModel
    override fun bindVM(binding: FragmentCharctersBinding, vm: CharctersViewModel) {
        with(binding) {
            with(characterAdapter) {
                rvCharacters.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }
                rvCharacters.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                swipeRefresh.setOnRefreshListener { refresh() }
                characterClickListener = this@CharctersFragment

                with(vm) {
                    launchOnLifecycleScope {
                        charactersFlow.collectLatest { submitData(it) }
                    }
                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                        }
                    }
                }
            }
        }

    }

    override fun onCharacterClicked(binding: ItemCharacterBinding, character: Characters) {
        val extras = FragmentNavigatorExtras(
            binding.ivAvatar to "avatar_${character.id}",
            binding.tvName to "name_${character.id}",
            binding.tvStatus to "status_${character.id}"
        )
        /* findNavController().navigate(
            // CharactersFragmentDirections.actionCharactersToCharacterDetail(character),
            // extras
         )*/
    }

}