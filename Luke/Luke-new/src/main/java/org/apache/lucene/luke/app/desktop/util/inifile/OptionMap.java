package org.apache.lucene.luke.app.desktop.util.inifile;

import java.util.LinkedHashMap;

/** Key-value store for options */
class OptionMap extends LinkedHashMap<String, String> {

  String getAsString(String key) {
    return get(key);
  }

  Boolean getAsBoolean(String key) {
    return Boolean.parseBoolean(get(key));
  }

}
