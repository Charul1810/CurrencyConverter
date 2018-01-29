package com.incognysissolutions.currencyconverter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHARUL on 23-01-2018.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder>
         {
    private Context context;
    private List<Currency> contactList;
    private List<Currency> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView curr_name,symbol, rate;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            curr_name=view.findViewById(R.id.country_name);
            symbol = view.findViewById(R.id.country_symbol);
            rate = view.findViewById(R.id.rate);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                 //   listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public CurrencyAdapter(Context context, List<Currency> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Currency contact = contactListFiltered.get(position);
        holder.curr_name.setText(contact.getCurrencyname());
        holder.symbol.setText(contact.getSymbol());
        holder.rate.setText(contact.getValue());
//
//        Glide.with(context)
//                .load(contact.getImage())
//                .apply(RequestOptions.circleCropTransform())
//                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    contactListFiltered = contactList;
//                } else {
//                    List<Currency> filteredList = new ArrayList<>();
//                    for (Contact row : contactList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    contactListFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = contactListFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                contactListFiltered = (ArrayList<Contact>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public interface ContactsAdapterListener {
        void onContactSelected(Currency contact);
    }
}

