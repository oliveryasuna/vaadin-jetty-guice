package com.oliveryasuna.example.vjg.vaadin.view;

import com.google.inject.Inject;
import com.oliveryasuna.example.vjg.vaadin.component.NameForm;
import com.oliveryasuna.example.vjg.vaadin.session.SessionData;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/home")
public final class HomeView extends VerticalLayout {

  // Constructors
  //--------------------------------------------------

  @Inject
  public HomeView(final SessionData sessionData, final NameForm nameForm) {
    super();

    add(nameForm, new Span("Hello, " + sessionData.getName() + "!"));
  }

}
