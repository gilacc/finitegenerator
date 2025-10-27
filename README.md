# FiniteGenerator

[![mvn](https://github.com/gilacc/finitegenerator/actions/workflows/maven.yml/badge.svg)](https://github.com/gilacc/finitegenerator/actions/workflows/maven.yml)
![GitHub License](https://img.shields.io/github/license/gilacc/finitegenerator)
![Maven Central Version](https://img.shields.io/maven-central/v/io.github.gilacc/finitegenerator)

A small Java utility library to create finite `Iterable`s from either finite or infinite `Iterable`s and `Iterator`s.
The library provides a single class, `FiniteGenerator`, which is an immutable container that decorates a sequence and
imposes a reasonable limit on how many elements it can produce; this can be very helpful for working with `Iterables`
that yield an indefinite amount of elements, transforming `Iterable`s into collection types and avoiding potential
out-of-memory errors caused by infinite sequences.

## Usage

To include this library as a Maven dependency:

```xml
<dependency>
    <groupId>io.github.gilacc</groupId>
    <artifactId>finitegenerator</artifactId>
    <version>0.1.1</version>
</dependency>
```

## Examples

Generate a sequence of 10 integers, starting with 0:

```java
import java.util.Iterator;
import java.util.stream.IntStream;

import io.github.gilacc.finitegenerator.FiniteGenerator;

public class Range {
    public static void main(String[] args) {
        Iterator<Integer> range = IntStream.iterate(0, i -> i + 1).iterator();
        new FiniteGenerator<>(range, 10).forEach(System.out::println);
    }
}
```

Given a list of N elements, create a sublist with only the 5 first elements:

```java
import java.util.List;

import io.github.gilacc.finitegenerator.FiniteGenerator;

public class Sublist {
    public static void main(String[] args) {
        List<Integer> list = List.of(0, 1, 2, 3, 4, 5, 6);
        List<Integer> sublist = new FiniteGenerator<>(list, 5).toList();
        sublist.forEach(System.out::println);
    }
}
```

Generate a sequence of 20 floating-point numbers between 0 and 1:

```java
import java.util.Random;
import java.util.stream.DoubleStream;

import io.github.gilacc.finitegenerator.FiniteGenerator;

public class RandomNumbers {
    public static void main(String[] args) {
        DoubleStream randomNumbers = new Random().doubles();
        new FiniteGenerator<>(randomNumbers.iterator(), 20).forEach(System.out::println);
    }
}
```
