package org.jbpm.pvm.internal.email.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;

/**
 * Settings for establishing a session with a mail server.
 * 
 * @author Brad Davis
 */
public class MailServer {

  private AddressFilter addressFilter;
  private Properties sessionProperties;
  private Authenticator authenticator;

  public AddressFilter getAddressFilter() {
    return addressFilter;
  }

  protected void setAddressFilter(AddressFilter filter) {
    this.addressFilter = filter;
  }

  public Properties getSessionProperties() {
    return sessionProperties;
  }

  protected void setSessionProperties(Properties sessionProperties) {
    this.sessionProperties = sessionProperties;
  }

  public Authenticator getAuthenticator() {
    return authenticator;
  }

  protected void setAuthenticator(Authenticator authenticator) {
    this.authenticator = authenticator;
  }

  public Session getMailSession() {
    return Session.getDefaultInstance(sessionProperties, authenticator);
  }
}
