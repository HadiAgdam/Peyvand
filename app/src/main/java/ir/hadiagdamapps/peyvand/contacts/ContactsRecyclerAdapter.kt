package ir.hadiagdamapps.peyvand.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Contact

class ContactsRecyclerAdapter(private val context: Context, private val list: List<Contact>) :
    RecyclerView.Adapter<ContactsRecyclerAdapter.Holder>() {


    // TODO implement hold to select, and remove functions
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.profile_card_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val contact = list[position]


        holder.name.text = contact.name.toString()
        holder.tel.text = contact.tel.toString()



        if (contact.picture == null) {
            TODO("load from a placeholder")
        } else {
            TODO("load the image using picasso")
        }
    }


    class Holder(view: View) : ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val name: TextView = view.findViewById(R.id.name)
        val tel: TextView = view.findViewById(R.id.tel)
    }
}