package com.gwtStudy.myWebApp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GwtAppServiceIntfAsync {
    void gwtAppCallServer(String data, AsyncCallback<String> callback) throws IllegalArgumentException;
}
