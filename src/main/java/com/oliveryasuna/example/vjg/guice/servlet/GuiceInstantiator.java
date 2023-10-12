package com.oliveryasuna.example.vjg.guice.servlet;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.di.Instantiator;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinServiceInitListener;

import java.util.stream.Stream;

final class GuiceInstantiator implements Instantiator {

  // Constructors
  //--------------------------------------------------

  public GuiceInstantiator(final GuiceVaadinServletService service) {
    super();

    if(service == null) throw new IllegalArgumentException("Injector must not be null.");

    this.service = service;
  }

  // Fields
  //--------------------------------------------------

  private final GuiceVaadinServletService service;

  // Methods
  //--------------------------------------------------

  @Override
  public Stream<VaadinServiceInitListener> getServiceInitListeners() {
    final GuiceVaadinServlet servlet = service.getServlet();

    return servlet.getVaadinServiceInitListenerClasses().stream()
        .map(servlet.getInjector()::getInstance);
  }

  @Override
  public <T> T getOrCreate(final Class<T> type) {
    return service.getServlet().getInjector().getInstance(type);
  }

  @Override
  public <T extends Component> T createComponent(final Class<T> componentClass) {
    return getOrCreate(componentClass);
  }

  @Override
  public I18NProvider getI18NProvider() {
    final Class<? extends I18NProvider> i18nProviderClass = service.getServlet().getI18NProviderClass();

    if(i18nProviderClass == null) {
      return null;
    }

    return getOrCreate(i18nProviderClass);
  }

}
