package com.example.bsnanny.views.fragments.nanny

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.R
import com.example.bsnanny.adapter.findNanny.FindNannyAdapter
import com.example.bsnanny.databinding.FragmentFindNannyBinding
import com.example.bsnanny.models.findNanny.FindNanny
import com.example.bsnanny.models.findNanny.FindNannyApiItems
import com.example.bsnanny.models.findNanny.FindNannyBody
import com.example.bsnanny.models.findNanny.FindNannyData
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.viewmodels.nannies.FindNannyViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class FindNannyFragment : Fragment() {
    private lateinit var binding: FragmentFindNannyBinding

    private lateinit var mList: ArrayList<FindNannyData>
    private lateinit var searchList: ArrayList<FindNannyData>
    private lateinit var adapter: FindNannyAdapter
    private var titleText: String = ""
    private var iterator: Int = 0
    private lateinit var arrayList: ArrayList<String>
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private var stringIndex : Int = 0
    private val findNannyViewModel : FindNannyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFindNannyBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_find_nanny, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FN", "onViewCreated: ${arguments?.getString("data")} ")




        val findNannyItems: FindNannyApiItems? = arguments?.getParcelable("findNannyItems")

        val findNannyBody = FindNannyBody(
            latitude = findNannyItems?.latitude.toString(),
            longitude = findNannyItems?.longitude.toString(),
            price = findNannyItems?.price.toString(),
            noOfChildren = findNannyItems?.noOfChildren?.toDouble()?.toInt()!!,
            from = findNannyItems.from.toString(),
            to = findNannyItems.to.toString(),
            startDate = findNannyItems.startDate.toString(),
            endDate = findNannyItems.endDate.toString(),
            city = findNannyItems.city.toString(),
            pin = findNannyItems.pin.toString(),
            country = findNannyItems.country.toString(),
            address = findNannyItems.address.toString()
        )

        findNanny(findNannyBody)
        subscribeObserver()



        val activity = activity as AppCompatActivity?


        activity?.setSupportActionBar(binding.nannyToolbar)
        if (activity?.supportActionBar != null) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        }
        binding.nannyToolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        setHasOptionsMenu(true)
        binding.nannyToolbar.title = ""
        binding.nannyToolbar.setOnClickListener {
            handleToolbarClick()
        }
        arrayList = ArrayList()
        arrayList.add("Find Nanny")
        arrayList.add("Get Nanny")
        arrayList.add("Choose the best Nanny")
        arrayList.add("Search Nanny")
        arrayList.add("Search Here")

        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        //addDataToList()


      //  nannyNameArray = arrayOfNulls<String>(mList.size)







         runnable = object : Runnable {
            override fun run() {

                handleTitleText()

                handler.postDelayed(this, 7000)
            }
        }

        binding.titleTetSwitcher.setFactory {
            val textView = TextView(requireActivity())
            textView.setTextColor(Color.BLACK)
            textView.textSize = 20F
            textView.typeface = Typeface.DEFAULT_BOLD
            textView.gravity = Gravity.CENTER_HORIZONTAL
            textView
        }
        binding.titleTetSwitcher.setText(arrayList[stringIndex])


    }


    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onCreateOptionsMenu(menu, inflater)",
            "androidx.fragment.app.Fragment"
        )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem: MenuItem = menu.findItem(R.id.searchMenu)
        val searchView: SearchView = menuItem.actionView as SearchView

        searchView.queryHint = "Search Nanny here"
        searchView.findViewById<LinearLayout>(androidx.appcompat.R.id.search_plate)
            .setBackgroundColor(Color.TRANSPARENT)


            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    filterList(newText)
                    return true
                }

            })




        val filterItem = menu.findItem(R.id.Filter)
        filterItem.setOnMenuItemClickListener {
            filterItem.isEnabled = false
            val popupView = layoutInflater.inflate(R.layout.filter_nanny_dialog, null)
            val popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
            popupWindow.showAsDropDown(searchView,0,0)
            popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))

            popupWindow.animationStyle = R.style.PopupAnimation

            val dismiss : MaterialButton = popupView.findViewById(R.id.filterApplyButton)
            dismiss.setOnClickListener {
                filterItem.isEnabled = true

                popupWindow.dismiss()
            }


            true
        }



            super.onCreateOptionsMenu(menu, inflater)
        }




    private fun filterList(query: String?) {
        if (query != null) {
            searchList = ArrayList()
            for (i in mList) {
                if (i.name.lowercase(Locale.ROOT).contains(query)) {
                    searchList.add(i)
                }
            }
            if (searchList.isEmpty()) {
                adapter.filteredList(searchList)
                binding.nannyRCVStatus.visibility = View.VISIBLE
                binding.nannyRCVStatus.text = "No Nannies Found"
                Toasty.info(requireContext(), "No Data Found", Toast.LENGTH_SHORT, true).show()
            } else {
                adapter.filteredList(searchList)
                binding.nannyRCVStatus.visibility = View.INVISIBLE
            }
        }

    }

    private fun handleToolbarClick() {
        val searchItem = binding.nannyToolbar.menu.findItem(R.id.searchMenu)
        val searchView = searchItem.actionView as SearchView
        searchItem.expandActionView()
        searchView.isIconified = false
    }


    private fun handleTitleText() {

        if (iterator <  arrayList.size){
            titleText = arrayList[iterator]
            if (stringIndex == arrayList.size - 1){
                stringIndex = 0
                binding.titleTetSwitcher.setText(arrayList[stringIndex])
            }else{
                binding.titleTetSwitcher.setText(arrayList[++stringIndex])
            }
            iterator++
        }else{
            iterator = 0
        }
    }
    override fun onStart() {
        handler.postDelayed(runnable, 7000)
        super.onStart()
    }

    override fun onStop() {
        handler.removeCallbacks(runnable)
        super.onStop()
    }
    private fun subscribeObserver(){
        findNannyViewModel.res.observe(viewLifecycleOwner){
            when(it){
                is NetworkResults.Error -> {
                    Toast.makeText(requireContext(), it.errorMessage.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResults.Loading -> {


                }
                is NetworkResults.Success -> {

                    mList = it.data?.nanny?.nannies!!
                    Toast.makeText(requireContext(), "ok ", Toast.LENGTH_LONG).show()
                    adapter = FindNannyAdapter(it.data?.nanny?.nannies)
                    binding.requestsRecyclerView.adapter = adapter
                    adapter.itemClicked(object : FindNannyAdapter.OnItemsClicked{
                        override fun onClicked(position: Int) {
                            val action = FindNannyFragmentDirections.actionFindNannyFragmentToHireNannyFragment(it.data.nanny.nannies[position].id.toString())
                            findNavController().navigate(action)
                        }

                    })
                }
            }
        }
    }
    private fun findNanny(findNannyBody: FindNannyBody){
        findNannyViewModel.findNannies(findNannyBody)
    }

}