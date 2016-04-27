
    package org.sparrow.spql;

	import org.antlr.v4.runtime.*;
	import org.antlr.v4.runtime.atn.ATN;
	import org.antlr.v4.runtime.atn.ATNDeserializer;
	import org.antlr.v4.runtime.atn.ParserATNSimulator;
	import org.antlr.v4.runtime.atn.PredictionContextCache;
	import org.antlr.v4.runtime.dfa.DFA;
	import org.antlr.v4.runtime.tree.ParseTreeListener;
	import org.antlr.v4.runtime.tree.TerminalNode;

	import java.util.ArrayList;
	import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SpqlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, K_CREATE=6, K_DROP=7, K_DATABASE=8, 
		K_DATABASES=9, K_SHOW=10, K_SELECT=11, K_INSERT=12, K_INTO=13, K_DELETE=14, 
		K_FROM=15, K_WHERE=16, IDENTIFIER=17, NUMERIC_LITERAL=18, BIND_PARAMETER=19, 
		STRING_LITERAL=20, BLOB_LITERAL=21, SINGLE_LINE_COMMENT=22, MULTILINE_COMMENT=23, 
		SPACES=24, UNEXPECTED_CHAR=25;
	public static final int
		RULE_parse = 0, RULE_sql_stmt_list = 1, RULE_sql_stmt = 2, RULE_create_database_stmt = 3, 
		RULE_drop_database_stmt = 4, RULE_show_databases_stmt = 5, RULE_insert_stmt = 6, 
		RULE_token_list = 7, RULE_delete_stmt = 8, RULE_select_stmt = 9, RULE_token_name = 10, 
		RULE_database_name = 11, RULE_key_name = 12, RULE_key_value = 13, RULE_any_name = 14, 
		RULE_keyword = 15;
	public static final String[] ruleNames = {
		"parse", "sql_stmt_list", "sql_stmt", "create_database_stmt", "drop_database_stmt", 
		"show_databases_stmt", "insert_stmt", "token_list", "delete_stmt", "select_stmt", 
		"token_name", "database_name", "key_name", "key_value", "any_name", "keyword"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'('", "')'", "','", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "K_CREATE", "K_DROP", "K_DATABASE", 
		"K_DATABASES", "K_SHOW", "K_SELECT", "K_INSERT", "K_INTO", "K_DELETE", 
		"K_FROM", "K_WHERE", "IDENTIFIER", "NUMERIC_LITERAL", "BIND_PARAMETER", 
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
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << K_CREATE) | (1L << K_DROP) | (1L << K_SHOW) | (1L << K_SELECT) | (1L << K_INSERT) | (1L << K_DELETE))) != 0)) {
				{
				{
				setState(32);
				sql_stmt_list();
				}
				}
				setState(37);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(38);
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
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(40);
				match(T__0);
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(46);
			sql_stmt();
			setState(55);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(48); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(47);
						match(T__0);
						}
						}
						setState(50); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__0 );
					setState(52);
					sql_stmt();
					}
					} 
				}
				setState(57);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(61);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(58);
					match(T__0);
					}
					} 
				}
				setState(63);
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
			setState(70);
			switch (_input.LA(1)) {
			case K_CREATE:
				{
				setState(64);
				create_database_stmt();
				}
				break;
			case K_DROP:
				{
				setState(65);
				drop_database_stmt();
				}
				break;
			case K_SHOW:
				{
				setState(66);
				show_databases_stmt();
				}
				break;
			case K_INSERT:
				{
				setState(67);
				insert_stmt();
				}
				break;
			case K_DELETE:
				{
				setState(68);
				delete_stmt();
				}
				break;
			case K_SELECT:
				{
				setState(69);
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
		public TerminalNode K_CREATE() { return getToken(SpqlParser.K_CREATE, 0); }
		public TerminalNode K_DATABASE() { return getToken(SpqlParser.K_DATABASE, 0); }
		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class,0);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(K_CREATE);
			setState(73);
			match(K_DATABASE);
			setState(74);
			((Create_database_stmtContext)_localctx).database_name = database_name();
			 parserHandler.createDatabase((((Create_database_stmtContext)_localctx).database_name!=null?_input.getText(((Create_database_stmtContext)_localctx).database_name.start,((Create_database_stmtContext)_localctx).database_name.stop):null)); 
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
			setState(77);
			match(K_DROP);
			setState(78);
			match(K_DATABASE);
			setState(79);
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
			setState(82);
			match(K_SHOW);
			setState(83);
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
			setState(86);
			match(K_INSERT);
			setState(87);
			match(K_INTO);
			setState(88);
			((Insert_stmtContext)_localctx).database_name = database_name();
			setState(89);
			match(T__1);
			setState(90);
			((Insert_stmtContext)_localctx).tks = token_list();
			setState(91);
			match(T__2);
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
		enterRule(_localctx, 14, RULE_token_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((Token_listContext)_localctx).tokens =  new ArrayList<String>(); 
			setState(95);
			((Token_listContext)_localctx).token_name = token_name();
			 _localctx.tokens.add((((Token_listContext)_localctx).token_name!=null?_input.getText(((Token_listContext)_localctx).token_name.start,((Token_listContext)_localctx).token_name.stop):null)); 
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(97);
				match(T__3);
				setState(98);
				((Token_listContext)_localctx).token_name = token_name();
				 _localctx.tokens.add((((Token_listContext)_localctx).token_name!=null?_input.getText(((Token_listContext)_localctx).token_name.start,((Token_listContext)_localctx).token_name.stop):null)); 
				}
				}
				setState(105);
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
		enterRule(_localctx, 16, RULE_delete_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(K_DELETE);
			setState(107);
			match(K_FROM);
			setState(108);
			((Delete_stmtContext)_localctx).database_name = database_name();
			setState(109);
			match(K_WHERE);
			setState(110);
			((Delete_stmtContext)_localctx).key_name = key_name();
			setState(111);
			match(T__4);
			setState(112);
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
		enterRule(_localctx, 18, RULE_select_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(K_SELECT);
			setState(116);
			match(K_FROM);
			setState(117);
			((Select_stmtContext)_localctx).database_name = database_name();
			setState(123);
			_la = _input.LA(1);
			if (_la==K_WHERE) {
				{
				setState(118);
				match(K_WHERE);
				setState(119);
				((Select_stmtContext)_localctx).key_name = key_name();
				setState(120);
				match(T__4);
				setState(121);
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
		enterRule(_localctx, 20, RULE_token_name);
		try {
			setState(129);
			switch (_input.LA(1)) {
			case T__1:
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
				setState(127);
				any_name();
				}
				break;
			case NUMERIC_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
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
		enterRule(_localctx, 22, RULE_database_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
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
		enterRule(_localctx, 24, RULE_key_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
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
		enterRule(_localctx, 26, RULE_key_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
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
		enterRule(_localctx, 28, RULE_any_name);
		try {
			setState(144);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(137);
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
				setState(138);
				keyword();
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(139);
				match(STRING_LITERAL);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 4);
				{
				setState(140);
				match(T__1);
				setState(141);
				any_name();
				setState(142);
				match(T__2);
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
		enterRule(_localctx, 30, RULE_keyword);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\33\u0097\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\7\2"+
		"$\n\2\f\2\16\2\'\13\2\3\2\3\2\3\3\7\3,\n\3\f\3\16\3/\13\3\3\3\3\3\6\3"+
		"\63\n\3\r\3\16\3\64\3\3\7\38\n\3\f\3\16\3;\13\3\3\3\7\3>\n\3\f\3\16\3"+
		"A\13\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4I\n\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\7\th\n\t\f\t\16\tk\13\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13~\n\13\3\13\3\13\3"+
		"\f\3\f\5\f\u0084\n\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\5\20\u0093\n\20\3\21\3\21\3\21\2\2\22\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \2\3\3\2\b\22\u0096\2%\3\2\2\2\4-\3\2\2\2\6H\3\2"+
		"\2\2\bJ\3\2\2\2\nO\3\2\2\2\fT\3\2\2\2\16X\3\2\2\2\20`\3\2\2\2\22l\3\2"+
		"\2\2\24u\3\2\2\2\26\u0083\3\2\2\2\30\u0085\3\2\2\2\32\u0087\3\2\2\2\34"+
		"\u0089\3\2\2\2\36\u0092\3\2\2\2 \u0094\3\2\2\2\"$\5\4\3\2#\"\3\2\2\2$"+
		"\'\3\2\2\2%#\3\2\2\2%&\3\2\2\2&(\3\2\2\2\'%\3\2\2\2()\7\2\2\3)\3\3\2\2"+
		"\2*,\7\3\2\2+*\3\2\2\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\60\3\2\2\2/-\3\2"+
		"\2\2\609\5\6\4\2\61\63\7\3\2\2\62\61\3\2\2\2\63\64\3\2\2\2\64\62\3\2\2"+
		"\2\64\65\3\2\2\2\65\66\3\2\2\2\668\5\6\4\2\67\62\3\2\2\28;\3\2\2\29\67"+
		"\3\2\2\29:\3\2\2\2:?\3\2\2\2;9\3\2\2\2<>\7\3\2\2=<\3\2\2\2>A\3\2\2\2?"+
		"=\3\2\2\2?@\3\2\2\2@\5\3\2\2\2A?\3\2\2\2BI\5\b\5\2CI\5\n\6\2DI\5\f\7\2"+
		"EI\5\16\b\2FI\5\22\n\2GI\5\24\13\2HB\3\2\2\2HC\3\2\2\2HD\3\2\2\2HE\3\2"+
		"\2\2HF\3\2\2\2HG\3\2\2\2I\7\3\2\2\2JK\7\b\2\2KL\7\n\2\2LM\5\30\r\2MN\b"+
		"\5\1\2N\t\3\2\2\2OP\7\t\2\2PQ\7\n\2\2QR\5\30\r\2RS\b\6\1\2S\13\3\2\2\2"+
		"TU\7\f\2\2UV\7\13\2\2VW\b\7\1\2W\r\3\2\2\2XY\7\16\2\2YZ\7\17\2\2Z[\5\30"+
		"\r\2[\\\7\4\2\2\\]\5\20\t\2]^\7\5\2\2^_\b\b\1\2_\17\3\2\2\2`a\b\t\1\2"+
		"ab\5\26\f\2bi\b\t\1\2cd\7\6\2\2de\5\26\f\2ef\b\t\1\2fh\3\2\2\2gc\3\2\2"+
		"\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2j\21\3\2\2\2ki\3\2\2\2lm\7\20\2\2mn\7"+
		"\21\2\2no\5\30\r\2op\7\22\2\2pq\5\32\16\2qr\7\7\2\2rs\5\34\17\2st\b\n"+
		"\1\2t\23\3\2\2\2uv\7\r\2\2vw\7\21\2\2w}\5\30\r\2xy\7\22\2\2yz\5\32\16"+
		"\2z{\7\7\2\2{|\5\34\17\2|~\3\2\2\2}x\3\2\2\2}~\3\2\2\2~\177\3\2\2\2\177"+
		"\u0080\b\13\1\2\u0080\25\3\2\2\2\u0081\u0084\5\36\20\2\u0082\u0084\7\24"+
		"\2\2\u0083\u0081\3\2\2\2\u0083\u0082\3\2\2\2\u0084\27\3\2\2\2\u0085\u0086"+
		"\5\36\20\2\u0086\31\3\2\2\2\u0087\u0088\5\36\20\2\u0088\33\3\2\2\2\u0089"+
		"\u008a\5\36\20\2\u008a\35\3\2\2\2\u008b\u0093\7\23\2\2\u008c\u0093\5 "+
		"\21\2\u008d\u0093\7\26\2\2\u008e\u008f\7\4\2\2\u008f\u0090\5\36\20\2\u0090"+
		"\u0091\7\5\2\2\u0091\u0093\3\2\2\2\u0092\u008b\3\2\2\2\u0092\u008c\3\2"+
		"\2\2\u0092\u008d\3\2\2\2\u0092\u008e\3\2\2\2\u0093\37\3\2\2\2\u0094\u0095"+
		"\t\2\2\2\u0095!\3\2\2\2\f%-\649?Hi}\u0083\u0092";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}