package com.helen.andbase.dao;


import android.content.Context;

import net.tsz.afinal.FinalDb;

public class HBaseDao {
	public static final String DB_NAME="team_db";
	protected FinalDb mDb;
	protected boolean isDebug = true;
	public HBaseDao(Context context){
		mDb = FinalDb.create(context, DB_NAME, isDebug);
	}
}
