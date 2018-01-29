package com.incognysissolutions.currencyconverter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Tab_Exchange_Rates extends Fragment {
    List<Currency> mylist;
    TextView datetime;
    DatabaseHandler db;
    ListView listView;
    AppAdapter adapter;
    private static String URL = "https://api.fixer.io/latest?base=";
    SharedPreferences sharedPreferences;
    String selectedcurrency = "USD";
    String default_currency;
    ProgressDialog progressDialog;
    RequestQueue mRequestQueue;
    Menu menu;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tab__exchange__rates, container, false);
        setHasOptionsMenu(true);
        db = new DatabaseHandler(v.getContext());
        listView = (ListView) v.findViewById(R.id.credit_list_view);
        progressDialog = new ProgressDialog(getActivity());
        mRequestQueue = Volley.newRequestQueue(getActivity());
        datetime = v.findViewById(R.id.rate_time);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        final String formattedDate = "Last Updated on "+df.format(c.getTime());

        datetime.setText(formattedDate);

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("rates", Context.MODE_PRIVATE);
//        default_currency=sharedPreferences.getString("default_currency","USD");
//


       load();

        return v;
    }

    public void load() {


    List<Currency> list = db.getallcurrency();
        mylist = list;
        adapter = new AppAdapter();
        listView.setAdapter(adapter);


    }


    String twodecimal(String c) {
        double d = Double.parseDouble(c);
        return String.format("%.2f", d);
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mylist.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity().getApplicationContext(),
                        R.layout.list_item, null);
                new ViewHolder(convertView);
            }


            final ViewHolder holder = (ViewHolder) convertView.getTag();



            holder.curr_id.setText(mylist.get(position).getId()+" ");
            holder.symbol.setText(mylist.get(position).getCurrencycode());

            if(holder.symbol.getText().equals("AUD")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.aud));

            }
            else if(holder.symbol.getText().equals("BGN")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.bgn));

            }

            else if(holder.symbol.getText().equals("BRL")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.brl));

            }

            else if(holder.symbol.getText().equals("CAD")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.cad));

            }


            else if(holder.symbol.getText().equals("CHF")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.chf));

            }


            else if(holder.symbol.getText().equals("CNY")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.cny));

            }


            else if(holder.symbol.getText().equals("CZK")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.czk));

            }


            else if(holder.symbol.getText().equals("DKK")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.dkk));

            }

            else if(holder.symbol.getText().equals("GBP")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.gbp));

            }


            else if(holder.symbol.getText().equals("HKD")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.hkd));

            }


            else if(holder.symbol.getText().equals("HRK")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.hrk));

            }


            else if(holder.symbol.getText().equals("HUF")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.huf));

            }


            else if(holder.symbol.getText().equals("IDR")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.idr));

            }



            else if(holder.symbol.getText().equals("ILS")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.ils));

            }



            else if(holder.symbol.getText().equals("INR")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.flag_of_india));

            }



            else if(holder.symbol.getText().equals("JPY")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.jpy));

            }



            else if(holder.symbol.getText().equals("KRW")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.krw));

            }



            else if(holder.symbol.getText().equals("MXN")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.mxn));

            }


            else if(holder.symbol.getText().equals("MYR")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.myr));

            }


            else if(holder.symbol.getText().equals("NOK")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.nok));

            }


            else if(holder.symbol.getText().equals("NZD")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.nzd));

            }


            else if(holder.symbol.getText().equals("PHP")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.php));

            }


            else if(holder.symbol.getText().equals("PLN")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.pln));

            }


            else if(holder.symbol.getText().equals("RON")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.ron));

            }


            else if(holder.symbol.getText().equals("RUB")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.rub));

            }


            else if(holder.symbol.getText().equals("SEK")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.ske));

            }


            else if(holder.symbol.getText().equals("SGD")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.sgd));

            }


            else if(holder.symbol.getText().equals("THB")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.thb));

            }


            else if(holder.symbol.getText().equals("TRY")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.tur));

            }


            else if(holder.symbol.getText().equals("USD")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.usd));

            }



            else if(holder.symbol.getText().equals("ZAR")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.zar));

            }


            else if(holder.symbol.getText().equals("EUR")){

                holder.thumbnail.setImageDrawable(getResources().getDrawable(R.drawable.eur));

            }







            try {
                holder.rate.setText(mylist.get(position).getSymbol() + " " + twodecimal(mylist.get(position).getValue()));
            }
            catch (Exception e){
               Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
            holder.country_name.setText( mylist.get(position).getCurrencyname());






            return convertView;

        }


        class ViewHolder {
            public TextView curr_id,symbol, rate,country_name,datetime;
            public ImageView thumbnail;


            public ViewHolder(View view) {
                curr_id=view.findViewById(R.id.curr_id);
                symbol = view.findViewById(R.id.country_symbol);
                rate = view.findViewById(R.id.rate);
                country_name=view.findViewById(R.id.country_name);
                thumbnail=view.findViewById(R.id.img_flag);
                // row_c=(LinearLayout) view.findViewById(R.id.row_credit);
                view.setTag(this);
            }
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        CharSequence sorting[]= new CharSequence[]{"AUD","BGN","BRL"};
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        if (id==R.id.action_refresh){



        }
        if(id==R.id.action_currency){



        }




        return super.onOptionsItemSelected(item);
    }




}
