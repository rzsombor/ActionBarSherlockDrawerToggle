package hu.scythe.actionbarsherlockdrawertoggledemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;

import com.actionbarsherlock.app.ActionBarSherlockDrawerToggle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity {

	private DrawerLayout drawerLayout;
	private ActionBarSherlockDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

		drawerToggle = new ActionBarSherlockDrawerToggle(this, drawerLayout,
				R.drawable.ic_navigation_drawer, R.string.app_name,
				R.string.app_name);
		drawerToggle.setDrawerIndicatorEnabled(true);

		drawerLayout.setDrawerListener(new DrawerListener() {
			@Override
			public void onDrawerStateChanged(int newState) {
				drawerToggle.onDrawerStateChanged(newState);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				drawerToggle.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				drawerToggle.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				drawerToggle.onDrawerClosed(drawerView);
			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_git:
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse("https://github.com/rzsombor/ActionBarSherlockDrawerToggle"));
			startActivity(i);
			return true;
		case R.id.menu_what:
			WhatDialog d = new WhatDialog();
			d.show(getSupportFragmentManager(), WhatDialog.TAG);
			return true;
		default:
			return drawerToggle.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_main, menu);

		return true;
	}

}
