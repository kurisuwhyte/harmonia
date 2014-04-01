Harmonia
========

This is a draft of the Harmonia's language specification.

Some features:

  - Rich static type system (parametric types, row polymorphism)
  - Safe: hosted, no null, no pointers, immutability by default
  - Ubiquitous: compile to JS, run everywhere
  - Object oriented with structural typing and multi-methods.
  - Functional: first-class functions, HOFs.
  - Compile-time meta programming.
  - Explicit parametrised dependencies.



## 0) Motivation

Harmonia is a programming language focused on writing programming
languages. We believe that the best way of solving problems is to have
not one generic purpose programming language, but several little domain
specific ones.

A common problem with domain specific languages is that they usually
can't talk to one another, so instead Harmonia provides the necessary
core primitives for shaping the language into several DSELs, which then
allow these languages to inter-operate, using the same semantics.

We want Harmonia to be made of simple and orthogonal core concepts, that
may be composed to solve any kind of (simple or complex) problem. We
also want the resulting programs to be simple to read in text form,
although we believe that programs should not be treated as simple
text. For such, we also want a programming language for which good
tooling can be easily written.


## 1) Introduction

Harmonia is a programming language supporting both object-oriented and
functional programming idioms. Although the language can be thought of
as a general programming language, we prefer to see it as a programming
language focused on writing domain specific embedded languages for
solving specific problems. To this end, Harmonia incorporates a set of
simple and composable concepts from many recent innovations in
programming language design: first-class and higher-order functions,
static polymorphic typing (parametric and structural, via
row-polymorphism), user-defined algebraic datatypes, pattern matching,
multi-methods, extensible records with scoped labels, a rich module
system (by way of ML, Scala and Newspeak), and compile-time macros.

The language is influenced by several modern programming languages, such
as Haskell, Newspeak, Standard ML, Elm, Self, Dylan, Clojure, Magpie,
and Racket. While the syntax is a mixture of Smalltalk and Haskell, the
semantics are mostly derived from Lisp and ML. Particularly, Harmonia
prefers constructs that are easier to optimise and reason about given a
static type system, and so uses strict evaluation semantics.

This document defines the formal syntax for Harmonia, and provides an
informal description of the semantics for the constructs in the
language.


### 1.1) Program structure

Harmonia's programs are structured in terms of modules, which capture
the idea of grouping logical functionality together to construct
reusable components.

The top level of a module consists of a set of declarations, including
multi-methods, ADTs, types and other modules. Inside each declaration
you have expressions, which denotes values and have a particular static
type associated.

The whole syntactical structure denotes a series of regions (lexical
scoping), where particular semantics associated with bindings are
valid. New regions and bindings are introduced by modules, parameters in
multi-methods, and expression-level constructs such as `let` and
`where`.


### 1.2) Values and types

Every expression evaluates to a value, and has an associated static
type. Values and types do not share the same namespace in Harmonia, and
you can not treat types as regular values. None the less, the language
allows for user-defined data types, and supports parametric
polymorphism, row polymorphism, and ad-hoc polymorphism through
multi-methods.


### 1.3) Namespaces

( ... )


## 2) Syntactical structure

This section describes the syntactical structure for Harmonia
constructs. We use the following convention for presenting the syntax:

    pattern?                    Optional
    pattern*                    Zero or more repetitions
    pattern+                    One or more repetitions
    (pattern)                   Grouping
    pattern1 | pattern2         Choice
    pattern1 ... pattern2       Anything between 1 and 2
    pattern1 pattern2           Sequence
    not(pattern)                Negation
    "terminal"                  Terminal
    <special>                   Special character
    
The productions are expressed in the form:

    non-terminal = alt1 | alt2 | ... | altn

Harmonia supports the Unicode character set, and implementations are
expected to properly support this.

### 2.1) Comments

```hs
comment      = lexeme-break "--" lexeme-break <any>* new-line
lexeme-break = white-space
             | <beginning of file>
             | <beginning of line>
             | <end of file>
             | <end of line>
new-line     = <carriage return> <line feed>
             | <carriage return>
             | <line feed>
             | <form feed>
```

Comments are valid white-space, beginning with a sequence of two
consecutive dashes (e.g. `--`), surrounded by white-space, and ending
with a new line.

The requirement for the dashes to be surrounded by white-space allows
one to use symbols such as `-->` as valid identifiers. We believe that
this restriction leads to simpler rules of reasoning than those of
Haskell, where a character preceding or following two consecutive dashes
makes it not a comment only if it would form a valid lexeme.


### 2.2) Identifiers and operators

```hs
reserved = "data"
         | "type"
         | "module"
         | "where"
         | "let"
         | "open"
         | "if"
         | "else"
         | "then"
         | "with"
         | "given"
         | "case"
         | "of"
         | "deriving"
         | "do"
         | "="
         | "->"
         | "|"
         | "|>"
         | "<|"
```

(...)

### 2.2) Numeric literals

(...)

### 2.3) String literals

(...)

### 2.4) Block structure

(...)

## 3) Concepts

### 3.1) The language kernel

At the core, Harmonia is a pure functional language not far from lambda
calculus: lambda abstraction, application, pre-defined data types, user-defined
data types (no deriving).

### 3.2) Failure handling

Introduces exceptions and exception handling.

(TODO: needs moar research)

### 3.3) Object orientation

Introduces multi-methods and first-class parametric modules.

### 3.4) Concurrency

Introduces CSP as a model for (non-deterministic) concurrency.

### 3.5) Syntactic abstraction

Macros.

## 4) Standard library

## 5) Formal syntax
