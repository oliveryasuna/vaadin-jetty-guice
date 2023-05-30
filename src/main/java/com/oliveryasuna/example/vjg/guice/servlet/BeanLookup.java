package com.oliveryasuna.example.vjg.guice.servlet;

import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

final class BeanLookup {

  // Constructors
  //--------------------------------------------------

  public BeanLookup(final List<String> basePackages) {
    super();

    this.basePackages = Collections.unmodifiableList(basePackages);
  }

  // Fields
  //--------------------------------------------------

  private final List<String> basePackages;

  // Methods
  //--------------------------------------------------

  public BeanLookupResult lookup() {
    final Reflections reflections = new Reflections(basePackages.toArray());

    final Set<Class<? extends VaadinServiceInitListener>> vaadinServiceInitListenerClasses
        = Collections.unmodifiableSet(filterInstantiable(reflections.getSubTypesOf(VaadinServiceInitListener.class)));

    final Set<Class<? extends I18NProvider>> i18nProviderClasses
        = Collections.unmodifiableSet(filterInstantiable(reflections.getSubTypesOf(I18NProvider.class)));

    final Class<? extends I18NProvider> i18nProviderClass;

    final int i18nProviderClassesSize = i18nProviderClasses.size();

    if(i18nProviderClassesSize == 1) {
      i18nProviderClass = i18nProviderClasses.iterator().next();
    } else if(i18nProviderClassesSize == 0) {
      i18nProviderClass = null;
    } else {
      throw new IllegalStateException("There must be only one I18NProvider class.");
    }

    return new BeanLookupResult(vaadinServiceInitListenerClasses, i18nProviderClass);
  }

  private <T> Set<Class<? extends T>> filterInstantiable(final Set<Class<? extends T>> classes) {
    return classes.stream()
        .filter(clazz -> !clazz.isInterface() && !clazz.isEnum() && !clazz.isAnnotation() && !Modifier.isAbstract(clazz.getModifiers()))
        .collect(Collectors.toSet());
  }

  // Nested
  //--------------------------------------------------

  public static final class BeanLookupResult {

    // Constructors
    //--------------------------------------------------

    private BeanLookupResult(
        final Set<Class<? extends VaadinServiceInitListener>> vaadinServiceInitListenersClasses,
        final Class<? extends I18NProvider> i18nProviderClass
    ) {
      super();

      this.vaadinServiceInitListenersClasses = vaadinServiceInitListenersClasses;
      this.i18nProviderClass = i18nProviderClass;
    }

    // Fields
    //--------------------------------------------------

    private final Set<Class<? extends VaadinServiceInitListener>> vaadinServiceInitListenersClasses;

    private final Class<? extends I18NProvider> i18nProviderClass;

    // Getters/setters
    //--------------------------------------------------

    public Set<Class<? extends VaadinServiceInitListener>> getVaadinServiceInitListenersClasses() {
      return vaadinServiceInitListenersClasses;
    }

    public Class<? extends I18NProvider> getI18nProviderClass() {
      return i18nProviderClass;
    }

  }

}
