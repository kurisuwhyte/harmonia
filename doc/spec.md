Harmonia
========

This is a draft of the Harmonia's language specification.

Some features:

  - Rich static type system (yay generics!)
  - Safe: hosted, no null, no pointers, immutability by default
  - Ubiquitous: compile to JS, run everywhere
  - Object oriented by way of Smalltalk (everything is a message)
  - Sane multiple inheritance with traits
  - Functional: first-class functions, HOFs and all that
  - Meta-programming: compile-time macros (?)
  - Explicit parametrised dependencies.



## 0) Prelude

## 1) Overview of Harmonia

Harmonia is an object oriented/functional hybrid programming language, with a
rich static type system to aid the programmer in his quest for writing awesome
— and safe, and fast! — web applications.

Despite compiling down to JavaScript, Harmonia is a classical language, that
means the main building blocks we've got are classes:

    object World {
      value io: import "io"
      define main: args Array[String] => Unit = io print "Hello, world"
    }

    λ> harmonia hello-world.harm --entry-point World

Except for the entry-point object (which gets access to the Lobby by default),
objects in your application only get access to the things you give them access
to.

> By the way, the `object` is just a nice way to get a singleton class. We'll
> give you a World class an an instance of it automagically! Of course, the
> `class` declaration works as expectedly. And yes, this is a Scala rip-off :D

As it is to be expected of a functional language, Harmonia has closures in the
same fashion as JS does. And you get a nice syntax for creating anonymous
functions:

    [-2, -1, 0, 1, 2] filter: { x | x ≥ 0 }
    --> [1, 2]
    
And, yes! Unicode symbols are a go in your message names. Do note, however,
that Harmonia has no concepts of precedence between operators, so you have to
group the expressions yourself:

    6 - 2 * 2   --> 8
    6 - (2 * 2) --> 2
    
> Oh, did I tell you that you can use scheme-like symbols? Like: `is-number?`?
> Because you **TOTALLY CAN** :)

Traits allow you to get awesome multiple inheritance, and are where you should
put your reusable functionality. Think about them as classes that define how
they can be combined:

    trait Semi-Group[A] {
      define concat: other A => A
    }

A class including a trait must define all of the fields that a trait expects
(do note that traits can include an implementation of a particular field):

    class Promise[A] fork: (A => Promise[Either[B, C]])
    <: Semi-Group[A], Monoid[A], Functor[A], Monad[A] {

      :: Semi-Group[A]
      define concat: other A => A =
        chain: { a | other chain: { b | Promise[Array[A]] of: [a, b] }}

      -- ( ... )

    }

Besides `define`, you also get `override`, `expect`, `rename` & `map`. For
example, you could use `map` to transform a given trait's definition:


    object Auth {
      define ensure-permissions: f Function[Path, Boolean] =
        { p Path | User has-permission? then: (f call: p) }
    }

    class AuthenticatedRoute <: Route {
      map accept: p Path => Boolean = Auth ensure-permissions
    }

Last, but not least, you can encode algebraic datatypes through types. This is
a better take on Scala's case-classes:

    type List[A] = head: A tail: List[A]
                 | nil
    
    object Finder {

      define find: xs [List[A]] => Maybe[A] using: f (A => Bool) =
        match xs
        | nil              -> Nothing
        | head: x tail: ys -> (f x) then: (Just x)
                                    else: Finder find: ys using: f
        
    }


## 2) Concepts

## 3) Program structure

## 4) Standard library

## 5) Supporting tools

## 6) Formal syntax

## 7) Formal semantics

