package com.test;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.LikeFilter;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: YANLL
 * @version:
 * @since: 2019/06/28
 */
public class LDAPTest {


    public static void main(String[] args) throws Exception {
        LDAPTest ldapTest = new LDAPTest();
        ldapTest.检索();
    }


    private void 认证() {
        try {
            for (int i = 0; i < 50; i++) {
                long s = System.currentTimeMillis();
                Properties props = new Properties();
                props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                props.put(Context.SECURITY_AUTHENTICATION, "simple");
                props.put(Context.SECURITY_CREDENTIALS, "");
                props.put(Context.SECURITY_PRINCIPAL, "");
                props.put(Context.PROVIDER_URL, "");
                InitialDirContext initialDirContext = null;
                try {
                    initialDirContext = new InitialDirContext(props);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (initialDirContext != null) {
                        initialDirContext.close();
                        initialDirContext = null;
                    }
                }
                System.out.println("exec " + (System.currentTimeMillis() - s) + " ms");
                Thread.sleep(new Random().nextInt(2000));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void 检索() {
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            org.springframework.ldap.core.support.LdapContextSource contextSource = new org.springframework.ldap.core.support.LdapContextSource();
            contextSource.setCacheEnvironmentProperties(true);
            contextSource.setUrl("ldap://ldap.yeepay.com:389");
            contextSource.setUserDn("portal@yeepay.com");
            contextSource.setPassword("kJ3#si7FQ");
            contextSource.setReferral("follow");
            contextSource.afterPropertiesSet();
            org.springframework.ldap.core.LdapTemplate ldapTemplate = new org.springframework.ldap.core.LdapTemplate();
            ldapTemplate.setContextSource(contextSource);


            SearchControls s = new SearchControls();
            s.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String returnedAtts[] = {"sAMAccountName", "mail", "pwdLastSet", "msDS-UserPasswordExpiryTimeComputed"};
            s.setReturningAttributes(returnedAtts);


            AndFilter f = new AndFilter();
            f.and(new LikeFilter("sAMAccountName", "*"));

            List<Map<String, String>> list = ldapTemplate.search("DC=yeepay,DC=com", f.toString(), s, new AttributesMapper() {
                @Override
                public Object mapFromAttributes(Attributes attributes) throws NamingException {
                    Map<String, String> map = new HashMap<>();
                    Attribute mail = attributes.get("mail");
                    if (mail == null) return null;
                    Attribute sAMAccountName = attributes.get("sAMAccountName");
                    Attribute pwdLastSet = attributes.get("pwdLastSet");
                    Attribute userPasswordExpiryTimeComputed = attributes.get("msDS-UserPasswordExpiryTimeComputed");
                    map.put("sAMAccountName", sAMAccountName != null ? sAMAccountName.get().toString() : "");
                    map.put("email", mail != null ? mail.get().toString() : "");
                    map.put("pwdLastSet", pwdLastSet != null ? pwdLastSet.get().toString() : "");
                    map.put("userPasswordExpiryTimeComputed", userPasswordExpiryTimeComputed != null ? userPasswordExpiryTimeComputed.get().toString() : "");
                    System.out.println(map);
                    return map;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
