package com.helen.andbase.utils;


import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AppManager {
    private static Map<String, WeakReference<Activity>> activitiesMap = new HashMap<>();
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void add(Activity activity) {
        activitiesMap.put(activity.toString(), new WeakReference<Activity>(activity));
    }

    public void remove(Activity activity) {
        activitiesMap.remove(activity.toString());
    }

    public void ExitApp() {
        try {
            Set<String> keys = activitiesMap.keySet();
            for (String key : keys) {
                WeakReference<Activity> weakReferenceAct = activitiesMap.get(key);
                if (weakReferenceAct != null && weakReferenceAct.get() != null) {
                    weakReferenceAct.get().finish();
                    weakReferenceAct.clear();
                }
            }
            activitiesMap.clear();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
