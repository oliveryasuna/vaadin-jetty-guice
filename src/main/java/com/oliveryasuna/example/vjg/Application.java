package com.oliveryasuna.example.vjg;

import com.oliveryasuna.example.vjg.jetty.JettyServer;

public final class Application {

  // Entry point
  //--------------------------------------------------

  public static void main(final String[] args) throws Exception {
    new Application(8080, "/").run(args);
  }

  // Constructors
  //--------------------------------------------------

  private Application(final int port, final String contextPath) {
    super();

    this.port = port;
    this.contextPath = contextPath;
  }

  // Fields
  //--------------------------------------------------

  private final int port;

  private final String contextPath;

  // Methods
  //--------------------------------------------------

  public void run(final String[] args) throws InterruptedException {
    final Thread serverThread = new Thread(new JettyServer(port, contextPath, args));

    serverThread.start();
    serverThread.join();
  }

}
