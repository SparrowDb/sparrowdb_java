grammar Spql;

options {
    language = Java;
}

@header {
    package org.sparrow.spql;
    import java.util.ArrayList;
}

@members {
    public interface ISpqlParseHandler {
        void createDatabase(String dbName);
        void deleteStatement(String dbName, String keyName, String valueName);
        void dropDatabase(String dbName);
        void insertStatement(String dbName, ArrayList<String> tokens);
        void selectStatement(String dbName, String keyName, String valueName);
        void showDatabases();
    }
    SpqlParser.ISpqlParseHandler parserHandler;

    public void setHandler(SpqlParser.ISpqlParseHandler parserHandler) {
        this.parserHandler = parserHandler;
    }
}

parse
 : ( sql_stmt_list  )* EOF
 ;

sql_stmt_list
 : ';'* sql_stmt ( ';'+ sql_stmt )* ';'*
 ;

sql_stmt
 : ( create_database_stmt
    | drop_database_stmt
    | show_databases_stmt
    | insert_stmt
    | delete_stmt
    | select_stmt
    )
;

create_database_stmt
 : K_CREATE K_DATABASE database_name
    { parserHandler.createDatabase($database_name.text); }
 ;

drop_database_stmt
 : K_DROP K_DATABASE database_name
    { parserHandler.dropDatabase($database_name.text); }
 ;

show_databases_stmt
 : K_SHOW K_DATABASES
    { parserHandler.showDatabases(); }
 ;

insert_stmt
    @init {
            List<String> tokens = new ArrayList<>();
    }
 : K_INSERT K_INTO database_name '(' tks=token_list ')'
    { parserHandler.insertStatement($database_name.text, $tks.tokens); }
 ;

token_list returns [ArrayList<String> tokens]
    :{ $tokens = new ArrayList<String>(); }
    token_name { $tokens.add($token_name.text); } ( ','  token_name { $tokens.add($token_name.text); } )*
;

delete_stmt
 : K_DELETE K_FROM database_name K_WHERE key_name '=' key_value
    { parserHandler.deleteStatement($database_name.text, $key_name.text, $key_value.text); }
 ;

select_stmt
 : K_SELECT K_FROM database_name ( K_WHERE key_name '=' key_value )?
    { parserHandler.selectStatement($database_name.text, $key_name.text, $key_value.text); }
 ;

K_CREATE : C R E A T E;
K_DROP: D R O P;
K_DATABASE : D A T A B A S E;
K_DATABASES : D A T A B A S E S;
K_SHOW : S H O W;
K_SELECT : S E L E C T;
K_INSERT : I N S E R T;
K_INTO : I N T O;
K_DELETE : D E L E T E;
K_FROM : F R O M;
K_WHERE : W H E R E;

token_name
 : any_name
 | NUMERIC_LITERAL
 ;

database_name
 : any_name
 ;

key_name
 : any_name
 ;

key_value
 : any_name
 ;

any_name
 : IDENTIFIER
 | keyword
 | STRING_LITERAL
 | '(' any_name ')'
 ;

keyword
 : K_CREATE
    | K_DROP
    | K_DATABASE
    | K_DATABASES
    | K_SHOW
    | K_SELECT
    | K_INSERT
    | K_INTO
    | K_DELETE
    | K_FROM
    | K_WHERE
;

IDENTIFIER
 : '"' (~'"' | '""')* '"'
 | '`' (~'`' | '``')* '`'
 | '[' ~']'* ']'
 | [a-zA-Z_] [a-zA-Z_0-9]*
 ;

NUMERIC_LITERAL
 : DIGIT+ ( '.' DIGIT* )? ( E [-+]? DIGIT+ )?
 | '.' DIGIT+ ( E [-+]? DIGIT+ )?
 ;

BIND_PARAMETER
 : '?' DIGIT*
 | [:@$] IDENTIFIER
 ;

STRING_LITERAL
 : '\'' ( ~'\'' | '\'\'' )* '\''
 ;

BLOB_LITERAL
 : X STRING_LITERAL
 ;

SINGLE_LINE_COMMENT
 : '--' ~[\r\n]* -> channel(HIDDEN)
 ;

MULTILINE_COMMENT
 : '/*' .*? ( '*/' | EOF ) -> channel(HIDDEN)
 ;

SPACES
 : [ \u000B\t\r\n] -> channel(HIDDEN)
 ;

UNEXPECTED_CHAR
 : .
 ;

fragment DIGIT : [0-9];

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];