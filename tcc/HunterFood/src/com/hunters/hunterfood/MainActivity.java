package com.hunters.hunterfood;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

//MainActivity herda de ActivityGroup, porque voc� est� lidando
//com v�rias atividades em um elemento s�. Ou seja, o TabHost hospeda um grupo de atividades.
public class MainActivity extends ActivityGroup {
	static TabHost tabHost;
	static int tab = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Resources res = getResources();
		tabHost = (TabHost)findViewById(R.id.tabhost);
		tabHost.setup(this.getLocalActivityManager());
		TabHost.TabSpec spec;
		Intent intent;
		
		//Cada Tab adicionada tem sua pr�pria Activity, que por sua vez , tem seu pr�prio .xml(layout) e //tamb�m tem um elemento Drawable. Esse elemento eu usei para mudar o �cone da Tab.
		//Quando ela n�o est� ativa, o �cone � cinza. Quando ela est� ativa, o �cone fica colorido e indica //pro usu�rio que ele est� naquela Tab.
		
		//Adiciona Tab #1
		intent = new Intent().setClass(this, HomeActivity.class);
		spec = tabHost.newTabSpec("0").setIndicator("Home").setContent(intent);
		tabHost.addTab(spec);
			
		//Adiciona Tab #2
		intent = new Intent().setClass(this, RestauranteActivity.class);
		spec = tabHost.newTabSpec("1").setIndicator("Minhas Buscas").setContent(intent);
		tabHost.addTab(spec);
		
		//Adiciona Tab #3
		intent = new Intent().setClass(this, CurtirActivity.class);
		spec = tabHost.newTabSpec("2").setIndicator("Curtir").setContent(intent);
		tabHost.addTab(spec);
		
		//essa ultima linha indica qual tab ser� carregada ao iniciar essa activity. No nosso caso, a Primeira!!!
		
		tabHost.setCurrentTab(0);
	
	}

}
