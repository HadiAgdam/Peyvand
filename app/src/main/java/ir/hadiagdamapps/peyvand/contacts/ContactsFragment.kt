package ir.hadiagdamapps.peyvand.contacts

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.ContactsHelper
import ir.hadiagdamapps.peyvand.tools.MyFragment

class ContactsFragment : MyFragment(R.layout.fragment_contacts) {

    // TODO implement hold to select, and remove functions

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ContactsRecyclerAdapter
    private val helper by lazy { ContactsHelper(requireContext()) }

    private fun initContent() {
        val contacts = helper.getContacts()
        adapter = ContactsRecyclerAdapter(requireContext(), contacts)
        recycler.adapter = adapter
    }

    override fun initViews(view: View) {
        recycler = view.findViewById(R.id.recycler)
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    override fun main() {
        initContent()
    }
}