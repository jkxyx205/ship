package com.yodean.client.security.realm;

import com.yodean.cas.api.RemoteServiceInterface;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by rick on 7/13/18.
 */
public class ClientRealm extends AuthorizingRealm {

    private RemoteServiceInterface remoteService;

    private String appKey;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        remoteService.getSession("helloword", 1);
//        PermissionContext context = remoteService.getPermissions(appKey, username);
//        authorizationInfo.setRoles(context.getRoles());
//        authorizationInfo.setStringPermissions(context.getPermissions());
        authorizationInfo.getRoles().add("admin");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        throw new UnsupportedOperationException("永远不会被调用");
    }
}
