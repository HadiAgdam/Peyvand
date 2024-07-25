package ir.hadiagdamapps.peyvand.contacts

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Contact
import ir.hadiagdamapps.peyvand.tools.ContactsHelper
import ir.hadiagdamapps.peyvand.tools.Picture

class ContactsRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<ContactsRecyclerAdapter.Holder>() {

    private val helper = ContactsHelper(context)
    private val list = helper.getAll()

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

        holder.overlay.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                setTitle(R.string.delete_dialog_title)
                setMessage(R.string.delete_dialog_content)
                setPositiveButton(R.string.delete_dialog_positive) { _, _ -> helper.delete(list[position]) }
                setNegativeButton(R.string.delete_dialog_negative, null)
                setNeutralButton(R.string.delete_dialog_neutral, null)
            }.show()

            true
        }

        holder.overlay.setOnClickListener {
            TODO("implement an animated new activity")
        }

        holder.name.text = contact.name.toString()
        holder.tel.text = contact.tel.toString()

        if (contact.picture == null)
            holder.image.setImageBitmap(Picture.getPlaceHolder(context))
        else holder.image.setImageBitmap(contact.picture!!.toBitmap())
        // TODO instead of picasso I'll just download/cache it and convert to bitmap
    }


    class Holder(view: View) : ViewHolder(view) {
        val overlay: View = view.findViewById(R.id.overlay)
        val image: ImageView = view.findViewById(R.id.image)
        val name: TextView = view.findViewById(R.id.name)
        val tel: TextView = view.findViewById(R.id.tel)
    }
}