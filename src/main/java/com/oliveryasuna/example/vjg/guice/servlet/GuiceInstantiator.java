package com.oliveryasuna.example.vjg.guice.servlet;

import com.google.inject.Injector;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.di.Instantiator;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;

import java.util.stream.Stream;

final class GuiceInstantiator implements Instantiator {

  // Constructors
  //--------------------------------------------------

  public GuiceInstantiator(final Injector injector) {
    super();

    if(injector == null) throw new IllegalArgumentException("Injector must not be null.");

    this.injector = injector;
  }

  // Fields
  //--------------------------------------------------

  private final Injector injector;

  private GuiceVaadinServlet servlet;

  // Methods
  //--------------------------------------------------

  @Override
  public boolean init(final VaadinService service) {
    if(!(service instanceof final GuiceVaadinServletService guiceService)) {
      return false;
    }

    servlet = guiceService.getServlet();

    return true;
  }

  @Override
  public Stream<VaadinServiceInitListener> getServiceInitListeners() {
    return servlet.getVaadinServiceInitListenerClasses().stream()
        .map(injector::getInstance);
  }

  @Override
  public <T> T getOrCreate(final Class<T> type) {
    return servlet.getInjector().getInstance(type);
  }

  @Override
  public <T extends Component> T createComponent(final Class<T> componentClass) {
    return getOrCreate(componentClass);
  }

  @Override
  public I18NProvider getI18NProvider() {
    final Class<? extends I18NProvider> i18nProviderClass = servlet.getI18NProviderClass();

    if(i18nProviderClass == null) {
      return null;
    }

    return getOrCreate(i18nProviderClass);
  }

}
