/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aptgeneratorplugin;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * A trivially small annotation processor existing only to verify that annotation processors can and
 * will be correctly run and translated to JS inside of j2cl_library() rules.
 */
public class DummyProcessor extends AbstractProcessor {

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  private boolean createdDummy;

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    HashSet<String> supportedAnnotationNames = new HashSet<String>();
    supportedAnnotationNames.add(Override.class.getName());
    return supportedAnnotationNames;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (createdDummy) {
      return false;
    }

    createdDummy = true;
    try {
      JavaFileObject dummySourceFile =
          processingEnv.getFiler().createSourceFile("aptgeneratorplugin.Dummy");
      Writer writer = dummySourceFile.openWriter();
      writer.write(
          "package aptgeneratorplugin;\n"
              + "// Generated by DummyProcessor.java\n"
              + "public class Dummy {}\n");
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return false;
  }
}
