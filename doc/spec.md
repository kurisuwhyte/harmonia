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

    Nothing map: f = Nothing
    (Just: a) map: f = Just: (f apply: a)


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


    signature Set where
      type Element
      type Set
      empty -> Set
      Set add: Element -> Set
      Element is-member-of: Set -> Boolean


    module Set for: Element implementing Set where
      type Set = [Element]

      empty = []

      [] add: x = [x]
      (y . ys) add: x = given
                        | x == y    => y . ys
                        | x < y     => x . y . ys
                        | otherwise => y . (ys add: x)

      x is-member-of: [] = false
      x is-member-of: (y . ys) = (y == x) or: (y < x and: (x is-member-of: ys))


    module IntSet = Set for: Int
    open IntSet
    
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

