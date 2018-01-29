package com.incognysissolutions.currencyconverter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    SlidingTabLayout tabs;
    int Numboftabs = 2;
    CharSequence Titles[] = {"Exchange Rates", "Currency Converter"};
    HomeAdapter adapter;
    ViewPager pager;
    private RequestQueue mRequestQueue;
    public TextView curr_id, symbol, rate, country_name, datetime;
    List<Currency> mylist;
    private String jsonResponse;
    DatabaseHandler db;
    ProgressDialog progressDialog;
    private Menu menu;
    private boolean usd = false;

    private static String URL = "https://api.fixer.io/latest?base=";
    SharedPreferences sharedPreferences;
    String selectedcurrency = "USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        curr_id = findViewById(R.id.curr_id);
        symbol = findViewById(R.id.country_symbol);
        rate = findViewById(R.id.rate);
        country_name = findViewById(R.id.country_name);

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        db = new DatabaseHandler(this);


        sharedPreferences = getSharedPreferences("default_currency", Context.MODE_PRIVATE);
        int status = sharedPreferences.getInt("status", 0);


        String default_currency = sharedPreferences.getString("default_currency", "USD");
        URL = URL + default_currency;
        selectedcurrency = default_currency.trim();


        if (status == 0) {
            // Insert into DB
            loadata();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("status", 1);
            editor.putString("default_currency", "USD");
            editor.apply();
            load();
        } else {
            // Don not Insert Select
            load();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        this.menu = menu;
        //menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.usd));
        if (selectedcurrency.equals("USD")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.usd));
        } else if (selectedcurrency.equals("AUD")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.aud));
        } else if (selectedcurrency.equals("BGN")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgn));
        } else if (selectedcurrency.equals("BRL")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.brl));
        } else if (selectedcurrency.equals("CAD")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cad));
        } else if (selectedcurrency.equals("CHF")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.chf));
        } else if (selectedcurrency.equals("CNY")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cny));
        } else if (selectedcurrency.equals("CZK")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.czk));
        } else if (selectedcurrency.equals("DKK")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dkk));
        } else if (selectedcurrency.equals("GBP")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gbp));
        } else if (selectedcurrency.equals("HKD")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hkd));
        } else if (selectedcurrency.equals("HRK")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hrk));
        } else if (selectedcurrency.equals("HUF")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.huf));
        } else if (selectedcurrency.equals("IDR")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.idr));
        } else if (selectedcurrency.equals("ILS")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ils));
        } else if (selectedcurrency.equals("INR")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flag_of_india));
        } else if (selectedcurrency.equals("JPY")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.jpy));
        } else if (selectedcurrency.equals("KRW")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.krw));
        } else if (selectedcurrency.equals("MXN")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mxn));
        } else if (selectedcurrency.equals("MYR")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.myr));
        } else if (selectedcurrency.equals("NOK")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nok));
        } else if (selectedcurrency.equals("NZD")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nzd));
        } else if (selectedcurrency.equals("PHP")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.php));
        } else if (selectedcurrency.equals("PLN")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pln));
        } else if (selectedcurrency.equals("RON")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ron));
        } else if (selectedcurrency.equals("RUB")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rub));
        } else if (selectedcurrency.equals("SEK")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ske));
        } else if (selectedcurrency.equals("SGD")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sgd));
        } else if (selectedcurrency.equals("THB")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.thb));
        } else if (selectedcurrency.equals("TRY")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.tur));
        } else if (selectedcurrency.equals("USD")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.usd));
        } else if (selectedcurrency.equals("ZAR")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.zar));
        } else if (selectedcurrency.equals("EUR")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.eur));
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        if (id == R.id.action_refresh) {

            load();

        }

        if (id == R.id.action_currency)

        {


            new MaterialDialog.Builder(this)
                    .title("Select Currency")
                    .items(R.array.default_curr)
                    .itemsCallback(new MaterialDialog.ListCallback() {

                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                            SharedPreferences.Editor editor1 = sharedPreferences.edit();

                            if (which == 0) {

                                editor1.putString("default_currency", "AUD");

                            }

                            if (which == 1) {

                                editor1.putString("default_currency", "BGN");
                            }

                            if (which == 2) {

                                editor1.putString("default_currency", "BRL");
                            }


                            if (which == 3) {

                                editor1.putString("default_currency", "CAD");

                            }

                            if (which == 4) {

                                editor1.putString("default_currency", "CHF");

                            }


                            if (which == 5) {

                                editor1.putString("default_currency", "CNY");

                            }


                            if (which == 6) {

                                editor1.putString("default_currency", "CZK");

                            }


                            if (which == 7) {

                                editor1.putString("default_currency", "DKK");

                            }


                            if (which == 8) {

                                editor1.putString("default_currency", "GBP");

                            }


                            if (which == 9) {

                                editor1.putString("default_currency", "HKD");
                            }


                            if (which == 10) {

                                editor1.putString("default_currency", "HRK");

                            }


                            if (which == 11) {

                                editor1.putString("default_currency", "HUF");

                            }

                            if (which == 12) {

                                editor1.putString("default_currency", "IDR");

                            }

                            if (which == 13) {

                                editor1.putString("default_currency", "ILS");

                            }

                            if (which == 14) {

                                editor1.putString("default_currency", "INR");

                            }

                            if (which == 15) {

                                editor1.putString("default_currency", "JPY");

                            }

                            if (which == 16) {

                                editor1.putString("default_currency", "KRW");

                            }

                            if (which == 17) {

                                editor1.putString("default_currency", "MXN");

                            }

                            if (which == 18) {

                                editor1.putString("default_currency", "MYR");

                            }

                            if (which == 19) {

                                editor1.putString("default_currency", "NOK");

                            }

                            if (which == 20) {

                                editor1.putString("default_currency", "NZD");

                            }

                            if (which == 21) {

                                editor1.putString("default_currency", "PHP");

                            }

                            if (which == 22) {

                                editor1.putString("default_currency", "PLN");

                            }

                            if (which == 23) {

                                editor1.putString("default_currency", "RON");

                            }

                            if (which == 24) {

                                editor1.putString("default_currency", "RUB");

                            }

                            if (which == 25) {

                                editor1.putString("default_currency", "SEK");

                            }

                            if (which == 26) {

                                editor1.putString("default_currency", "SGD");

                            }

                            if (which == 27) {

                                editor1.putString("default_currency", "TBB");

                            }

                            if (which == 28) {

                                editor1.putString("default_currency", "TRY");

                            }

                            if (which == 29) {

                                editor1.putString("default_currency", "USD");

                            }


                            if (which == 30) {

                                editor1.putString("default_currency", "ZAR");

                            }


                            if (which == 31) {

                                editor1.putString("default_currency", "EUR");

                            }


                            editor1.apply();

                            String default_cu = sharedPreferences.getString("default_currency", "base");
                            URL = "https://api.fixer.io/latest?base=";
                            selectedcurrency = default_cu;
                            URL = URL + selectedcurrency;
                            load();


                        }
                    })
                    .show();
            ;

        }


        return super.onOptionsItemSelected(item);
    }

    private void load() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        final String formattedDate = df.format(c.getTime());


        progressDialog.setMessage("Fetching Data");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject myJson = new JSONObject(response);
                            String base = myJson.optString("base");
                            String r = myJson.optString("rates");
                            JSONObject rates = new JSONObject(r);


                            try {
                                db.updatecurrency(new Currency("AUD", rates.optString("AUD"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("BGN", rates.optString("BGN"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("BRL", rates.optString("BRL"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("CAD", rates.optString("CAD"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("CHF", rates.optString("CHF"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("CNY", rates.optString("CNY"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("CZK", rates.optString("CZK"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("DKK", rates.optString("DKK"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("GBP", rates.optString("GBP"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("HKD", rates.optString("HKD"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("HRK", rates.optString("HRK"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("HUF", rates.optString("HUF"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("IDR", rates.optString("IDR"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("ILS", rates.optString("ILS"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("INR", rates.optString("INR"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("JPY", rates.optString("JPY"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("KRW", rates.optString("KRW"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("MXN", rates.optString("MXN"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("MYR", rates.optString("MYR"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("NOK", rates.optString("NOK"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("NZD", rates.optString("NZD"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("PHP", rates.optString("PHP"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("PLN", rates.optString("PLN"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("RON", rates.optString("RON"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("RUB", rates.optString("RUB"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("SEK", rates.optString("SEK"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("SGD", rates.optString("SGD"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("THB", rates.optString("THB"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("TRY", rates.optString("TRY"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("USD", rates.optString("USD"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("ZAR", rates.optString("ZAR"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency("EUR", rates.optString("EUR"), base, formattedDate));
                            } catch (Exception ex) {
                            }


                            try {
                                db.updatecurrency(new Currency(base, "1", base, formattedDate));
                            } catch (Exception ex) {
                            }

                            progressDialog.hide();

                            adapter = new HomeAdapter(getSupportFragmentManager(), Titles, Numboftabs);
                            // Assigning ViewPager View and setting the adapter
                            pager = (ViewPager) findViewById(R.id.pager);
                            pager.setAdapter(adapter);
                            pager.setOffscreenPageLimit(7);

                            // Assiging the Sliding Tab Layout View
                            tabs = (SlidingTabLayout) findViewById(R.id.tabs);
                            tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

                            // Setting Custom Color for the Scroll bar indicator of the Tab View
                            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                                @Override
                                public int getIndicatorColor(int position) {
                                    return getResources().getColor(R.color.white);
                                }
                            });

                            tabs.setDistributeEvenly(false);// remove multiple lines in tab
                            // Setting the ViewPager For the SlidingTabsLayout
                            tabs.setViewPager(pager);


                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            //datetime.setText(e.getMessage());
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        finally {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Toast.makeText(getApplicationContext(), "There is some error in the API. Sit back and relax until we fix it!", Toast.LENGTH_LONG).show();
                        }
                    }
                }

        );
        mRequestQueue.add(stringRequest);
    }


    public void loadata() {

        db.add_currency(new Currency(1, "", "AUD", "$", "Australian dollar", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(2, "", "BGN", "Лв", "Bulgarian lev", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(3, "", "BRL", "R$", "Brazilian real", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(4, "", "CAD", "$", "Canadian dollar", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(5, "", "CHF", "Fr.", "Swiss franc", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(6, "", "CNY", "¥", "Chinese Yuan Renminbi", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(7, "", "CZK", "Kč", "Czech koruna", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(8, "", "DKK", "kr.", "Danish krone", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(9, "", "GBP", "£", "Great Britain Pound", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(10, "", "HKD", "HK$", "Hong Kong dollar", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(11, "", "HRK", "kn", "Croatian kuna", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(12, "", "HUF", "Ft", "Hungarian forint", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(13, "", "IDR", "Rp", "Indonesian rupiah", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(14, "", "ILS", "₪", "Israeli new shekel", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(15, "", "INR", "₹", "Indian rupee", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(16, "", "JPY", "¥", "Japanese yen", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(17, "", "KRW", "₩", "South Korean won", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(18, "", "MXN", "$", "Mexican peso", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(19, "", "MYR", "RM", "Malaysian ringgit", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(20, "", "NOK", "kr", "Norwegian krone", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(21, "", "NZD", "$", "New Zealand dollar", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(22, "", "PHP", "₱", "Philippine peso", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(23, "", "PLN", "zł", "Polish złoty", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(24, "", "RON", "lei", "Romanian leu", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(25, "", "RUB", "₽", "Russian ruble", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(26, "", "SEK", "kr", "Swedish krona", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(27, "", "SGD", "$", "Singapore dollar", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(28, "", "THB", "฿", "Thai baht", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(29, "", "TRY", "TRY", "Turkish lira", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(30, "", "USD", "$", "United States Dollar", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(31, "", "ZAR", "R", "South African rand", "0", "EUR", "2018-01-23 00:00:00"));
        db.add_currency(new Currency(32, "", "EUR", "€", "Euro", "0", "EUR", "2018-01-23 00:00:00"));
        ;

    }


}
