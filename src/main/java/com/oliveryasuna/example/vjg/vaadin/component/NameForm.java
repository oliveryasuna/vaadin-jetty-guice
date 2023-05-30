package com.oliveryasuna.example.vjg.vaadin.component;

import com.google.inject.Inject;
import com.oliveryasuna.example.vjg.guice.annotation.UIScope;
import com.oliveryasuna.example.vjg.vaadin.session.SessionData;
import com.oliveryasuna.example.vjg.vaadin.view.HomeView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouteConfiguration;

@UIScope
public final class NameForm extends HorizontalLayout {

  // Constructors
  //--------------------------------------------------

  @Inject
  public NameForm(final SessionData sessionData) {
    super();

    final TextField nameField = new TextField("Name");
    final Button loginButton = new Button("Login", event -> {
      sessionData.setName(nameField.getValue());

      final Class<? extends Component> currentViewClass
          = RouteConfiguration.forApplicationScope().getRoute(UI.getCurrent().getInternals().getActiveViewLocation().getPath()).get();

      if(currentViewClass.equals(HomeView.class)) {
        UI.getCurrent().getPage().reload();
      } else {
        UI.getCurrent().navigate(HomeView.class);
      }
    });

    add(nameField, loginButton);

    setDefaultVerticalComponentAlignment(Alignment.END);
  }

}
