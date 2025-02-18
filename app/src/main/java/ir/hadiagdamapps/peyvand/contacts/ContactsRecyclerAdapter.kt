package ir.hadiagdamapps.peyvand.contacts

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.data.ContactUpdateManager
import ir.hadiagdamapps.peyvand.data.models.contact.Contact
import ir.hadiagdamapps.peyvand.data.ContactsHelper
import ir.hadiagdamapps.peyvand.data.models.contact.ContactUpdate
import ir.hadiagdamapps.peyvand.tools.Picture

class ContactsRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<ContactsRecyclerAdapter.Holder>() {

    private val helper = ContactsHelper(context)
    private val list: ArrayList<Contact> = helper.getAll() as ArrayList
    private val updateManager = object : ContactUpdateManager(context) {
        override fun updateSuccess(contacts: List<ContactUpdate>) {
            contacts.forEach { contact ->
                list.indexOfFirst { it.publicKey == contact.publicKey.toString() }
                    .takeIf { it != -1 }?.let {
                        list[it].name = contact.name
                        contact.pictureUrl?.let { url -> list[it].picture = Picture.parse(url) }
                        list[it].bio = contact.bio
                        notifyItemRemoved(it)
                    }
            }
        }
    }

    init {
        updateManager.sync()
    }

    fun addItem(contact: Contact) {
        list.add(contact)
        notifyItemInserted(list.size - 1)
    }

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
                setPositiveButton(R.string.delete_dialog_positive) { _, _ ->
                    helper.delete(list[position])
                    list.removeAt(position)
                    notifyItemRemoved(position)
                }
                setNegativeButton(R.string.delete_dialog_negative, null)
                setNeutralButton(R.string.delete_dialog_neutral, null)
            }.show()

            true
        }

        holder.overlay.setOnClickListener {
            Intent(context, ContactActivity::class.java).apply {
                putExtra("contact", list[position])
                context.startActivity(this)
            }
        }

        holder.name.text = contact.name.toString()
        holder.tel.text = contact.tel.toString()

        if (contact.picture == null || contact.picture?.toBitmap() == null) {
            if (contact.picture?.urlString != null) {
                Log.e("image loaded picasso", contact.picture!!.urlString ?: "null")
                Picasso.get().load(contact.picture!!.urlString).into(holder.image)
            }
//            else
//                holder.image.setImageBitmap(Picture.getPlaceHolder(context))
        } else
            holder.image.setImageBitmap(contact.picture!!.toBitmap())
        // TODO instead of picasso I'll just download/cache it and convert to bitmap


        Log.e("telegram", contact.socialMedias.telegram ?: "bosh")
        Log.e("whatsapp", contact.socialMedias.x ?: "bosh")
        Log.e("instagram", contact.socialMedias.instagram ?: "bosh")

        contact.socialMedias.telegram?.let {
            holder.telegramIcon.visibility = View.VISIBLE
        }   
        contact.socialMedias.instagram?.let {
            holder.instagramIcon.visibility = View.VISIBLE
        }
        contact.socialMedias.x?.let {
            holder.whatsappIcon.visibility = View.VISIBLE
        }
    }


    class Holder(view: View) : ViewHolder(view) {
        val overlay: View = view.findViewById(R.id.overlay)
        val image: ImageView = view.findViewById(R.id.image)
        val name: TextView = view.findViewById(R.id.name)
        val tel: TextView = view.findViewById(R.id.tel)

        val telegramIcon: View = view.findViewById(R.id.telegramIcon)
        val instagramIcon: View = view.findViewById(R.id.instagramIcon)
        val whatsappIcon: View = view.findViewById(R.id.whatsappIcon)
    }
}