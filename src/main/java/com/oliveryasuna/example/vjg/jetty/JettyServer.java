package com.oliveryasuna.example.vjg.jetty;

import com.oliveryasuna.example.vjg.Application;
import com.oliveryasuna.example.vjg.guice.servlet.GuiceVaadinServlet;
import com.vaadin.flow.server.startup.ServletContextListeners;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JettyServer implements Runnable {

  // Logging
  //--------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger(JettyServer.class);

  // Constructors
  //--------------------------------------------------

  public JettyServer(final int port, final String contextPath, final String[] args) {
    super();

    this.port = port;
    this.contextPath = contextPath;

    this.args = args;
  }

  // Fields
  //--------------------------------------------------

  private final int port;

  private final String contextPath;

  private final String[] args;

  // Methods
  //--------------------------------------------------

  @Override
  public void run() {
    final Server server = new Server(port);
    final WebAppContext context = createWebAppContext();

    server.setHandler(context);

    try {
      server.start();
      server.join();
    } catch(final Exception e) {
      LOGGER.error("Failed to start Jetty server.", e);

      Thread.currentThread().interrupt();
    }
  }

  private WebAppContext createWebAppContext() {
    final WebAppContext context = new WebAppContext();

    context.setBaseResource(Resource.newResource(Application.class.getResource("/webapp")));
    context.setContextPath(contextPath);
    context.setExtractWAR(false);

    final ServletHolder vaadinServletHolder = createVaadinServlet(context);

    vaadinServletHolder.setInitOrder(1);

    context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*");
    context.setConfigurationDiscovered(true);
    context.setConfigurations(new Configuration[] {
        new AnnotationConfiguration(),
        new WebInfConfiguration(),
        new WebXmlConfiguration(),
        new MetaInfConfiguration(),
        new FragmentConfiguration(),
        new EnvConfiguration(),
        new PlusConfiguration(),
        new JettyWebXmlConfiguration()
    });
    context.getServletContext().setExtendedListenerTypes(true);
    context.addEventListener(new ServletContextListeners());

    return context;
  }

  private ServletHolder createVaadinServlet(final WebAppContext context) {
    return context.addServlet(GuiceVaadinServlet.class, "/*");
  }

}
