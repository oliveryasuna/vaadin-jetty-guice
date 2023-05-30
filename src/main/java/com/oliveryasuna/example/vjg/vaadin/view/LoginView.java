package com.oliveryasuna.example.vjg.vaadin.view;

import com.google.inject.Inject;
import com.oliveryasuna.example.vjg.vaadin.component.NameForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/")
public final class LoginView extends VerticalLayout {

  // Constructors
  //--------------------------------------------------

  @Inject
  public LoginView(final NameForm nameForm) {
    super();

    add(nameForm);
  }

}
