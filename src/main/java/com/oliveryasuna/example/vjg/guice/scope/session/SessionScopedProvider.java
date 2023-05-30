package com.oliveryasuna.example.vjg.guice.scope.session;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.Map;

final class SessionScopedProvider<T> implements Provider<T> {

  // Constructors
  //--------------------------------------------------

  public SessionScopedProvider(final SessionScope sessionScope, final Key<?> key, final Provider<T> unscopedProvider) {
    super();

    this.sessionScope = sessionScope;
    this.key = key;
    this.unscopedProvider = unscopedProvider;
  }

  // Fields
  //--------------------------------------------------

  private final SessionScope sessionScope;

  private final Key<?> key;

  private final Provider<T> unscopedProvider;

  // Methods
  //--------------------------------------------------

  @Override
  public T get() {
    final VaadinSession session = VaadinSession.getCurrent();

    if(session == null) {
      throw new IllegalStateException("No session found.");
    }

    synchronized(session) {
      final Map<Key<?>, Object> scope = sessionScope.getCache().computeIfAbsent(session, k -> new HashMap<>());

      return (T)scope.computeIfAbsent(key, k -> unscopedProvider.get());
    }
  }

}
