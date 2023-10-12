package com.oliveryasuna.example.vjg.guice.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.oliveryasuna.example.vjg.guice.scope.session.SessionScope;
import com.oliveryasuna.example.vjg.guice.scope.ui.UIScope;
import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class GuiceVaadinServlet extends VaadinServlet {

  // Constructors
  //--------------------------------------------------

  public GuiceVaadinServlet() {
    super();
  }

  // Fields
  //--------------------------------------------------

  private final UIScope uiScope = new UIScope();

  private final SessionScope sessionScope = new SessionScope();

  private final Set<Class<? extends VaadinServiceInitListener>> vaadinServiceInitListenerClasses = new HashSet<>();

  private Class<? extends I18NProvider> i18NProviderClass;

  private Injector injector;

  // Methods
  //--------------------------------------------------

  @Override
  public void init(final ServletConfig configuration) throws ServletException {
    initializeBeans();
    initializeInjector();

    super.init(configuration);
  }

  private void initializeBeans() {
    final BeanLookup.BeanLookupResult beanLookupResult = new BeanLookup(Collections.singletonList("com.oliveryasuna.example.vjg")).lookup();

    vaadinServiceInitListenerClasses.addAll(beanLookupResult.getVaadinServiceInitListenersClasses());
    i18NProviderClass = beanLookupResult.getI18nProviderClass();
  }

  private void initializeInjector() {
    injector = Guice.createInjector(new VaadinModule(this));
  }

  @Override
  protected GuiceVaadinServletService createServletService(final DeploymentConfiguration configuration) throws ServiceException {
    final GuiceVaadinServletService servletService = new GuiceVaadinServletService(this, configuration);

    servletService.init();

    return servletService;
  }

  // Getters/setters
  //--------------------------------------------------

  UIScope getUiScope() {
    return uiScope;
  }

  SessionScope getSessionScope() {
    return sessionScope;
  }

  Set<Class<? extends VaadinServiceInitListener>> getVaadinServiceInitListenerClasses() {
    return Collections.unmodifiableSet(vaadinServiceInitListenerClasses);
  }

  Class<? extends I18NProvider> getI18NProviderClass() {
    return i18NProviderClass;
  }

  public Injector getInjector() {
    return injector;
  }

}
