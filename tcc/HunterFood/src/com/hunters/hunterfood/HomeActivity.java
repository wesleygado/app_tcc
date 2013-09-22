package com.hunters.hunterfood;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends Activity {
	
	private ListView lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		lista = (ListView) findViewById(R.id.lista_restaurantes);
		
		new RestaurantesTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public String getRESTFileContent(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		try {
			HttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = new String(getBytes(instream));

				instream.close();
				return result;
			}
		} catch (Exception e) {
			Log.e("NGVL", "Falha ao acessar Web service", e);
		}
		return null;
	}
	
	public byte[] getBytes(InputStream is) {
		try {
			int bytesLidos;
			ByteArrayOutputStream bigBuffer = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];

			while ((bytesLidos = is.read(buffer)) > 0) {
				bigBuffer.write(buffer, 0, bytesLidos);
			}

			return bigBuffer.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private class RestaurantesTask extends AsyncTask<String, Void, String[]> {

		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(HomeActivity.this);
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			if(result != null){
					ArrayAdapter<String> adapter =	new ArrayAdapter<String>(getBaseContext(),	android.R.layout.simple_list_item_1, result);
					lista.setAdapter(adapter);
			}
			dialog.dismiss();
		}
		
		@Override
		protected String[] doInBackground(String... params) { 
			try {
				
				String urlTwitter = "http://192.168.43.113:8080/WebService/restaurante/buscarTodos";
				String url = Uri.parse(urlTwitter).toString();
				String conteudo = getRESTFileContent(url);
				
				
				// pegamos o resultado
				JSONObject jsonObject = new JSONObject(conteudo);
				JSONArray resultados = jsonObject.getJSONArray("restaurante");
				
				// montamos o resultado
				String[] tweets = new String[resultados.length()];
				
				for (int i = 0; i < resultados.length(); i++) {
					
					JSONObject tweet = resultados.getJSONObject(i);
					String nome = tweet.getString("nome");
					String endereco = tweet.getString("endereco");
					String cidade = tweet.getString("cidade");
					tweets[i] = nome + " - " + endereco + "-" + cidade;
					
				}
				
				return tweets;
				
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
		}
	
	}

}
