(ns harmonia.parser
  (:require [instaparse.core :as insta]))

(def grammar
  (insta/parser
   "
(* Provides the base structure for higher-level syntactical constructs *)
<lexeme-break> = white-space
               | #'^'
               | #'$'
               ;
<white-space>  = #'\s'
               ;
<new-line>     = '\r\n'
               | '\r'
               | '\n'
               | '\f'
               ;
special        = '(' | ')' | '[' | ']' | '{' | '}' | ':' | '`' | ',' | '_'
               ;
letter         = #'[a-zA-Z]'
               ;
digit          = #'[0-9]'
               ;
symbol         = '!' | '#' | '$' | '%'   | '&' | '*'  | '+' | '/'
               | '<' | '=' | '>' | '?'   | '@' | '\\' | '^' | '|'
               | '-' | '~' | '´' | \"'\" | '.'
               ;
octal-digit    = #'[0-7]'
               ;
hex-digit      = #'[a-fA-F0-9]'
               ;

(* Comments *)
<comment> = lexeme-break '--' lexeme-break #'[^\r\n]' new-line
          ;

(* Identifiers and operators *)
reserved = 'data'
         | 'type'
         | 'module'
         | 'where'
         | 'let'
         | 'open'
         | 'with'
         | 'case'
         | 'of'
         | 'deriving'
         | 'do'
         | '='
         | '->'
         | '|>'
         
identifier-start = letter
identifier-rest  = letter | digit | symbol
identifier       = identifier-start identifier-rest* !':'
keyword          = identifier ':'

"
   ))

