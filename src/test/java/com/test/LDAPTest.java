package com.test;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Random;

/**
 * @author: YANLL
 * @version:
 * @since: 2019/06/28
 */
public class LDAPTest {


    public static void main(String[] args) throws Exception {
        LDAPTest ldapTest = new LDAPTest();
        ldapTest.原始();
    }


    private void 认证() {
        try {
            for (int i = 0; i < 50; i++) {
                long s = System.currentTimeMillis();
                Properties props = new Properties();
                props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                props.put(Context.SECURITY_AUTHENTICATION, "simple");
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


    public void 原始() throws Exception {
        SearchControls ctls = new SearchControls();
        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String returnedAtts[] = {"sAMAccountName", "mail", "pwdLastSet", "msDS-UserPasswordExpiryTimeComputed"};
        ctls.setReturningAttributes(returnedAtts);
        String filter = "(sAMAccountName=*)";
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, "");
        env.put(Context.SECURITY_PRINCIPAL, "");
        env.put(Context.SECURITY_CREDENTIALS, "");

        DirContext ctx = new InitialDirContext(env);

        NamingEnumeration e = ctx.search("DC=yeepay,DC=com", filter, ctls);
        int i = 0;
        try {
            while (e.hasMore()) {
                SearchResult entry = (SearchResult) e.next();

                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
