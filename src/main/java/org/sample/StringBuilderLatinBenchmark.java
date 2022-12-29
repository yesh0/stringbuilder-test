package org.sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class StringBuilderLatinBenchmark {

  public String[] latinStrings;
  public StringBuilder expandedBuilder;

  @Setup(Level.Iteration)
  public void setup() {
    //noinspection SpellCheckingInspection
    String first = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    latinStrings = new String[3];
    for (int i = 0; i < 3; i++) {
      latinStrings[i] = first.substring(i * 2) + first.substring(0, i * 2);
    }
    expandedBuilder = new StringBuilder();
  }

  /**
   * Tests {@link StringBuilder} performance: concatenations reuses the same builder.
   */
  @Benchmark
  public String testConcatWithSameBuilder() {
    expandedBuilder.setLength(0);
    return expandedBuilder
        .append(latinStrings[0])
        .append(latinStrings[1])
        .append(latinStrings[2])
        .toString();
  }

  /**
   * Tests performance: string concatenations with Indy.
   */
  @Benchmark
  public String testConcatLatin() {
    return latinStrings[0] + latinStrings[1] + latinStrings[2];
  }

  /**
   * Tests performance: concatenations with {@link String#join(CharSequence, CharSequence...)}.
   */
  @Benchmark
  public String testConcatLatinJoiner() {
    return String.join("", latinStrings);
  }

}
