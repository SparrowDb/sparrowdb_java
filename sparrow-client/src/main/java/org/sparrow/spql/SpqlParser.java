// Generated from Spql.g by ANTLR 4.5

    package org.sparrow.spql;
    import java.util.ArrayList;
    import java.util.HashMap;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SpqlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, K_CREATE=9, 
		K_DROP=10, K_DATABASE=11, K_DATABASES=12, K_SHOW=13, K_SELECT=14, K_INSERT=15, 
		K_INTO=16, K_DELETE=17, K_FROM=18, K_WHERE=19, IDENTIFIER=20, NUMERIC_LITERAL=21, 
		BIND_PARAMETER=22, STRING_LITERAL=23, BLOB_LITERAL=24, SINGLE_LINE_COMMENT=25, 
		MULTILINE_COMMENT=26, SPACES=27, UNEXPECTED_CHAR=28;
	public static final int
		RULE_parse = 0, RULE_sql_stmt_list = 1, RULE_sql_stmt = 2, RULE_create_database_stmt = 3, 
		RULE_drop_database_stmt = 4, RULE_show_databases_stmt = 5, RULE_insert_stmt = 6, 
		RULE_hash_list = 7, RULE_token_list = 8, RULE_delete_stmt = 9, RULE_select_stmt = 10, 
		RULE_token_name = 11, RULE_database_name = 12, RULE_key_name = 13, RULE_key_value = 14, 
		RULE_any_name = 15, RULE_keyword = 16;
	public static final String[] ruleNames = {
		"parse", "sql_stmt_list", "sql_stmt", "create_database_stmt", "drop_database_stmt", 
		"show_databases_stmt", "insert_stmt", "hash_list", "token_list", "delete_stmt", 
		"select_stmt", "token_name", "database_name", "key_name", "key_value", 
		"any_name", "keyword"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'{'", "'}'", "'('", "')'", "':'", "','", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, "K_CREATE", "K_DROP", 
		"K_DATABASE", "K_DATABASES", "K_SHOW", "K_SELECT", "K_INSERT", "K_INTO", 
		"K_DELETE", "K_FROM", "K_WHERE", "IDENTIFIER", "NUMERIC_LITERAL", "BIND_PARAMETER", 
		"STRING_LITERAL", "BLOB_LITERAL", "SINGLE_LINE_COMMENT", "MULTILINE_COMMENT", 
		"SPACES", "UNEXPECTED_CHAR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Spql.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    public interface ISpqlParseHandler {
	        void createDatabase(String dbName, HashMap<String, String> tokens);
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

	public SpqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ParseContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(SpqlParser.EOF, 0); }
		public List<Sql_stmt_listContext> sql_stmt_list() {
			return getRuleContexts(Sql_stmt_listContext.class);
		}
		public Sql_stmt_listContext sql_stmt_list(int i) {
			return getRuleContext(Sql_stmt_listContext.class,i);
		}
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitParse(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << K_CREATE) | (1L << K_DROP) | (1L << K_SHOW) | (1L << K_SELECT) | (1L << K_INSERT) | (1L << K_DELETE))) != 0)) {
				{
				{
				setState(34);
				sql_stmt_list();
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(40);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Sql_stmt_listContext extends ParserRuleContext {
		public List<Sql_stmtContext> sql_stmt() {
			return getRuleContexts(Sql_stmtContext.class);
		}
		public Sql_stmtContext sql_stmt(int i) {
			return getRuleContext(Sql_stmtContext.class,i);
		}
		public Sql_stmt_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sql_stmt_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterSql_stmt_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitSql_stmt_list(this);
		}
	}

	public final Sql_stmt_listContext sql_stmt_list() throws RecognitionException {
		Sql_stmt_listContext _localctx = new Sql_stmt_listContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sql_stmt_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(42);
				match(T__0);
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			sql_stmt();
			setState(57);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(50); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(49);
						match(T__0);
						}
						}
						setState(52); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__0 );
					setState(54);
					sql_stmt();
					}
					} 
				}
				setState(59);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(63);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(60);
					match(T__0);
					}
					} 
				}
				setState(65);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Sql_stmtContext extends ParserRuleContext {
		public Create_database_stmtContext create_database_stmt() {
			return getRuleContext(Create_database_stmtContext.class,0);
		}
		public Drop_database_stmtContext drop_database_stmt() {
			return getRuleContext(Drop_database_stmtContext.class,0);
		}
		public Show_databases_stmtContext show_databases_stmt() {
			return getRuleContext(Show_databases_stmtContext.class,0);
		}
		public Insert_stmtContext insert_stmt() {
			return getRuleContext(Insert_stmtContext.class,0);
		}
		public Delete_stmtContext delete_stmt() {
			return getRuleContext(Delete_stmtContext.class,0);
		}
		public Select_stmtContext select_stmt() {
			return getRuleContext(Select_stmtContext.class,0);
		}
		public Sql_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sql_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterSql_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitSql_stmt(this);
		}
	}

	public final Sql_stmtContext sql_stmt() throws RecognitionException {
		Sql_stmtContext _localctx = new Sql_stmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_sql_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			switch (_input.LA(1)) {
			case K_CREATE:
				{
				setState(66);
				create_database_stmt();
				}
				break;
			case K_DROP:
				{
				setState(67);
				drop_database_stmt();
				}
				break;
			case K_SHOW:
				{
				setState(68);
				show_databases_stmt();
				}
				break;
			case K_INSERT:
				{
				setState(69);
				insert_stmt();
				}
				break;
			case K_DELETE:
				{
				setState(70);
				delete_stmt();
				}
				break;
			case K_SELECT:
				{
				setState(71);
				select_stmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Create_database_stmtContext extends ParserRuleContext {
		public Database_nameContext database_name;
		public Hash_listContext tks;
		public TerminalNode K_CREATE() { return getToken(SpqlParser.K_CREATE, 0); }
		public TerminalNode K_DATABASE() { return getToken(SpqlParser.K_DATABASE, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
		}
		public Hash_listContext hash_list() {
			return getRuleContext(Hash_listContext.class,0);
		}
		public Create_database_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_create_database_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterCreate_database_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitCreate_database_stmt(this);
		}
	}

	public final Create_database_stmtContext create_database_stmt() throws RecognitionException {
		Create_database_stmtContext _localctx = new Create_database_stmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_create_database_stmt);

		            HashMap<String, String> tokens = new HashMap<>();
		    
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(K_CREATE);
			setState(75);
			match(K_DATABASE);
			setState(76);
			((Create_database_stmtContext)_localctx).database_name = database_name();
			setState(82);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(77);
				match(T__1);
				setState(78);
				((Create_database_stmtContext)_localctx).tks = hash_list();
				setState(79);
				match(T__2);
				 tokens = ((Create_database_stmtContext)_localctx).tks.tokens;
				}
			}

			 parserHandler.createDatabase((((Create_database_stmtContext)_localctx).database_name!=null?_input.getText(((Create_database_stmtContext)_localctx).database_name.start,((Create_database_stmtContext)_localctx).database_name.stop):null), tokens); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Drop_database_stmtContext extends ParserRuleContext {
		public Database_nameContext database_name;
		public TerminalNode K_DROP() { return getToken(SpqlParser.K_DROP, 0); }
		public TerminalNode K_DATABASE() { return getToken(SpqlParser.K_DATABASE, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
		}
		public Drop_database_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_drop_database_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterDrop_database_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitDrop_database_stmt(this);
		}
	}

	public final Drop_database_stmtContext drop_database_stmt() throws RecognitionException {
		Drop_database_stmtContext _localctx = new Drop_database_stmtContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_drop_database_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(K_DROP);
			setState(87);
			match(K_DATABASE);
			setState(88);
			((Drop_database_stmtContext)_localctx).database_name = database_name();
			 parserHandler.dropDatabase((((Drop_database_stmtContext)_localctx).database_name!=null?_input.getText(((Drop_database_stmtContext)_localctx).database_name.start,((Drop_database_stmtContext)_localctx).database_name.stop):null)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Show_databases_stmtContext extends ParserRuleContext {
		public TerminalNode K_SHOW() { return getToken(SpqlParser.K_SHOW, 0); }
		public TerminalNode K_DATABASES() { return getToken(SpqlParser.K_DATABASES, 0); }
		public Show_databases_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_show_databases_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterShow_databases_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitShow_databases_stmt(this);
		}
	}

	public final Show_databases_stmtContext show_databases_stmt() throws RecognitionException {
		Show_databases_stmtContext _localctx = new Show_databases_stmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_show_databases_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(K_SHOW);
			setState(92);
			match(K_DATABASES);
			 parserHandler.showDatabases(); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Insert_stmtContext extends ParserRuleContext {
		public Database_nameContext database_name;
		public Token_listContext tks;
		public TerminalNode K_INSERT() { return getToken(SpqlParser.K_INSERT, 0); }
		public TerminalNode K_INTO() { return getToken(SpqlParser.K_INTO, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
		}
		public Token_listContext token_list() {
			return getRuleContext(Token_listContext.class,0);
		}
		public Insert_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insert_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterInsert_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitInsert_stmt(this);
		}
	}

	public final Insert_stmtContext insert_stmt() throws RecognitionException {
		Insert_stmtContext _localctx = new Insert_stmtContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_insert_stmt);

		            List<String> tokens = new ArrayList<>();
		    
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(K_INSERT);
			setState(96);
			match(K_INTO);
			setState(97);
			((Insert_stmtContext)_localctx).database_name = database_name();
			setState(98);
			match(T__3);
			setState(99);
			((Insert_stmtContext)_localctx).tks = token_list();
			setState(100);
			match(T__4);
			 parserHandler.insertStatement((((Insert_stmtContext)_localctx).database_name!=null?_input.getText(((Insert_stmtContext)_localctx).database_name.start,((Insert_stmtContext)_localctx).database_name.stop):null), ((Insert_stmtContext)_localctx).tks.tokens); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Hash_listContext extends ParserRuleContext {
		public HashMap<String, String> tokens;
		public Token_nameContext key;
		public Token_nameContext val;
		public List<Token_nameContext> token_name() {
			return getRuleContexts(Token_nameContext.class);
		}
		public Token_nameContext token_name(int i) {
			return getRuleContext(Token_nameContext.class,i);
		}
		public Hash_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hash_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterHash_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitHash_list(this);
		}
	}

	public final Hash_listContext hash_list() throws RecognitionException {
		Hash_listContext _localctx = new Hash_listContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_hash_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((Hash_listContext)_localctx).tokens =  new HashMap<String, String>(); 
			setState(104);
			((Hash_listContext)_localctx).key = token_name();
			setState(105);
			match(T__5);
			setState(106);
			((Hash_listContext)_localctx).val = token_name();
			 _localctx.tokens.put((((Hash_listContext)_localctx).key!=null?_input.getText(((Hash_listContext)_localctx).key.start,((Hash_listContext)_localctx).key.stop):null), (((Hash_listContext)_localctx).val!=null?_input.getText(((Hash_listContext)_localctx).val.start,((Hash_listContext)_localctx).val.stop):null)); 
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(108);
				match(T__6);
				setState(109);
				((Hash_listContext)_localctx).key = token_name();
				setState(110);
				match(T__5);
				setState(111);
				((Hash_listContext)_localctx).val = token_name();
				 _localctx.tokens.put((((Hash_listContext)_localctx).key!=null?_input.getText(((Hash_listContext)_localctx).key.start,((Hash_listContext)_localctx).key.stop):null), (((Hash_listContext)_localctx).val!=null?_input.getText(((Hash_listContext)_localctx).val.start,((Hash_listContext)_localctx).val.stop):null)); 
				}
				}
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Token_listContext extends ParserRuleContext {
		public ArrayList<String> tokens;
		public Token_nameContext token_name;
		public List<Token_nameContext> token_name() {
			return getRuleContexts(Token_nameContext.class);
		}
		public Token_nameContext token_name(int i) {
			return getRuleContext(Token_nameContext.class,i);
		}
		public Token_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_token_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterToken_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitToken_list(this);
		}
	}

	public final Token_listContext token_list() throws RecognitionException {
		Token_listContext _localctx = new Token_listContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_token_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((Token_listContext)_localctx).tokens =  new ArrayList<String>(); 
			setState(120);
			((Token_listContext)_localctx).token_name = token_name();
			 _localctx.tokens.add((((Token_listContext)_localctx).token_name!=null?_input.getText(((Token_listContext)_localctx).token_name.start,((Token_listContext)_localctx).token_name.stop):null)); 
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(122);
				match(T__6);
				setState(123);
				((Token_listContext)_localctx).token_name = token_name();
				 _localctx.tokens.add((((Token_listContext)_localctx).token_name!=null?_input.getText(((Token_listContext)_localctx).token_name.start,((Token_listContext)_localctx).token_name.stop):null)); 
				}
				}
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Delete_stmtContext extends ParserRuleContext {
		public Database_nameContext database_name;
		public Key_nameContext key_name;
		public Key_valueContext key_value;
		public TerminalNode K_DELETE() { return getToken(SpqlParser.K_DELETE, 0); }
		public TerminalNode K_FROM() { return getToken(SpqlParser.K_FROM, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
		}
		public TerminalNode K_WHERE() { return getToken(SpqlParser.K_WHERE, 0); }
		public Key_nameContext key_name() {
			return getRuleContext(Key_nameContext.class,0);
		}
		public Key_valueContext key_value() {
			return getRuleContext(Key_valueContext.class,0);
		}
		public Delete_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delete_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterDelete_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitDelete_stmt(this);
		}
	}

	public final Delete_stmtContext delete_stmt() throws RecognitionException {
		Delete_stmtContext _localctx = new Delete_stmtContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_delete_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(K_DELETE);
			setState(132);
			match(K_FROM);
			setState(133);
			((Delete_stmtContext)_localctx).database_name = database_name();
			setState(134);
			match(K_WHERE);
			setState(135);
			((Delete_stmtContext)_localctx).key_name = key_name();
			setState(136);
			match(T__7);
			setState(137);
			((Delete_stmtContext)_localctx).key_value = key_value();
			 parserHandler.deleteStatement((((Delete_stmtContext)_localctx).database_name!=null?_input.getText(((Delete_stmtContext)_localctx).database_name.start,((Delete_stmtContext)_localctx).database_name.stop):null), (((Delete_stmtContext)_localctx).key_name!=null?_input.getText(((Delete_stmtContext)_localctx).key_name.start,((Delete_stmtContext)_localctx).key_name.stop):null), (((Delete_stmtContext)_localctx).key_value!=null?_input.getText(((Delete_stmtContext)_localctx).key_value.start,((Delete_stmtContext)_localctx).key_value.stop):null)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_stmtContext extends ParserRuleContext {
		public Database_nameContext database_name;
		public Key_nameContext key_name;
		public Key_valueContext key_value;
		public TerminalNode K_SELECT() { return getToken(SpqlParser.K_SELECT, 0); }
		public TerminalNode K_FROM() { return getToken(SpqlParser.K_FROM, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
		}
		public TerminalNode K_WHERE() { return getToken(SpqlParser.K_WHERE, 0); }
		public Key_nameContext key_name() {
			return getRuleContext(Key_nameContext.class,0);
		}
		public Key_valueContext key_value() {
			return getRuleContext(Key_valueContext.class,0);
		}
		public Select_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterSelect_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitSelect_stmt(this);
		}
	}

	public final Select_stmtContext select_stmt() throws RecognitionException {
		Select_stmtContext _localctx = new Select_stmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_select_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(K_SELECT);
			setState(141);
			match(K_FROM);
			setState(142);
			((Select_stmtContext)_localctx).database_name = database_name();
			setState(148);
			_la = _input.LA(1);
			if (_la==K_WHERE) {
				{
				setState(143);
				match(K_WHERE);
				setState(144);
				((Select_stmtContext)_localctx).key_name = key_name();
				setState(145);
				match(T__7);
				setState(146);
				((Select_stmtContext)_localctx).key_value = key_value();
				}
			}

			 parserHandler.selectStatement((((Select_stmtContext)_localctx).database_name!=null?_input.getText(((Select_stmtContext)_localctx).database_name.start,((Select_stmtContext)_localctx).database_name.stop):null), (((Select_stmtContext)_localctx).key_name!=null?_input.getText(((Select_stmtContext)_localctx).key_name.start,((Select_stmtContext)_localctx).key_name.stop):null), (((Select_stmtContext)_localctx).key_value!=null?_input.getText(((Select_stmtContext)_localctx).key_value.start,((Select_stmtContext)_localctx).key_value.stop):null)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Token_nameContext extends ParserRuleContext {
		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class,0);
		}
		public TerminalNode NUMERIC_LITERAL() { return getToken(SpqlParser.NUMERIC_LITERAL, 0); }
		public Token_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_token_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterToken_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitToken_name(this);
		}
	}

	public final Token_nameContext token_name() throws RecognitionException {
		Token_nameContext _localctx = new Token_nameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_token_name);
		try {
			setState(154);
			switch (_input.LA(1)) {
			case T__3:
			case K_CREATE:
			case K_DROP:
			case K_DATABASE:
			case K_DATABASES:
			case K_SHOW:
			case K_SELECT:
			case K_INSERT:
			case K_INTO:
			case K_DELETE:
			case K_FROM:
			case K_WHERE:
			case IDENTIFIER:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				any_name();
				}
				break;
			case NUMERIC_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(153);
				match(NUMERIC_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Database_nameContext extends ParserRuleContext {
		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class,0);
		}
		public Database_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_database_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterDatabase_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitDatabase_name(this);
		}
	}

	public final Database_nameContext database_name() throws RecognitionException {
		Database_nameContext _localctx = new Database_nameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_database_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			any_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Key_nameContext extends ParserRuleContext {
		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class,0);
		}
		public Key_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterKey_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitKey_name(this);
		}
	}

	public final Key_nameContext key_name() throws RecognitionException {
		Key_nameContext _localctx = new Key_nameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_key_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			any_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Key_valueContext extends ParserRuleContext {
		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class,0);
		}
		public Key_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterKey_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitKey_value(this);
		}
	}

	public final Key_valueContext key_value() throws RecognitionException {
		Key_valueContext _localctx = new Key_valueContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_key_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			any_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Any_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SpqlParser.IDENTIFIER, 0); }
		public KeywordContext keyword() {
			return getRuleContext(KeywordContext.class,0);
		}
		public TerminalNode STRING_LITERAL() { return getToken(SpqlParser.STRING_LITERAL, 0); }
		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class,0);
		}
		public Any_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_any_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterAny_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitAny_name(this);
		}
	}

	public final Any_nameContext any_name() throws RecognitionException {
		Any_nameContext _localctx = new Any_nameContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_any_name);
		try {
			setState(169);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(162);
				match(IDENTIFIER);
				}
				break;
			case K_CREATE:
			case K_DROP:
			case K_DATABASE:
			case K_DATABASES:
			case K_SHOW:
			case K_SELECT:
			case K_INSERT:
			case K_INTO:
			case K_DELETE:
			case K_FROM:
			case K_WHERE:
				enterOuterAlt(_localctx, 2);
				{
				setState(163);
				keyword();
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(164);
				match(STRING_LITERAL);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 4);
				{
				setState(165);
				match(T__3);
				setState(166);
				any_name();
				setState(167);
				match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeywordContext extends ParserRuleContext {
		public TerminalNode K_CREATE() { return getToken(SpqlParser.K_CREATE, 0); }
		public TerminalNode K_DROP() { return getToken(SpqlParser.K_DROP, 0); }
		public TerminalNode K_DATABASE() { return getToken(SpqlParser.K_DATABASE, 0); }
		public TerminalNode K_DATABASES() { return getToken(SpqlParser.K_DATABASES, 0); }
		public TerminalNode K_SHOW() { return getToken(SpqlParser.K_SHOW, 0); }
		public TerminalNode K_SELECT() { return getToken(SpqlParser.K_SELECT, 0); }
		public TerminalNode K_INSERT() { return getToken(SpqlParser.K_INSERT, 0); }
		public TerminalNode K_INTO() { return getToken(SpqlParser.K_INTO, 0); }
		public TerminalNode K_DELETE() { return getToken(SpqlParser.K_DELETE, 0); }
		public TerminalNode K_FROM() { return getToken(SpqlParser.K_FROM, 0); }
		public TerminalNode K_WHERE() { return getToken(SpqlParser.K_WHERE, 0); }
		public KeywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).enterKeyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SpqlListener ) ((SpqlListener)listener).exitKeyword(this);
		}
	}

	public final KeywordContext keyword() throws RecognitionException {
		KeywordContext _localctx = new KeywordContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_keyword);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << K_CREATE) | (1L << K_DROP) | (1L << K_DATABASE) | (1L << K_DATABASES) | (1L << K_SHOW) | (1L << K_SELECT) | (1L << K_INSERT) | (1L << K_INTO) | (1L << K_DELETE) | (1L << K_FROM) | (1L << K_WHERE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\36\u00b0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\7\2&\n\2\f\2\16\2)\13\2\3\2\3\2\3\3\7\3.\n\3\f\3\16\3\61\13\3\3\3"+
		"\3\3\6\3\65\n\3\r\3\16\3\66\3\3\7\3:\n\3\f\3\16\3=\13\3\3\3\7\3@\n\3\f"+
		"\3\16\3C\13\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4K\n\4\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\5\5U\n\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7"+
		"\tu\n\t\f\t\16\tx\13\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u0081\n\n\f\n\16"+
		"\n\u0084\13\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\5\f\u0097\n\f\3\f\3\f\3\r\3\r\5\r\u009d\n\r\3\16"+
		"\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00ac"+
		"\n\21\3\22\3\22\3\22\2\2\23\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \""+
		"\2\3\3\2\13\25\u00b0\2\'\3\2\2\2\4/\3\2\2\2\6J\3\2\2\2\bL\3\2\2\2\nX\3"+
		"\2\2\2\f]\3\2\2\2\16a\3\2\2\2\20i\3\2\2\2\22y\3\2\2\2\24\u0085\3\2\2\2"+
		"\26\u008e\3\2\2\2\30\u009c\3\2\2\2\32\u009e\3\2\2\2\34\u00a0\3\2\2\2\36"+
		"\u00a2\3\2\2\2 \u00ab\3\2\2\2\"\u00ad\3\2\2\2$&\5\4\3\2%$\3\2\2\2&)\3"+
		"\2\2\2\'%\3\2\2\2\'(\3\2\2\2(*\3\2\2\2)\'\3\2\2\2*+\7\2\2\3+\3\3\2\2\2"+
		",.\7\3\2\2-,\3\2\2\2.\61\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61"+
		"/\3\2\2\2\62;\5\6\4\2\63\65\7\3\2\2\64\63\3\2\2\2\65\66\3\2\2\2\66\64"+
		"\3\2\2\2\66\67\3\2\2\2\678\3\2\2\28:\5\6\4\29\64\3\2\2\2:=\3\2\2\2;9\3"+
		"\2\2\2;<\3\2\2\2<A\3\2\2\2=;\3\2\2\2>@\7\3\2\2?>\3\2\2\2@C\3\2\2\2A?\3"+
		"\2\2\2AB\3\2\2\2B\5\3\2\2\2CA\3\2\2\2DK\5\b\5\2EK\5\n\6\2FK\5\f\7\2GK"+
		"\5\16\b\2HK\5\24\13\2IK\5\26\f\2JD\3\2\2\2JE\3\2\2\2JF\3\2\2\2JG\3\2\2"+
		"\2JH\3\2\2\2JI\3\2\2\2K\7\3\2\2\2LM\7\13\2\2MN\7\r\2\2NT\5\32\16\2OP\7"+
		"\4\2\2PQ\5\20\t\2QR\7\5\2\2RS\b\5\1\2SU\3\2\2\2TO\3\2\2\2TU\3\2\2\2UV"+
		"\3\2\2\2VW\b\5\1\2W\t\3\2\2\2XY\7\f\2\2YZ\7\r\2\2Z[\5\32\16\2[\\\b\6\1"+
		"\2\\\13\3\2\2\2]^\7\17\2\2^_\7\16\2\2_`\b\7\1\2`\r\3\2\2\2ab\7\21\2\2"+
		"bc\7\22\2\2cd\5\32\16\2de\7\6\2\2ef\5\22\n\2fg\7\7\2\2gh\b\b\1\2h\17\3"+
		"\2\2\2ij\b\t\1\2jk\5\30\r\2kl\7\b\2\2lm\5\30\r\2mv\b\t\1\2no\7\t\2\2o"+
		"p\5\30\r\2pq\7\b\2\2qr\5\30\r\2rs\b\t\1\2su\3\2\2\2tn\3\2\2\2ux\3\2\2"+
		"\2vt\3\2\2\2vw\3\2\2\2w\21\3\2\2\2xv\3\2\2\2yz\b\n\1\2z{\5\30\r\2{\u0082"+
		"\b\n\1\2|}\7\t\2\2}~\5\30\r\2~\177\b\n\1\2\177\u0081\3\2\2\2\u0080|\3"+
		"\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083"+
		"\23\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0086\7\23\2\2\u0086\u0087\7\24"+
		"\2\2\u0087\u0088\5\32\16\2\u0088\u0089\7\25\2\2\u0089\u008a\5\34\17\2"+
		"\u008a\u008b\7\n\2\2\u008b\u008c\5\36\20\2\u008c\u008d\b\13\1\2\u008d"+
		"\25\3\2\2\2\u008e\u008f\7\20\2\2\u008f\u0090\7\24\2\2\u0090\u0096\5\32"+
		"\16\2\u0091\u0092\7\25\2\2\u0092\u0093\5\34\17\2\u0093\u0094\7\n\2\2\u0094"+
		"\u0095\5\36\20\2\u0095\u0097\3\2\2\2\u0096\u0091\3\2\2\2\u0096\u0097\3"+
		"\2\2\2\u0097\u0098\3\2\2\2\u0098\u0099\b\f\1\2\u0099\27\3\2\2\2\u009a"+
		"\u009d\5 \21\2\u009b\u009d\7\27\2\2\u009c\u009a\3\2\2\2\u009c\u009b\3"+
		"\2\2\2\u009d\31\3\2\2\2\u009e\u009f\5 \21\2\u009f\33\3\2\2\2\u00a0\u00a1"+
		"\5 \21\2\u00a1\35\3\2\2\2\u00a2\u00a3\5 \21\2\u00a3\37\3\2\2\2\u00a4\u00ac"+
		"\7\26\2\2\u00a5\u00ac\5\"\22\2\u00a6\u00ac\7\31\2\2\u00a7\u00a8\7\6\2"+
		"\2\u00a8\u00a9\5 \21\2\u00a9\u00aa\7\7\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a4"+
		"\3\2\2\2\u00ab\u00a5\3\2\2\2\u00ab\u00a6\3\2\2\2\u00ab\u00a7\3\2\2\2\u00ac"+
		"!\3\2\2\2\u00ad\u00ae\t\2\2\2\u00ae#\3\2\2\2\16\'/\66;AJTv\u0082\u0096"+
		"\u009c\u00ab";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}