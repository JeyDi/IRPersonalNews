package org.apache.lucene.luke.app.desktop;

import java.io.IOException;

/** Factory of {@link Preferences} */
public class PreferencesFactory {

  private static Preferences prefs;

  public synchronized static Preferences getInstance() throws IOException {
    if (prefs == null) {
      prefs = new PreferencesImpl();
    }
    return prefs;
  }

}
