package elegant.access.apidemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import elegant.access.apidemo.databinding.FragmentFirstBinding
import elegant.access.apidemo.http.observer.GitHubResultObserver
import elegant.access.apidemo.search.DaggerSearchUserComponent
import elegant.access.apidemo.search.SearchUserComponent
import elegant.access.apidemo.search.SearchUserViewModel
import elegant.access.apidemo.search.model.SearchUserResult
import javax.inject.Inject

class SearchUserFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {

            searchUserViewModel.queryUser("gmsv","1","2",object :GitHubResultObserver<SearchUserResult>(){
                override fun onSuccess(data: SearchUserResult) {
                    Log.d("testst","onSuccess")
                }

                override fun onFailure(e: RetrofitResultException) {
                    super.onFailure(e)
                    Log.d("error code :", e.code.toString())
                    Log.d("error message :", e.msg)

                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}