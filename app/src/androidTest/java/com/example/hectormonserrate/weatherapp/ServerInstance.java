package com.example.hectormonserrate.weatherapp;

import android.os.StrictMode;
import java.io.IOException;
import okhttp3.mockwebserver.MockWebServer;

public class ServerInstance {
  private static MockWebServer server;

  public static MockWebServer getServer() {
    if (server == null) {
      server = new MockWebServer();
    }

    return server;
  }
}
