package com.oliveryasuna.example.vjg.vaadin.session;

import com.oliveryasuna.example.vjg.guice.annotation.SessionScope;

import java.io.Serializable;

@SessionScope
public final class SessionData implements Serializable {

  // Constructors
  //--------------------------------------------------

  public SessionData() {
    super();
  }

  // Fields
  //--------------------------------------------------

  private String name;

  // Getters/setters
  //--------------------------------------------------

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

}
