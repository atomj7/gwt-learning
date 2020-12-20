package com.gwtStudy.myWebApp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwtAppService")
public interface GwtAppServiceIntf extends RemoteService {
    String gwtAppCallServer(String data) throws IllegalArgumentException;
}
