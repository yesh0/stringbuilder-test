/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * Benchmarks on {@link StringBuilder} usages.
 *
 * <p>
 * Note that we are compiling things with Java 17. So explicit string concatenation must be used to
 * avoid interference from <a href="https://openjdk.org/jeps/280"> JEP 280: Indify String
 * Concatenation</a>.
 * </p>
 */
@SuppressWarnings("SpellCheckingInspection")
@State(Scope.Thread)
public class StringBuilderBenchmark {

  public String[] strings;
  public StringBuilder sameBuilder;
  public char[] buffer;

  @Setup(Level.Iteration)
  public void setup() {
    String first = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    String second = "要驳互助说时用争存说，驳争存说时用互助说；反对和平论时用阶级争斗说，反对斗争时就主张人类之爱。论敌是唯心论者呢，他的立场是唯物论，待到和唯物论者相辩难，他却又化为唯心论者了。要之，是用英尺来量俄里，又用法尺来量密达，而发见无一相合的人。因为别的一切，无一相合，于是永远觉得自己是“允执厥中”，永远得到自己满足。从这些人们的批评的指示，则只要不完全，有缺陷，就不行。但现在的人，的事，那里会有十分完全，并无缺陷的呢，为万全计，就只好毫不动弹。然而这毫不动弹，却也就是一个大错。";

    strings = new String[16];
    for (int i = 0; i < 8; i++) {
      strings[i * 2] = first.substring(i) + first.substring(0, i);
      strings[i * 2 + 1] = second.substring(i) + second.substring(0, i);
    }
    sameBuilder = new StringBuilder((strings[0].length() + strings[1].length()) * 2);

    int length = (strings[0].length() + strings[1].length()) * 8;
    buffer = new char[length];
  }

  /**
   * Tests {@link StringBuilder} performance: each concatenation instantiates a new builder.
   */
  @Benchmark
  public String testConcatWithBuilders() {
    StringBuilder builder = new StringBuilder();
    for (String string : strings) {
      builder.append(string);
    }
    return builder.toString();
  }

  /**
   * Tests {@link StringBuilder} performance: concatenations reuses the same builder.
   */
  @Benchmark
  public String testConcatWithSameBuilder() {
    sameBuilder.setLength(0);
    for (String string : strings) {
      sameBuilder.append(string);
    }
    return sameBuilder.toString();
  }

  /**
   * Tests raw string concatenation: strings are copied into a char array.
   */
  @Benchmark
  public String testConcatWithCharArray() {
    int position = 0;
    for (String string : strings) {
      string.getChars(0, string.length(), buffer, position);
      position += string.length();
    }
    return new String(buffer);
  }

  /**
   * Tests string concatenation: {@link String#join(CharSequence, CharSequence...)}
   */
  @Benchmark
  public String testConcatJoiner() {
    return String.join("", strings);
  }

  /**
   * Tests performance: string concatenations with Indy.
   */
  @Benchmark
  public String testConcat() {
    return strings[0]
        + strings[1]
        + strings[2]
        + strings[3]
        + strings[4]
        + strings[5]
        + strings[6]
        + strings[7]
        + strings[8]
        + strings[9]
        + strings[10]
        + strings[11]
        + strings[12]
        + strings[13]
        + strings[14]
        + strings[15];
  }

}
