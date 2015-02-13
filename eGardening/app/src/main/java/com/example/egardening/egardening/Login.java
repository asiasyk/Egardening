package com.example.egardening.egardening;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Login extends Activity implements OnClickListener {

    EditText et_pass, et_username;
    Button btn_login;
    String username, password;


    //HttpEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_login = (Button) findViewById(R.id.button);
        et_username = (EditText) findViewById(R.id.editText);
        et_pass = (EditText) findViewById(R.id.editText2);

        btn_login.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                new NetworkConnection().execute();
                username = et_username.getText().toString();
                password = et_pass.getText().toString();

            default:
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }







    ///Inner class

    class NetworkConnection extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(Login.this);
        InputStream is = null ;
        String result = "";
        HttpEntity entity = null;


        protected void onPreExecute() {
            progressDialog.setMessage("Connecting to server...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    NetworkConnection.this.cancel(true);
                }
            });
            //super.onPreExecute();
        }



        @Override
        protected Void doInBackground(String...params) {
            ArrayList<NameValuePair> nvps;
            HttpClient http_client = new DefaultHttpClient(); //form container
            HttpPost http_post = new HttpPost("http://egarden-pl.byethost17.com/egardening/login/index.php");

            try {

                nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("username", username));
                nvps.add(new BasicNameValuePair("password", password));

                System.out.println(username+" "+password);

                http_post.setEntity(new UrlEncodedFormEntity(nvps));
                HttpResponse response = http_client.execute(http_post);

                //if(response.getStatusLine().getStatusCode() == 200) {
                    entity = response.getEntity();
                    is = entity.getContent();

                //}





            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error converting result " + e.toString());
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void result) {

            //bind data in lisview or any other componet
            //super.onPostExecute(result);
            /*try {
                JSONArray Jarray = new JSONArray(result);
                for(int i=0;i<Jarray.length();i++)
                {
                    JSONObject Jasonobject = null;



                    Jasonobject = Jarray.getJSONObject(i);

                    //get an output on the screen
                    String usr = Jasonobject.getString("username");
                    String pwd = Jasonobject.getString("password");



                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data "+e.toString());
            }*/
            try {

                    //is = entity.getContent();

                    JSONObject json_response = new JSONObject(convertStreamToString(is));
                    String retrieved_usr = json_response.getString("username"); //the name of the field in the table in the DB
                    String retrieved_psd = json_response.getString("password"); //the name of the field in the table in the DB

                    //Validate login
                    if((username.equals(retrieved_usr)) && (password.equals(retrieved_psd))) {

                        //this is the session object
                        SharedPreferences sp = getSharedPreferences("SessionUser", 0);
                        SharedPreferences.Editor speditor = sp.edit();
                        speditor.putString("USERNAME", username);
                        speditor.putString("PASSWORD", password); //Not really needed to store password in session
                        speditor.commit();

                        this.progressDialog.dismiss();
                        Toast.makeText(getBaseContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Main.class);
                        startActivity(intent);

                }

                else {
                    this.progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                }

                //this.progressDialog.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Log.e("log_tag", "Error parsing data "+e.toString());
                this.progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Connection Error!", Toast.LENGTH_SHORT).show();

            }




        }


        public  String convertStreamToString(InputStream is) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(sb);
            return sb.toString();
        }


    }




}



