package com.oliveryasuna.example.vjg.guice.scope.session;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.vaadin.flow.server.SessionDestroyEvent;
import com.vaadin.flow.server.SessionDestroyListener;
import com.vaadin.flow.server.VaadinSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SessionScope implements Scope, SessionDestroyListener {

  // Constructors
  //--------------------------------------------------

  public SessionScope() {
    super();
  }

  // Fields
  //--------------------------------------------------

  private final Map<VaadinSession, Map<Key<?>, Object>> cache = new ConcurrentHashMap<>();

  // Methods
  //--------------------------------------------------

  @Override
  public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscopedProvider) {
    return new SessionScopedProvider<>(this, key, unscopedProvider);
  }

  @Override
  public void sessionDestroy(final SessionDestroyEvent event) {
    synchronized(event.getSession()) {
      cache.remove(event.getSession());
    }
  }

  // Getters/setters
  //--------------------------------------------------

  Map<VaadinSession, Map<Key<?>, Object>> getCache() {
    return cache;
  }

}
