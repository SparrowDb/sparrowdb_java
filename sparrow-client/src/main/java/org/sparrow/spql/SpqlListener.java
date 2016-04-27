
    package org.sparrow.spql;

	import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SpqlParser}.
 */
public interface SpqlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SpqlParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(SpqlParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(SpqlParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#sql_stmt_list}.
	 * @param ctx the parse tree
	 */
	void enterSql_stmt_list(SpqlParser.Sql_stmt_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#sql_stmt_list}.
	 * @param ctx the parse tree
	 */
	void exitSql_stmt_list(SpqlParser.Sql_stmt_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#sql_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSql_stmt(SpqlParser.Sql_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#sql_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSql_stmt(SpqlParser.Sql_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#create_database_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCreate_database_stmt(SpqlParser.Create_database_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#create_database_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCreate_database_stmt(SpqlParser.Create_database_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#drop_database_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDrop_database_stmt(SpqlParser.Drop_database_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#drop_database_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDrop_database_stmt(SpqlParser.Drop_database_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#show_databases_stmt}.
	 * @param ctx the parse tree
	 */
	void enterShow_databases_stmt(SpqlParser.Show_databases_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#show_databases_stmt}.
	 * @param ctx the parse tree
	 */
	void exitShow_databases_stmt(SpqlParser.Show_databases_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#insert_stmt}.
	 * @param ctx the parse tree
	 */
	void enterInsert_stmt(SpqlParser.Insert_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#insert_stmt}.
	 * @param ctx the parse tree
	 */
	void exitInsert_stmt(SpqlParser.Insert_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#token_list}.
	 * @param ctx the parse tree
	 */
	void enterToken_list(SpqlParser.Token_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#token_list}.
	 * @param ctx the parse tree
	 */
	void exitToken_list(SpqlParser.Token_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#delete_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDelete_stmt(SpqlParser.Delete_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#delete_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDelete_stmt(SpqlParser.Delete_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#select_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSelect_stmt(SpqlParser.Select_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#select_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSelect_stmt(SpqlParser.Select_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#token_name}.
	 * @param ctx the parse tree
	 */
	void enterToken_name(SpqlParser.Token_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#token_name}.
	 * @param ctx the parse tree
	 */
	void exitToken_name(SpqlParser.Token_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#database_name}.
	 * @param ctx the parse tree
	 */
	void enterDatabase_name(SpqlParser.Database_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#database_name}.
	 * @param ctx the parse tree
	 */
	void exitDatabase_name(SpqlParser.Database_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#key_name}.
	 * @param ctx the parse tree
	 */
	void enterKey_name(SpqlParser.Key_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#key_name}.
	 * @param ctx the parse tree
	 */
	void exitKey_name(SpqlParser.Key_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#key_value}.
	 * @param ctx the parse tree
	 */
	void enterKey_value(SpqlParser.Key_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#key_value}.
	 * @param ctx the parse tree
	 */
	void exitKey_value(SpqlParser.Key_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#any_name}.
	 * @param ctx the parse tree
	 */
	void enterAny_name(SpqlParser.Any_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#any_name}.
	 * @param ctx the parse tree
	 */
	void exitAny_name(SpqlParser.Any_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SpqlParser#keyword}.
	 * @param ctx the parse tree
	 */
	void enterKeyword(SpqlParser.KeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link SpqlParser#keyword}.
	 * @param ctx the parse tree
	 */
	void exitKeyword(SpqlParser.KeywordContext ctx);
}