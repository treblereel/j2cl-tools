/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.vertispan.j2cl.tools;

import com.google.j2cl.common.SourceUtils;
import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.parsing.Config;
import com.vertispan.j2cl.build.task.BuildLog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsChecker {
    private final List<File> upstreamExterns;
    private final BuildLog log;

    public JsChecker(List<File> upstreamExterns, BuildLog log) {
        this.upstreamExterns = upstreamExterns;
        this.log = log;
    }

    public boolean checkAndGenerateExterns(List<SourceUtils.FileInfo> jsInputs, File externsFile) {
//        upstreamExterns.forEach(System.out::println);
//        jsInputs.stream().map(SourceUtils.FileInfo::sourcePath).forEach(System.out::println);
        // configure compiler
        Compiler compiler = new Compiler();
        compiler.setErrorManager(new SortingErrorManager(Collections.singleton(new LoggingErrorReportGenerator(compiler, log))));

        CompilerOptions options = new CompilerOptions();
        options.setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT_2017);
        options.setStrictModeInput(true);
        options.setIncrementalChecks(CompilerOptions.IncrementalCheckMode.GENERATE_IJS);
        options.setCodingConvention(new ClosureCodingConvention());
        options.setSkipTranspilationAndCrash(true);
        options.setContinueAfterErrors(true);
        options.setPrettyPrint(true);
        options.setPreserveTypeAnnotations(true);
        options.setPreserveDetailedSourceInfo(true);
        options.setEmitUseStrict(false);
        options.setParseJsDocDocumentation(Config.JsDocParsing.INCLUDE_DESCRIPTIONS_NO_WHITESPACE);
//        JsCheckerErrorFormatter errorFormatter =
//                new JsCheckerErrorFormatter(compiler, state.roots, labels);
//        errorFormatter.setColorize(true);
//        JsCheckerErrorManager errorManager = new JsCheckerErrorManager(errorFormatter);
//        compiler.setErrorManager(errorManager);


        // Run the compiler.
//        compiler.setPassConfig(new JsCheckerPassConfig(state, options));//TODO restore this line to actually check things!
        compiler.disableThreads();
        Result result = compiler.compile(
                Collections.emptyList(),
                jsInputs.stream()
                        .map(SourceUtils.FileInfo::sourcePath)
                        .map(SourceFile::fromFile)
                        .collect(Collectors.toUnmodifiableList()),
                options);

        if (!result.success) {
            // results were already piped through the LoggingErrorReportGenerator
            return false;
        }


        // write errors
        // TODO This is probably unnecessary, should have already gone out through our own error manager,
        //      but worth confirming.
//        if (!expectFailure) {
//            for (String line : errorManager.stderr) {
//                System.err.println(line);
//            }
//        }
//        if (!outputErrors.isEmpty()) {
//            Files.write(Paths.get(outputErrors), errorManager.stderr, UTF_8);
//        }

        // write .i.js type summary for this library
        try {
            Files.write(externsFile.toPath(), "/** @externs */".getBytes(UTF_8));
            Files.write(externsFile.toPath(), compiler.toSource().getBytes(UTF_8), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
