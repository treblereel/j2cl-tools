package org.kie.j2cl.tools.exports;

import jsinterop.annotations.JsType;
import org.kie.j2cl.tools.processors.annotations.GWT3Export;

@JsType
@GWT3Export
public class SimplePojoJsType {

  static String static_fieldOne = "static_fieldOne";

  protected static String static_fieldTwo = "static_fieldTwo";

  public static String static_fieldThree = "static_fieldThree";

  String instance_fieldOne = "instance_fieldOne";

  protected String instance_fieldTwo = "instance_fieldTwo";

  public String instance_fieldThree = "instance_fieldThree";
}
