# EZ Collections

[![Build Status](https://travis-ci.org/alexeydergunov/ez-collections.svg?branch=master)]
(https://travis-ci.org/alexeydergunov/ez-collections)

EZ Collections is a Java library providing collections of the primitive types. These collections work faster and consume
less memory than standard collections from java.util package.

The library is written mainly for programming contests as Codeforces, Topcoder, Google Code Jam. It doesn't have many
features that are necessary in normal software development, e.g. it's not thread-safe, doesn't support serialization,
has read-only iterators and maybe something else. But if you don't care about all these things, you may be happy to use
it in your project.

## Supported collections

Here is the list of already implemented collections and algorithms:

- ArrayList
- ArrayDeque (with the possibility to get the element by its index)
- Heap
- HashSet / HashMap (open-addressing implementation)
- TreeSet / TreeMap
- TreeList (list that performs add, set, insert and removeAt operations in O(logN))
- Sorting algorithms (guaranteed O(NlogN) implementation)
- Pair classes

## Installation and usage

To build the library, you need Maven installed. Enter the library's root directory and execute `mvn clean install`.
After that the library's jar files will be saved in your local Maven repository.

To use it at programming contests, it's recommended to install [CHelper](https://github.com/EgorKulikov/idea-chelper)
plugin for IDEA by Egor Kulikov. Starting from version 3.95, CHelper fully supports EZ Collections.

After installing CHelper plugin, just add the dependency on EZ Collections in your project. Note that you have to
specify both jar files: one contains class files, and the other one contains sources.

## Notes

- `NaN`, `POSITIVE_INFINITY` and other similar stuff are not supported.

- As it's impossible to return null (because EZ Collections store primitive types), method `returnedNull()` is added
in every class where it's necessary. You must call it to perform null-check immediately after calling the method which
could have returned null in usual Java Collections. For instance, these code fragments are identical:

```java
    TreeSet<Integer> set = new TreeSet<Integer>();
    Integer lower = set.lower(42);
    if (lower == null) {
        ...
    }

    EzIntTreeSet set = new EzIntTreeSet();
    int lower = set.lower(42);
    if (set.returnedNull()) {
        // don't use the lower variable, it's not guaranteed that it has some certain value
    }
```

- boolean is considered to be incomparable type, so Pairs with booleans don't implement Comparable. It will be fixed
someday.

## Example

Let's solve the problem [Bertown roads](http://codeforces.com/contest/118/problem/E) from one of the Codeforces rounds.
In this problem you have to store the graph, and one of the common ways is to store adjacency lists.

- Use `List<Integer>[]`: [link](http://codeforces.com/contest/118/submission/8293080) - 1060 ms, 39100 KB
- Use `EzIntList[]`: [link](http://codeforces.com/contest/118/submission/8293086) - 654 ms, 12148 KB
