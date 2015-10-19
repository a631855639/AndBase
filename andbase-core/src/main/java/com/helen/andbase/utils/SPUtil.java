package com.helen.andbase.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 李晓伟 on 2015/7/23.
 * SharedPreferences Util
 */
@SuppressWarnings("unused")
public class SPUtil {
    private static SPUtil INSTANCE;
    private SharedPreferences mSharedPre;
    private SharedPreferences.Editor mEditor;

    private static String mKeyPrefix;

    private SPUtil (Context context){
        mSharedPre = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        mEditor = mSharedPre.edit();
    }


    public static SPUtil getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SPUtil.class){
                if(INSTANCE == null){
                    INSTANCE = new SPUtil(context);
                }
            }
        }
        if(TextUtils.isEmpty(mKeyPrefix)){
            mKeyPrefix = "_";
        }else {
            mKeyPrefix +="_";
        }
        return INSTANCE;
    }

    private String appendPrefix(String key,boolean hasPrefix){
        if(hasPrefix) key = mKeyPrefix + key;
        return key;
    }

    /**put、get String start*/
    private SPUtil putString(String key, String value,boolean hasPrefix){
        mEditor.putString(appendPrefix(key,hasPrefix), value);
        return this;
    }

    public SPUtil putString(String key,@NonNull String value){
        return putString(key, value, true);
    }

    public SPUtil putStringWithoutPrefix(String key,@NonNull String value){
        return putString(key, value, false);
    }

    private String getString(String key , String defValue , boolean hasPrefix){
        return mSharedPre.getString(appendPrefix(key, hasPrefix), defValue);
    }

    public String getString(String key,String defValue){
        return getString(key, defValue, true);
    }

    public String getString(String key){
        return getString(key, "");
    }

    public String getStringWithoutPrefix(String key ,String defValue){
        return getString(key, defValue, false);
    }

    public String getStringWithoutPrefix(String key){
        return getStringWithoutPrefix(key, "");
    }
    /**put、get String end*/

    /**put、get Int start*/
    private SPUtil putInt(String key,int value,boolean hasPrefix){
        mEditor.putInt(appendPrefix(key, hasPrefix), value);
        return this;
    }

    public SPUtil putInt(String key,int value){
        return putInt(key, value, true);
    }

    public SPUtil putIntWithoutPrefix(String key,int value){
        return putInt(key, value, false);
    }

    private int getInt(String key , int defValue , boolean hasPrefix){
        return mSharedPre.getInt(appendPrefix(key, hasPrefix), defValue);
    }

    public int getInt(String key){
        return getInt(key, 0);
    }

    public int getInt(String key ,int defValue){
        return getInt(key, defValue, true);
    }

    public int getIntWithoutPrefix(String key ,int defValue){
        return getInt(key, defValue, false);
    }

    public int getIntWithoutPrefix(String key){
        return getIntWithoutPrefix(key, 0);
    }
    /**put、get Int end*/

    /**put、get Boolean start*/
    private SPUtil putBoolean(String key,boolean value,boolean hasPrefix){
        mEditor.putBoolean(appendPrefix(key, hasPrefix), value);
        return this;
    }

    public SPUtil putBoolean(String key,boolean value){
        return putBoolean(key, value, true);
    }

    public SPUtil putBooleanWithoutPrefix(String key,boolean value){
        return putBoolean(key, value, false);
    }

    private boolean getBoolean(String key , boolean defValue , boolean hasPrefix){
        return mSharedPre.getBoolean(appendPrefix(key, hasPrefix), defValue);
    }

    public boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key , boolean defValue){
        return getBoolean(key, defValue, true);
    }

    public boolean getBooleanWithoutPrefix(String key , boolean defValue){
        return getBoolean(key, defValue, false);
    }

    public boolean getBooleanWithoutPrefix(String key){
        return getBooleanWithoutPrefix(key, false);
    }
    /**put、get Boolean end*/

    /**put、get Long start*/
    private SPUtil putLong(String key,long value,boolean hasPrefix){
        mEditor.putLong(appendPrefix(key, hasPrefix), value);
        return this;
    }

    public SPUtil putLong(String key,long value){
        return putLong(key, value, true);
    }

    public SPUtil putLongWithoutPrefix(String key,long value){
        return putLong(key, value, false);
    }

    private long getLong(String key , long defValue , boolean hasPrefix){
        return mSharedPre.getLong(appendPrefix(key, hasPrefix), defValue);
    }

    public long getLong(String key){
        return getLong(key, 0);
    }

    public long getLong(String key ,long defValue){
        return getLong(key, defValue, true);
    }

    public long getLongWithoutPrefix(String key ,long defValue){
        return getLong(key, defValue, false);
    }

    public long getLongWithoutPrefix(String key){
        return getLongWithoutPrefix(key, 0);
    }
    /**put、get Long end*/

    /**put、get Float start*/
    private SPUtil putFloat(String key,float value,boolean hasPrefix){
        mEditor.putFloat(appendPrefix(key, hasPrefix), value);
        return this;
    }

    public SPUtil putFloat(String key,float value){
        return putFloat(key, value, true);
    }

    public SPUtil putFloatWithoutPrefix(String key,float value){
        return putFloat(key, value, false);
    }

    private float getFloat(String key , float defValue , boolean hasPrefix){
        return mSharedPre.getFloat(appendPrefix(key, hasPrefix), defValue);
    }

    public float getFloat(String key){
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key , float defValue){
        return getFloat(key, defValue, true);
    }

    public float getFloatWithoutPrefix(String key){
        return getFloatWithoutPrefix(key, 0.0f);
    }
    public float getFloatWithoutPrefix(String key , float defValue){
        return getFloat(key, defValue, false);
    }
    /**put、get Float end*/

    /**put、get StringSet start*/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private SPUtil putStringSet(String key,Set<String> value,boolean hasPrefix){
        mEditor.putStringSet(appendPrefix(key, hasPrefix),value);
        return this;
    }

    public SPUtil putStringSet(String key,@NonNull Set<String> value){
        return putStringSet(key, value, true);
    }

    public SPUtil putStringSetWithoutPrefix(String key,@NonNull Set<String> value){
        return putStringSet(key, value, false);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private Set<String> getStringSet(String key , Set<String> defValue , boolean hasPrefix){
        return mSharedPre.getStringSet(appendPrefix(key, hasPrefix), defValue);
    }

    public Set<String> getStringSet(String key , Set<String> defValue){
        return getStringSet(key, defValue, true);
    }

    public Set<String> getStringSet(String key){
        return getStringSet(key, null);
    }

    public Set<String> getStringSetWithoutPrefix(String key , Set<String> defValue){
        return getStringSet(key, defValue, false);
    }

    public Set<String> getStringSetWithoutPrefix(String key){
        return getStringSetWithoutPrefix(key, null);
    }
    /**put、get StringSet end*/

    /**put、get JSONObject start*/
    private SPUtil putJSONObject(String key,JSONObject value,boolean hasPrefix){
        return putString(key, value.toString(), hasPrefix);
    }

    public SPUtil putJSONObject(String key,@NonNull JSONObject obj){
        return putJSONObject(key, obj, true);
    }

    public SPUtil putJSONObjectWithoutPrefix(String key,@NonNull JSONObject obj){
        return putJSONObject(key, obj, false);
    }

    private JSONObject getJSONObject(String key,JSONObject defValue,boolean hasPrefix){
        String jsonStr = getString(key,"",hasPrefix);
        JSONObject obj;
        try {
            obj = new JSONObject(jsonStr);
        } catch (Exception e) {
            obj =defValue;
        }
        return obj;
    }

    public JSONObject getJSONObject(String key,JSONObject defValue){
        return getJSONObject(key, defValue, true);
    }

    public JSONObject getJSONObject(String key){
        return getJSONObject(key, null);
    }

    public JSONObject getJSONObjectWithoutPrefix(String key,JSONObject defValue){
        return getJSONObject(key, defValue, false);
    }
    public JSONObject getJSONObjectWithoutPrefix(String key){
        return getJSONObjectWithoutPrefix(key, null);
    }
    /**put、get JSONObject end*/

    /**put、get JSONArray start*/
    private SPUtil putJSONArray(String key,JSONArray value,boolean hasPrefix){
        return putString(key, value.toString(), hasPrefix);
    }

    public SPUtil putJSONArray(String key,@NonNull JSONArray value){
        return putJSONArray(key, value, true);
    }

    public SPUtil putJSONArrayWithoutPrefix(String key,@NonNull JSONArray value){
        return putJSONArray(key, value, false);
    }

    private JSONArray getJSONArray(String key,JSONArray defValue,boolean hasPrefix){
        String jsonStr = getString(key,"",hasPrefix);
        JSONArray obj;
        try {
            obj = new JSONArray(jsonStr);
        } catch (Exception e) {
            obj = defValue;
        }
        return obj;
    }

    public JSONArray getJSONArray(String key,JSONArray defValue){
        return getJSONArray(key, defValue, true);
    }

    public JSONArray getJSONArray(String key){
        return getJSONArray(key, null);
    }

    public JSONArray getJSONArrayWithoutPrefix(String key,JSONArray defValue){
        return getJSONArray(key, defValue, false);
    }

    public JSONArray getJSONArrayWithoutPrefix(String key){
        return getJSONArrayWithoutPrefix(key, null);
    }
    /**put、get JSONArray end*/

    /**put、get StringList start*/
    private SPUtil putStringList(String key , @NonNull List<String> value , boolean hasPrefix){
        StringBuilder sb = new StringBuilder();
        for(String str : value){
            sb.append(str).append(",");
        }
        sb.delete(sb.length()-1,sb.length());
        return putString(key,sb.toString(),hasPrefix);
    }

    public SPUtil putStringList(String key , @NonNull List<String> value){
        return putStringList(key, value, true);
    }

    public SPUtil putStringListWithoutPrefix(String key ,@NonNull List<String> value){
        return putStringList(key, value, false);
    }

    private List<String> getStringList(String key , List<String> defValue , boolean hasPrefix){
        List<String> obj;
        try {
            String str = getString(key,"",hasPrefix);
            obj = Arrays.asList(str.split(","));
        }catch (Exception e){
            obj = defValue;
        }
        return obj;
    }

    public List<String> getStringList(String key , List<String> defValue){
        return getStringList(key, defValue, true);
    }

    public List<String> getStringList(String key){
        return getStringList(key,null);
    }

    public List<String> getStringListWithoutPrefix(String key , List<String> defValue){
        return getStringList(key, defValue, false);
    }

    public List<String> getStringListWithoutPrefix(String key){
        return getStringListWithoutPrefix(key, null);
    }
    /**put、get StringList end*/

    /**put、get StringArray start*/
    private SPUtil putStringArray(String key , String[] value , boolean hasPrefix){
        StringBuilder sb = new StringBuilder();
        for(String str : value){
            sb.append(str).append(",");
        }
        sb.delete(sb.length()-1,sb.length());
        return putString(key, sb.toString(), hasPrefix);
    }

    public SPUtil putStringArray(String key ,@NonNull String[] value){
        return putStringArray(key, value, true);
    }

    public SPUtil putStringArrayWithoutPrefix(String key ,@NonNull String[] value){
        return putStringArray(key, value, false);
    }

    private String[] getStringArray(String key , String[] defValue ,boolean hasPrefix){
        String[] obj;
        try {
            String str = getString(key,"",hasPrefix);
            obj = str.split(",");
        }catch (Exception e){
            obj = defValue;
        }
        return obj;
    }

    public String[] getStringArray(String key ,String[] defValue){
        return  getStringArray(key, defValue, true);
    }

    public String[] getStringArray(String key){
        return  getStringArray(key,null);
    }

    public String[] getStringArrayWithoutPrefix(String key ,String[] defValue){
        return  getStringArray(key,defValue,false);
    }

    public String[] getStringArrayWithoutPrefix(String key){
        return  getStringArrayWithoutPrefix(key, null);
    }
    /**put、get StringArray end*/

    /**others*/
    public SPUtil remove(String key){
        mEditor.remove(appendPrefix(key,true));
        return this;
    }

    public SPUtil removeWithoutPrefix(String key){
        mEditor.remove(appendPrefix(key,false));
        return this;
    }

    public boolean contains(String key){
        return mSharedPre.contains(appendPrefix(key,true));
    }

    public boolean containsWithoutPrefix(String key){
        return mSharedPre.contains(appendPrefix(key, false));
    }

    public Map<String,?> getAll(){
        return mSharedPre.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        mSharedPre.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        mSharedPre.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public boolean commit(){
        return mEditor.commit();
    }

    public void apply(){
        mEditor.apply();
    }

    public void clear(){
        mEditor.clear().commit();
    }
}
