package com.helen.andbase;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.helen.andbase.utils.SPUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private SPUtil mSPUtil;
    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSPUtil() throws JSONException {
        mSPUtil = SPUtil.getInstance(getContext());
        String a = "a";
        int b = 1;
        boolean c = true;
        long d = 2;
        float e =3.5f;
        JSONObject f = new JSONObject("{\"name\":\"li\"}");
        JSONArray g = new JSONArray("[{\"name\":\"zhang\"},{\"name\":\"DJ\"}]");
        List<String> h = new ArrayList<>(3);
        h.add("b");
        h.add("c");
        h.add("d");
        String[] i = new String[]{"e","f"};
        Set<String> s = new HashSet<>(2);
        s.add("s");
        s.add("sss");
        mSPUtil.putString("a", a)
                .putInt("b", b)
                .putBoolean("c", c)
                .putLong("d", d)
                .putFloat("e", e)
                .putJSONObject("f", f)
                .putJSONArray("g", g)
                .putStringList("h", h)
                .putStringArray("i", i)
                .putString("j", a)
                .putStringSet("s", s)
                .remove("j")
                .commit();

        assertEquals(a, mSPUtil.getString("a"));
        assertEquals(b, mSPUtil.getInt("b"));
        assertEquals(c, mSPUtil.getBoolean("c"));
        assertEquals(d, mSPUtil.getLong("d"));
        assertEquals(e, mSPUtil.getFloat("e"));
        assertEquals(f.toString(), mSPUtil.getJSONObject("f").toString());
        assertEquals(g.toString(), mSPUtil.getJSONArray("g").toString());
        assertEquals(h.toString(),mSPUtil.getStringList("h").toString());
        assertEquals(Arrays.toString(i),Arrays.toString(mSPUtil.getStringArray("i")));
        assertEquals(s.size(), mSPUtil.getStringSet("s").size());
        assertEquals("", mSPUtil.getString("j"));
        assertNotNull(mSPUtil.getAll());
        assertEquals(true, mSPUtil.contains("a"));
        mSPUtil.clear();
    }
}