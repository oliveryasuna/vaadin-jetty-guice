package com.oliveryasuna.example.vjg.guice.servlet;

import com.vaadin.flow.di.Instantiator;
import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.VaadinServletService;

import java.util.Optional;

public class GuiceVaadinServletService extends VaadinServletService {

  // Constructors
  //--------------------------------------------------

  GuiceVaadinServletService(final GuiceVaadinServlet servlet, final DeploymentConfiguration configuration) {
    super(servlet, configuration);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected Optional<Instantiator> loadInstantiators() throws ServiceException {
    return Optional.of(new GuiceInstantiator(this));
  }

  @Override
  public GuiceVaadinServlet getServlet() {
    return (GuiceVaadinServlet)super.getServlet();
  }

}
