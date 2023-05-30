package com.oliveryasuna.example.vjg.guice.scope.ui;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.SessionDestroyEvent;
import com.vaadin.flow.server.SessionDestroyListener;
import com.vaadin.flow.server.VaadinSession;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public final class UIScope implements Scope, SessionDestroyListener {

  // Constructors
  //--------------------------------------------------

  public UIScope() {
    super();
  }

  // Fields
  //--------------------------------------------------

  private final Map<VaadinSession, Map<UI, Map<Key<?>, Object>>> cache = Collections.synchronizedMap(new WeakHashMap<>());

  // Methods
  //--------------------------------------------------

  @Override
  public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscopedProvider) {
    return new UIScopedProvider<>(this, key, unscopedProvider);
  }

  @Override
  public void sessionDestroy(final SessionDestroyEvent event) {
    synchronized(event.getSession()) {
      cache.remove(event.getSession());
    }
  }

  // Getters/setters
  //--------------------------------------------------

  Map<VaadinSession, Map<UI, Map<Key<?>, Object>>> getCache() {
    return cache;
  }

}
