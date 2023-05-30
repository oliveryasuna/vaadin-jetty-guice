package com.oliveryasuna.example.vjg.guice.servlet;

import com.google.inject.AbstractModule;
import com.oliveryasuna.example.vjg.guice.annotation.SessionScope;
import com.oliveryasuna.example.vjg.guice.annotation.UIScope;
import com.vaadin.flow.i18n.I18NProvider;

final class VaadinModule extends AbstractModule {

  // Constructors
  //--------------------------------------------------

  public VaadinModule(final GuiceVaadinServlet servlet) {
    super();

    if(servlet == null) throw new IllegalArgumentException("Servlet must not be null.");

    this.servlet = servlet;
  }

  // Fields
  //--------------------------------------------------

  private final GuiceVaadinServlet servlet;

  // Methods
  //--------------------------------------------------

  @Override
  protected void configure() {
    configureI18nProvider();
    configureScopes();
  }

  private void configureI18nProvider() {
    final Class<? extends I18NProvider> i18nProviderClass = servlet.getI18NProviderClass();

    if(i18nProviderClass != null) {
      bind(I18NProvider.class).to(i18nProviderClass);
    }
  }

  private void configureScopes() {
    bindScope(UIScope.class, servlet.getUiScope());
    bindScope(SessionScope.class, servlet.getSessionScope());
  }

}
