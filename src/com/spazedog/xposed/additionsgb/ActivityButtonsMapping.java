package com.spazedog.xposed.additionsgb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class ActivityButtonsMapping extends PreferenceActivity {
	
	protected String[] mButtons = new String[]{"power", "home", "menu", "back", "search", "volup", "voldown"};
	protected String[] mNames = new String[]{"Power", "Home", "Menu", "Back", "Search", "Volume Up", "Volume Down"};
	
	protected String mMapType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PreferenceManager prefMgr = getPreferenceManager();
		prefMgr.setSharedPreferencesName(Common.HOOK_PREFERENCES);
		prefMgr.setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
		
		mMapType = getIntent().getStringExtra("mapType");
		
		setTitle(getIntent().getStringExtra("title"));
		
		addPreferencesFromResource(R.xml.activity_buttons_mapping);
		
		PreferenceScreen preferenceScreen = getPreferenceScreen();
		
		for (int i=0; i < mButtons.length; i++) {
			Preference preference = new Preference(this);
			Intent intent = new Intent();
			
			intent.addCategory(Intent.ACTION_VIEW);
			intent.setClass(this, ActivityButtonMap.class);
			intent.putExtra("mapType", mMapType);
			intent.putExtra("title", (mMapType.equals("on") ? "Awake: " : "Sleep: ") + mNames[i]);
			intent.putExtra("key", mButtons[i]);
			
			preference.setKey(mButtons[i]);
			preference.setTitle(mNames[i]);
			preference.setIntent(intent);
			
			preferenceScreen.addPreference(preference);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		for (int i=0; i < mButtons.length; i++) {
			String button = "btn_" + mMapType + "_" + mButtons[i] + "_mapped";
			Preference preference = getPreferenceScreen().findPreference(mButtons[i]);
			
			if (getPreferenceManager().getSharedPreferences().getBoolean(button, false)) {
				preference.setSummary("Re-map is on");
			}
		}
	}
}
