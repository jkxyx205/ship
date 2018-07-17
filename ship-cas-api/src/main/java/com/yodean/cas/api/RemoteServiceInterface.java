package com.yodean.cas.api;

import org.apache.shiro.session.Session;

import java.io.Serializable;

/**
 * Created by rick on 7/13/18.
 */
public interface RemoteServiceInterface {

    Session getSession(String appKey, Serializable sessionId);

    Serializable createSession(Session session);

    void updateSession(String appKey, Session session);

    void deleteSession(String appKey, Session session);
}
