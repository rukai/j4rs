/*
 * Copyright 2018 astonbitecode
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.astonbitecode.j4rs.tests;

import org.astonbitecode.j4rs.api.Instance;
import org.astonbitecode.j4rs.api.dtos.InvocationArg;
import org.astonbitecode.j4rs.api.instantiation.NativeInstantiationImpl;
import org.astonbitecode.j4rs.errors.InvocationException;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class MyTestTest {
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<String> getStringWithFuture(String string) {
        return executor.submit(() -> string);
    }

    public Future<String> getErrorWithFuture(String message) {
        return executor.submit(() -> {
            throw new InvocationException(message);
        });
    }

    public void dummy() {
        Instance instance = NativeInstantiationImpl.instantiate("org.astonbitecode.j4rs.tests.MyTest");
        IntStream.range(0, 1000000000).forEach(i -> {
            if (i % 100000 == 0) {
                System.out.println(i);
            }

            InvocationArg ia = new InvocationArg("java.lang.String", "\"astring\"");
            instance.invoke("getMyWithArgs", ia);
        });
    }

    public Integer addInts(Integer... args) {
        int result = Arrays.stream(args).reduce(0, (a, b) -> {
            return a + b;
        });
        return result;
    }

    public Integer addInts(List<Integer> args) {
        int result = args.stream().reduce(0, (a, b) -> {
            return a + b;
        });
        return result;
    }
}
