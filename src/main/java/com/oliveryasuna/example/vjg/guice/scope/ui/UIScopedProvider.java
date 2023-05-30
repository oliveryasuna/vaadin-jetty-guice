package com.oliveryasuna.example.vjg.guice.scope.ui;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import java.util.Map;
import java.util.WeakHashMap;

final class UIScopedProvider<T> implements Provider<T> {

  // Constructors
  //--------------------------------------------------

  public UIScopedProvider(final UIScope uiScope, final Key<T> key, final Provider<T> unscopedProvider) {
    super();

    this.uiScope = uiScope;
    this.key = key;
    this.unscopedProvider = unscopedProvider;
  }

  // Fields
  //--------------------------------------------------

  private final UIScope uiScope;

  private final Key<T> key;

  private final Provider<T> unscopedProvider;

  // Methods
  //--------------------------------------------------

  @Override
  public T get() {
    final VaadinSession session = VaadinSession.getCurrent();

    if(session == null) {
      throw new IllegalStateException("No session found.");
    }

    final Map<UI, Map<Key<?>, Object>> uiScopeMap;

    synchronized(session) {
      uiScopeMap = uiScope.getCache().computeIfAbsent(session, key -> new WeakHashMap<>());
    }

    final UI ui = UI.getCurrent();

    if(ui == null) {
      throw new IllegalStateException("No UI found.");
    }

    synchronized(ui) {
      final Map<Key<?>, Object> scopeMap = uiScopeMap.computeIfAbsent(ui, key -> new WeakHashMap<>());

      return  (T)scopeMap.computeIfAbsent(key, key -> unscopedProvider.get());
    }
  }

}
