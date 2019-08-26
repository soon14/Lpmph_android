package com.ailk.pmph.utils;



import java.io.Serializable;

public class AppUtility implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8854150485900108472L;

    public static AppUtility app;
    private String sessionId;





    private boolean isShutdown;

    private AppUtility() {
    }

    public synchronized static AppUtility getInstance() {
        if (app == null) {
            app = new AppUtility();
        }
        app.setSessionId(PrefUtility.get("token",""));
        return app;
    }

    public static void setInstance(AppUtility readObj) {
        app = readObj;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public boolean isShutdown() {
        return isShutdown;
    }

    public void setShutdown(boolean isShutdown) {
        this.isShutdown = isShutdown;
    }


}


