Harmonia
========

This is a draft of the Harmonia's language specification.

Some features:

  - Rich static type system (parametric types, row polymorphism)
  - Safe: hosted, no null, no pointers, immutability by default
  - Ubiquitous: compile to JS, run everywhere
  - Object oriented with structural typing and multi-methods.
  - Functional: first-class functions, HOFs, currying by default.
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
system (by way of ML), and compile-time macros.

The language is influenced by several modern programming languages, such
as Haskell, Standard ML, Elm, Self, Dylan, Clojure, Magpie, and
Racket. While the syntax is a mixture of Smalltalk and Haskell, the
semantics are mostly derived from Lisp and ML. Particularly, Harmonia
prefers constructs that are easier to optimise and reason about given a
static type system, and so uses strict evaluation semantics.

This document defines the formal syntax for Harmonia, and provides an
informal description of the semantics for the constructs in the
language.


## 1.1) Program structure

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


## 1.2) Values and types

Every expression evaluates to a value, and has an associated static
type. Values and types do not share the same namespace in Harmonia, and
you can not treat types as regular values. None the less, the language
allows for user-defined data types, and supports parametric
polymorphism, row polymorphism, and ad-hoc polymorphism through
multi-methods.


## 1.3) Namespaces

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

## 2.1) Comments

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


## 2.2) Identifiers and operators

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
         

variable = small 
```
    


## 1) Overview of Harmonia

Harmonia is an programming language supporting both object-oriented and
functional programming idioms. It provides a rich static type system to
aid the programmer enforcing compositional constraints, and keeping
their code modular.


    (- [String] -> IO Unit -)
    main: args = ("Hello, " ++ args first) print

    λ> harmonia hello-world.harm


The language uses a Smalltalk-inspired syntax, with a certain influence
of Haskell and Lisps as well. Multi-methods are the bread and butter of
the language, and allow generic computations to be expressed with ease.

   
    data Maybe a = Nothing | Just: a

    Nothing map: transform = Nothing
    (Just: a) map: transform = Just: (a transform)


More complex objects are represented by
[extensible records with scoped labels](http://research.microsoft.com/pubs/65409/scopedlabels.pdf),
and methods on these objects are defined structurally through
row-polymorphism:


    type Named a = { a | name: String }

    alice = { name = "Alice P. Hacker", age = 12 }

    (- Named a -> String -)
    this first-name = this.name words first

    main: _ = alice first-name print  -- > "Alice"

    
Modules are similar to ML's, where you have signatures and
implementations. Modules can access anything in the lexical scope, and
are parametric for implementation details. No module but the main one
has access to the lobby, it's impossible for one module to load other
modules, unless you explicitly allow them to do that. This improves
security by capability, and allows Harmonia to be used for things like
configuration files without worrying about what those files can do.


    module set-signature where
      type Element
      type Set
      empty -> Set
      Set add: Element -> Set
      Element is-member-of: Set -> Boolean

    module set for: Element with [set-signature] where
      type Set = [Element]

      empty = []

      [] add: x = [x]
      (y . ys) add: x = given
                          | x == y    => y . ys
                          | x < y     => x . y . ys
                          | otherwise => y . (ys add: x)

      x is-member-of: [] = false
      x is-member-of: (y . ys) = y == x or: (y < x and: (x is-member-of: ys))


    module int-set = set for: Int
    open int-set
    
    empty add: 1 |> add: 2 |> add: 3



As it is to be expected of a functional language, Harmonia has closures in the
same fashion as JS does. And you get a nice syntax for creating anonymous
functions:


    [-2, -1, 0, 1, 2] filter: #(_ >= 0)                 -- short form
    [-2, -1, 0, 1, 2] filter: function x = x >= 0       -- long form
    -- > [1, 2]


And, yes! Unicode symbols are a go in your message names. Do note, however,
that Harmonia has no concepts of precedence between operators, so you have to
group the expressions yourself:

    6 - 2 * 2   -- > 8
    6 - (2 * 2) -- > 2
    
> Oh, did I tell you that you can use scheme-like symbols? Like: `is-number?`?
> Because you **TOTALLY CAN** :)


## 2) Concepts




## 3) Program structure

## 4) Standard library

## 5) Supporting tools

## 6) Formal syntax

## 7) Formal semantics

