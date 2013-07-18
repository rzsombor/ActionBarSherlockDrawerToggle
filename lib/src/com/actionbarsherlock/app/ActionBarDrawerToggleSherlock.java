package com.actionbarsherlock.app;

/* Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * This class encapsulates some awful hacks.
 * 
 * Before JB-MR2 (API 18) it was not possible to change the home-as-up indicator
 * glyph in an action bar without some really gross hacks. Since the MR2 SDK is
 * not published as of this writing, the new API is accessed via reflection here
 * if available.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class ActionBarDrawerToggleSherlock {
	private static final String TAG = "ActionBarDrawerToggleSherlock";

	private static final int[] THEME_ATTRS = new int[] { R.attr.homeAsUpIndicator };

	public static Object setActionBarUpIndicator(Object info,
			Activity activity, Drawable drawable, int contentDescRes) {
		
		if (info == null) {
			info = new SetSherlockIndicatorInfo(activity);
		}
		
		final SetSherlockIndicatorInfo sii = (SetSherlockIndicatorInfo) info;
		
		if (sii.upIndicatorView != null) {
			sii.upIndicatorView.setImageDrawable(drawable);
		} else {
			Log.w(TAG, "Couldn't set home-as-up indicator");
		}
		return info;
	}

	public static Object setActionBarDescription(Object info,
			Activity activity, int contentDescRes) {
		// Content description is not supported

		if (info == null) {
			info = new SetSherlockIndicatorInfo(activity);
		}

		return info;
	}

	public static Drawable getThemeUpIndicator(Activity activity) {
		final TypedArray a = activity.obtainStyledAttributes(THEME_ATTRS);
		final Drawable result = a.getDrawable(0);
		a.recycle();
		return result;
	}

	private static class SetSherlockIndicatorInfo {
		public ImageView upIndicatorView;

		public SetSherlockIndicatorInfo(Activity activity) {
			// Get ActionBarSherlock up
			ViewGroup sherlockRoot = (ViewGroup) activity
					.findViewById(R.id.abs__action_bar_container);
			if (sherlockRoot != null) {
				walkViewGroupForSherlockUpView(sherlockRoot);
			}
		}

		/**
		 * Method to walk the View hierarchy and look for a visible
		 * ActionBarSherlock UP View. The problem is, that the View hierarchy of
		 * ActionBarSherlock 4.3.1 contains multiple Views with the id
		 * R.id.abs__up, so we look for the one that is actually visible to the
		 * user. This is a recursive method, ideally the root View
		 * ActionBarSherlock should be passed in as the parameter.
		 */
		private void walkViewGroupForSherlockUpView(ViewGroup group) {
			for (int i = 0; i < group.getChildCount(); i++) {
				View current = group.getChildAt(i);

				if (current.getId() == R.id.abs__up
						&& current.getVisibility() == View.VISIBLE) {
					upIndicatorView = (ImageView) current;
				}

				if (current instanceof ViewGroup) {
					walkViewGroupForSherlockUpView((ViewGroup) current);
				}
			}
		}
	}
}