package elegant.access.apidemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import elegant.access.apidemo.databinding.FragmentSearchUserBinding
import elegant.access.apidemo.search.DaggerSearchUserComponent
import elegant.access.apidemo.search.SearchUserComponent
import elegant.access.apidemo.search.SearchUserViewModel
import elegant.access.apidemo.search.adapter.SearchUserAdapter
import javax.inject.Inject


class SearchUserFragment : Fragment() {

    private var _binding: FragmentSearchUserBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var searchUserViewModel: SearchUserViewModel

    private val searchUserComponent: SearchUserComponent by lazy {

        DaggerSearchUserComponent
            .builder()
            .appComponent((activity?.application as SearchUserApplication).appComponent)
            .fragment(this)
            .build()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        searchUserComponent.inject(this)
        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val searchUserAdapter = SearchUserAdapter()
        searchUserViewModel.pagingDataItems.observe(viewLifecycleOwner) {
            searchUserAdapter.submitList(it)
        }

        searchUserViewModel._searchUserStats.observe(viewLifecycleOwner) { searchUserUiStats ->
            when (searchUserUiStats) {
                ShowResult -> {
                    binding.fab.visibility = View.VISIBLE
                    binding.recyclerUser.visibility = View.VISIBLE
                    binding.lyError.errorLayout.visibility = View.GONE


                }
                is HideResult -> {

                    binding.fab.visibility = View.GONE
                    binding.recyclerUser.visibility = View.GONE
                    binding.lyError.errorLayout.visibility = View.VISIBLE
                    binding.lyError.errorTxtCause.text = searchUserUiStats.message

                }
            }
        }

        binding.lyError.errorBtnRetry.setOnClickListener {
            binding.editTextSearchName.requestFocus()
            binding.editTextSearchName.text = binding.editTextSearchName.text

        }

        binding.recyclerUser.layoutManager = LinearLayoutManager(context)
        binding.recyclerUser.adapter = searchUserAdapter
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Go to top", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
            (binding.recyclerUser.layoutManager as LinearLayoutManager).scrollToPosition(0)
        }
        binding.editTextSearchName.addTextChangedListener { editable ->

            searchUserViewModel.queryUser(editable.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

sealed class SearchUserUiStats
object ShowResult : SearchUserUiStats()
class HideResult(val message: String) : SearchUserUiStats()
