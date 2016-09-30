package com.example.hectormonserrate.weatherapp.rules;

import com.example.hectormonserrate.weatherapp.ServerInstance;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.QueueDispatcher;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class MockWebServerRule implements TestRule {

  private MockWebServer server;

  public MockWebServer getServer() {
    return server;
  }

  public void clearRequests() {
    server.setDispatcher(new QueueDispatcher());
  }

  @Override public Statement apply(final Statement base, Description description) {
    return new org.junit.runners.model.Statement() {
      @Override public void evaluate() throws Throwable {
        server = ServerInstance.getServer();
        server.start();
        try {
          base.evaluate();
        } finally {
          server.shutdown();
        }
      }
    };
  }
}
