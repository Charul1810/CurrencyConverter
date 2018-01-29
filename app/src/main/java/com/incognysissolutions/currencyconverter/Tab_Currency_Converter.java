package com.incognysissolutions.currencyconverter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.Console;
import java.nio.channels.FileLock;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Tab_Currency_Converter extends Fragment {
    private String TAG = MainActivity.class.getSimpleName();
    Button button_convert;
    List<Currency> mylist;
    EditText from, select_currency;
    TextView result;
    TextView first_country_name, hidden,hidden_value;
    Float first, resu, myfloat;
    DatabaseHandler db;
    Adapter adapter;
    ImageView first_country, second_country;
    SharedPreferences sharedPreferences;
    private static String URL = "https://api.fixer.io/latest?base=";
    String selectedcurrency = "USD";
    String s, res;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tab__currency__converter, container, false);
        setHasOptionsMenu(true);
        // first_country_editext=(EditText) v.findViewById(R.id.first_country_edittext);
        button_convert = (Button) v.findViewById(R.id.button_convert);
        from = (EditText) v.findViewById(R.id.first);
        result = (TextView) v.findViewById(R.id.text_result);
        db = new DatabaseHandler(getContext());
        hidden = (TextView) v.findViewById(R.id.hidden_result);
        select_currency = (EditText) v.findViewById(R.id.select_currency);
        first_country = (ImageView) v.findViewById(R.id.first_country_image);
        second_country = (ImageView) v.findViewById(R.id.second_country_flag);
        first_country_name = (TextView) v.findViewById(R.id.first_country_name);
        fab=(FloatingActionButton) v.findViewById(R.id.float_button);

        sharedPreferences = getActivity().getSharedPreferences("default_currency", Context.MODE_PRIVATE);


        String default_currency = sharedPreferences.getString("default_currency", "USD");
        URL = URL + default_currency;
        selectedcurrency = default_currency.trim();

        if (selectedcurrency.equals("USD")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.usd));
            first_country_name.setText("United States Dollar");
        } else if (selectedcurrency.equals("AUD")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.aud));
            first_country_name.setText("Australian dollar");
        } else if (selectedcurrency.equals("BGN")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.bgn));
            first_country_name.setText("Bulgarian lev");
        } else if (selectedcurrency.equals("BRL")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.brl));
            first_country_name.setText("Brazilian real");
        } else if (selectedcurrency.equals("CAD")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.cad));
            first_country_name.setText("Canadian dollar");
        } else if (selectedcurrency.equals("CHF")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.chf));
            first_country_name.setText("Swiss franc");
        } else if (selectedcurrency.equals("CNY")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.cny));
            first_country_name.setText("Chinese Yuan Renminbi");
        } else if (selectedcurrency.equals("CZK")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.czk));
            first_country_name.setText("Czech koruna");
        } else if (selectedcurrency.equals("DKK")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.dkk));
            first_country_name.setText("Danish krone");
        } else if (selectedcurrency.equals("GBP")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.gbp));
            first_country_name.setText("British Pound");
        } else if (selectedcurrency.equals("HKD")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.hkd));
            first_country_name.setText("Hong Kong dollar");
        } else if (selectedcurrency.equals("HRK")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.hrk));
            first_country_name.setText("Croatian kuna");
        } else if (selectedcurrency.equals("HUF")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.huf));
            first_country_name.setText("Hungarian forint");
        } else if (selectedcurrency.equals("IDR")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.idr));
            first_country_name.setText("Indonesian rupiah");
        } else if (selectedcurrency.equals("ILS")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.ils));
            first_country_name.setText("Israeli new shekel");
        } else if (selectedcurrency.equals("INR")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.flag_of_india));
            first_country_name.setText("Indian rupee");
        } else if (selectedcurrency.equals("JPY")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.jpy));
            first_country_name.setText("Japanese yen");
        } else if (selectedcurrency.equals("KRW")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.krw));
            first_country_name.setText("South Korean won");
        } else if (selectedcurrency.equals("MXN")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.mxn));
            first_country_name.setText("Mexican peso");
        } else if (selectedcurrency.equals("MYR")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.myr));
            first_country_name.setText("Malaysian ringgit");
        } else if (selectedcurrency.equals("NOK")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.nok));
            first_country_name.setText("Norwegian krone");
        } else if (selectedcurrency.equals("NZD")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.nzd));
            first_country_name.setText("New Zealand dollar");
        } else if (selectedcurrency.equals("PHP")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.php));
            first_country_name.setText("Philippine peso");
        } else if (selectedcurrency.equals("PLN")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.pln));
            first_country_name.setText("Polish złoty");
        } else if (selectedcurrency.equals("RON")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.ron));
            first_country_name.setText("Romanian leu");
        } else if (selectedcurrency.equals("RUB")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.rub));
            first_country_name.setText("Russian ruble");
        } else if (selectedcurrency.equals("SEK")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.ske));
            first_country_name.setText("Swedish krona");
        } else if (selectedcurrency.equals("SGD")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.sgd));
            first_country_name.setText("Singapore dollar");
        } else if (selectedcurrency.equals("THB")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.thb));
            first_country_name.setText("Thai baht");
        } else if (selectedcurrency.equals("TRY")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.tur));
            first_country_name.setText("Turkish lira");
        } else if (selectedcurrency.equals("USD")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.usd));
            first_country_name.setText("United States Dollar");
        } else if (selectedcurrency.equals("ZAR")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.zar));
            first_country_name.setText("South African rand");
        } else if (selectedcurrency.equals("EUR")) {
            first_country.setImageDrawable(getResources().getDrawable(R.drawable.eur));
            first_country_name.setText("Euro");
        }


        select_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title("Select Currency")
                        .items(R.array.default_curr)
                        .itemsCallback(new MaterialDialog.ListCallback() {

                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                first = Float.parseFloat(from.getText().toString());
                                if (which == 0) {
                                    s = db.getCurrency(1);
                                    select_currency.setText("Australian Dollar");
                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.usd));
                                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                                }

                                if (which == 1) {
                                    s = db.getCurrency(2);
                                    select_currency.setText("Bulgarian lev");
                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.bgn));
                                }

                                if (which == 2) {
                                    s = db.getCurrency(3);
                                    select_currency.setText("Brazilian real");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.brl));
                                }


                                if (which == 3) {
                                    s = db.getCurrency(4);
                                    select_currency.setText("Canadian dollar");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.cad));

                                }

                                if (which == 4) {
                                    s = db.getCurrency(5);
                                    select_currency.setText("Swiss franc");
                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.chf));

                                }


                                if (which == 5) {
                                    s = db.getCurrency(6);
                                    select_currency.setText("Chinese Yuan Renminbi");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.cny));

                                }


                                if (which == 6) {
                                    s = db.getCurrency(7);
                                    select_currency.setText("Czech koruna");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.czk));

                                }


                                if (which == 7) {
                                    s = db.getCurrency(8);
                                    select_currency.setText("Danish krone");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.dkk));

                                }


                                if (which == 8) {
                                    s = db.getCurrency(9);
                                    select_currency.setText("Great Britain Pound");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.gbp));

                                }


                                if (which == 9) {
                                    s = db.getCurrency(10);
                                    select_currency.setText("Hong Kong dollar");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.hkd));
                                }


                                if (which == 10) {
                                    s = db.getCurrency(11);
                                    select_currency.setText("Croatian kuna");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.hrk));

                                }


                                if (which == 11) {
                                    s = db.getCurrency(12);
                                    select_currency.setText("Hungarian forint");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.huf));
                                }

                                if (which == 12) {
                                    s = db.getCurrency(13);
                                    select_currency.setText("Indonesian rupiah");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.idr));
                                }

                                if (which == 13) {
                                    s = db.getCurrency(14);
                                    select_currency.setText("Israeli new shekel");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.ils));
                                }

                                if (which == 14) {
                                    s = db.getCurrency(15);
                                    select_currency.setText("Indian rupee");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.flag_of_india));
                                }

                                if (which == 15) {
                                    s = db.getCurrency(16);
                                    select_currency.setText("Japanese yen");
                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.jpy));
                                }

                                if (which == 16) {
                                    s = db.getCurrency(17);
                                    select_currency.setText("South Korean won");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.krw));
                                }

                                if (which == 17) {
                                    s = db.getCurrency(18);
                                    select_currency.setText("Mexican peso");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.mxn));
                                }

                                if (which == 18) {
                                    s = db.getCurrency(19);
                                    select_currency.setText("Malaysian ringgit");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.myr));
                                }

                                if (which == 19) {
                                    s = db.getCurrency(20);
                                    select_currency.setText("Norwegian krone");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.nok));
                                }

                                if (which == 20) {
                                    s = db.getCurrency(21);
                                    select_currency.setText("New Zealand dollar");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.usd));
                                }

                                if (which == 21) {
                                    s = db.getCurrency(22);
                                    select_currency.setText("Philippine peso");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.php));
                                }

                                if (which == 22) {
                                    s = db.getCurrency(23);
                                    select_currency.setText("Polish złoty");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.pln));
                                }

                                if (which == 23) {
                                    s = db.getCurrency(24);
                                    select_currency.setText("Romanian leu");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.ron));
                                }

                                if (which == 24) {
                                    s = db.getCurrency(25);
                                    select_currency.setText("Russian ruble");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.rub));
                                }

                                if (which == 25) {
                                    s = db.getCurrency(26);
                                    select_currency.setText("Swedish krona");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.ske));
                                }

                                if (which == 26) {
                                    s = db.getCurrency(27);
                                    select_currency.setText("Singapore dollar");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.sgd));
                                }

                                if (which == 27) {
                                    s = db.getCurrency(28);
                                    select_currency.setText("Thai baht");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.thb));
                                }

                                if (which == 28) {
                                    s = db.getCurrency(29);
                                    select_currency.setText("Turkish lira");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.tur));
                                }

                                if (which == 29) {
                                    s = db.getCurrency(30);
                                    select_currency.setText("United States Dollar");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.usd));
                                }


                                if (which == 30) {
                                    s = db.getCurrency(31);
                                    select_currency.setText("South African rand");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.zar));
                                }


                                if (which == 31) {
                                    s = db.getCurrency(32);
                                    select_currency.setText("Euro");

                                    second_country.setImageDrawable(getResources().getDrawable(R.drawable.eur));
                                }


                                hidden.setText(s);


                                myfloat = Float.parseFloat(s);
                                resu = first * myfloat;
                                result.setText(String.valueOf(resu));


                            }
                        })
                        .show();
                ;

            }
        });

        button_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_currency.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Please Select Currency for Conversion",Toast.LENGTH_LONG).show();

                }else{
                    myfloat = Float.parseFloat(hidden.getText().toString());
                first = Float.parseFloat(from.getText().toString());
                resu = first * myfloat;
                result.setText(String.valueOf(resu));

                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp,temp_value,temp_result;
                Drawable temp_img;
               // temp_value=from.getText().toString();
                from.setText("1");
                result.setText("");


                temp=first_country_name.getText().toString();
                temp_img=first_country.getDrawable();
                first_country.setImageDrawable(second_country.getDrawable());
                first_country_name.setText(select_currency.getText().toString());
                select_currency.setText("");
                select_currency.setText(temp);

               // result.setText(selectedcurrency);


                second_country.setImageDrawable(temp_img);
            }
        });

        return v;


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_currency) {


        }


        return super.onOptionsItemSelected(item);
    }
}
