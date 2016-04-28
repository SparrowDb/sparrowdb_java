// Generated from Spql.g by ANTLR 4.5

    package org.sparrow.spql;
    import java.util.ArrayList;
    import java.util.HashMap;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SpqlLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "K_CREATE", 
		"K_DROP", "K_DATABASE", "K_DATABASES", "K_SHOW", "K_SELECT", "K_INSERT", 
		"K_INTO", "K_DELETE", "K_FROM", "K_WHERE", "IDENTIFIER", "NUMERIC_LITERAL", 
		"BIND_PARAMETER", "STRING_LITERAL", "BLOB_LITERAL", "SINGLE_LINE_COMMENT", 
		"MULTILINE_COMMENT", "SPACES", "UNEXPECTED_CHAR", "DIGIT", "A", "B", "C", 
		"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", 
		"R", "S", "T", "U", "V", "W", "X", "Y", "Z"
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


	public SpqlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Spql.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\36\u018b\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\3\2\3\2\3\3\3\3\3\4\3\4\3\5"+
		"\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u00cf\n\25\f\25\16"+
		"\25\u00d2\13\25\3\25\3\25\3\25\3\25\3\25\7\25\u00d9\n\25\f\25\16\25\u00dc"+
		"\13\25\3\25\3\25\3\25\7\25\u00e1\n\25\f\25\16\25\u00e4\13\25\3\25\3\25"+
		"\3\25\7\25\u00e9\n\25\f\25\16\25\u00ec\13\25\5\25\u00ee\n\25\3\26\6\26"+
		"\u00f1\n\26\r\26\16\26\u00f2\3\26\3\26\7\26\u00f7\n\26\f\26\16\26\u00fa"+
		"\13\26\5\26\u00fc\n\26\3\26\3\26\5\26\u0100\n\26\3\26\6\26\u0103\n\26"+
		"\r\26\16\26\u0104\5\26\u0107\n\26\3\26\3\26\6\26\u010b\n\26\r\26\16\26"+
		"\u010c\3\26\3\26\5\26\u0111\n\26\3\26\6\26\u0114\n\26\r\26\16\26\u0115"+
		"\5\26\u0118\n\26\5\26\u011a\n\26\3\27\3\27\7\27\u011e\n\27\f\27\16\27"+
		"\u0121\13\27\3\27\3\27\5\27\u0125\n\27\3\30\3\30\3\30\3\30\7\30\u012b"+
		"\n\30\f\30\16\30\u012e\13\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3"+
		"\32\7\32\u0139\n\32\f\32\16\32\u013c\13\32\3\32\3\32\3\33\3\33\3\33\3"+
		"\33\7\33\u0144\n\33\f\33\16\33\u0147\13\33\3\33\3\33\3\33\5\33\u014c\n"+
		"\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 "+
		"\3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3"+
		"+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64"+
		"\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\3\u0145\29\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'"+
		"\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\2=\2?\2A\2C\2E\2G\2I"+
		"\2K\2M\2O\2Q\2S\2U\2W\2Y\2[\2]\2_\2a\2c\2e\2g\2i\2k\2m\2o\2\3\2\'\3\2"+
		"$$\3\2bb\3\2__\5\2C\\aac|\6\2\62;C\\aac|\4\2--//\5\2&&<<BB\3\2))\4\2\f"+
		"\f\17\17\5\2\13\r\17\17\"\"\3\2\62;\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4"+
		"\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOo"+
		"o\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2"+
		"XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\u018a\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3q\3\2\2\2\5s\3\2\2\2\7u\3\2\2"+
		"\2\tw\3\2\2\2\13y\3\2\2\2\r{\3\2\2\2\17}\3\2\2\2\21\177\3\2\2\2\23\u0081"+
		"\3\2\2\2\25\u0088\3\2\2\2\27\u008d\3\2\2\2\31\u0096\3\2\2\2\33\u00a0\3"+
		"\2\2\2\35\u00a5\3\2\2\2\37\u00ac\3\2\2\2!\u00b3\3\2\2\2#\u00b8\3\2\2\2"+
		"%\u00bf\3\2\2\2\'\u00c4\3\2\2\2)\u00ed\3\2\2\2+\u0119\3\2\2\2-\u0124\3"+
		"\2\2\2/\u0126\3\2\2\2\61\u0131\3\2\2\2\63\u0134\3\2\2\2\65\u013f\3\2\2"+
		"\2\67\u014f\3\2\2\29\u0153\3\2\2\2;\u0155\3\2\2\2=\u0157\3\2\2\2?\u0159"+
		"\3\2\2\2A\u015b\3\2\2\2C\u015d\3\2\2\2E\u015f\3\2\2\2G\u0161\3\2\2\2I"+
		"\u0163\3\2\2\2K\u0165\3\2\2\2M\u0167\3\2\2\2O\u0169\3\2\2\2Q\u016b\3\2"+
		"\2\2S\u016d\3\2\2\2U\u016f\3\2\2\2W\u0171\3\2\2\2Y\u0173\3\2\2\2[\u0175"+
		"\3\2\2\2]\u0177\3\2\2\2_\u0179\3\2\2\2a\u017b\3\2\2\2c\u017d\3\2\2\2e"+
		"\u017f\3\2\2\2g\u0181\3\2\2\2i\u0183\3\2\2\2k\u0185\3\2\2\2m\u0187\3\2"+
		"\2\2o\u0189\3\2\2\2qr\7=\2\2r\4\3\2\2\2st\7}\2\2t\6\3\2\2\2uv\7\177\2"+
		"\2v\b\3\2\2\2wx\7*\2\2x\n\3\2\2\2yz\7+\2\2z\f\3\2\2\2{|\7<\2\2|\16\3\2"+
		"\2\2}~\7.\2\2~\20\3\2\2\2\177\u0080\7?\2\2\u0080\22\3\2\2\2\u0081\u0082"+
		"\5A!\2\u0082\u0083\5_\60\2\u0083\u0084\5E#\2\u0084\u0085\5=\37\2\u0085"+
		"\u0086\5c\62\2\u0086\u0087\5E#\2\u0087\24\3\2\2\2\u0088\u0089\5C\"\2\u0089"+
		"\u008a\5_\60\2\u008a\u008b\5Y-\2\u008b\u008c\5[.\2\u008c\26\3\2\2\2\u008d"+
		"\u008e\5C\"\2\u008e\u008f\5=\37\2\u008f\u0090\5c\62\2\u0090\u0091\5=\37"+
		"\2\u0091\u0092\5? \2\u0092\u0093\5=\37\2\u0093\u0094\5a\61\2\u0094\u0095"+
		"\5E#\2\u0095\30\3\2\2\2\u0096\u0097\5C\"\2\u0097\u0098\5=\37\2\u0098\u0099"+
		"\5c\62\2\u0099\u009a\5=\37\2\u009a\u009b\5? \2\u009b\u009c\5=\37\2\u009c"+
		"\u009d\5a\61\2\u009d\u009e\5E#\2\u009e\u009f\5a\61\2\u009f\32\3\2\2\2"+
		"\u00a0\u00a1\5a\61\2\u00a1\u00a2\5K&\2\u00a2\u00a3\5Y-\2\u00a3\u00a4\5"+
		"i\65\2\u00a4\34\3\2\2\2\u00a5\u00a6\5a\61\2\u00a6\u00a7\5E#\2\u00a7\u00a8"+
		"\5S*\2\u00a8\u00a9\5E#\2\u00a9\u00aa\5A!\2\u00aa\u00ab\5c\62\2\u00ab\36"+
		"\3\2\2\2\u00ac\u00ad\5M\'\2\u00ad\u00ae\5W,\2\u00ae\u00af\5a\61\2\u00af"+
		"\u00b0\5E#\2\u00b0\u00b1\5_\60\2\u00b1\u00b2\5c\62\2\u00b2 \3\2\2\2\u00b3"+
		"\u00b4\5M\'\2\u00b4\u00b5\5W,\2\u00b5\u00b6\5c\62\2\u00b6\u00b7\5Y-\2"+
		"\u00b7\"\3\2\2\2\u00b8\u00b9\5C\"\2\u00b9\u00ba\5E#\2\u00ba\u00bb\5S*"+
		"\2\u00bb\u00bc\5E#\2\u00bc\u00bd\5c\62\2\u00bd\u00be\5E#\2\u00be$\3\2"+
		"\2\2\u00bf\u00c0\5G$\2\u00c0\u00c1\5_\60\2\u00c1\u00c2\5Y-\2\u00c2\u00c3"+
		"\5U+\2\u00c3&\3\2\2\2\u00c4\u00c5\5i\65\2\u00c5\u00c6\5K&\2\u00c6\u00c7"+
		"\5E#\2\u00c7\u00c8\5_\60\2\u00c8\u00c9\5E#\2\u00c9(\3\2\2\2\u00ca\u00d0"+
		"\7$\2\2\u00cb\u00cf\n\2\2\2\u00cc\u00cd\7$\2\2\u00cd\u00cf\7$\2\2\u00ce"+
		"\u00cb\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce\3\2"+
		"\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3"+
		"\u00ee\7$\2\2\u00d4\u00da\7b\2\2\u00d5\u00d9\n\3\2\2\u00d6\u00d7\7b\2"+
		"\2\u00d7\u00d9\7b\2\2\u00d8\u00d5\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9\u00dc"+
		"\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dd\3\2\2\2\u00dc"+
		"\u00da\3\2\2\2\u00dd\u00ee\7b\2\2\u00de\u00e2\7]\2\2\u00df\u00e1\n\4\2"+
		"\2\u00e0\u00df\3\2\2\2\u00e1\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3"+
		"\3\2\2\2\u00e3\u00e5\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e5\u00ee\7_\2\2\u00e6"+
		"\u00ea\t\5\2\2\u00e7\u00e9\t\6\2\2\u00e8\u00e7\3\2\2\2\u00e9\u00ec\3\2"+
		"\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ee\3\2\2\2\u00ec"+
		"\u00ea\3\2\2\2\u00ed\u00ca\3\2\2\2\u00ed\u00d4\3\2\2\2\u00ed\u00de\3\2"+
		"\2\2\u00ed\u00e6\3\2\2\2\u00ee*\3\2\2\2\u00ef\u00f1\5;\36\2\u00f0\u00ef"+
		"\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3"+
		"\u00fb\3\2\2\2\u00f4\u00f8\7\60\2\2\u00f5\u00f7\5;\36\2\u00f6\u00f5\3"+
		"\2\2\2\u00f7\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9"+
		"\u00fc\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00f4\3\2\2\2\u00fb\u00fc\3\2"+
		"\2\2\u00fc\u0106\3\2\2\2\u00fd\u00ff\5E#\2\u00fe\u0100\t\7\2\2\u00ff\u00fe"+
		"\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0102\3\2\2\2\u0101\u0103\5;\36\2\u0102"+
		"\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2"+
		"\2\2\u0105\u0107\3\2\2\2\u0106\u00fd\3\2\2\2\u0106\u0107\3\2\2\2\u0107"+
		"\u011a\3\2\2\2\u0108\u010a\7\60\2\2\u0109\u010b\5;\36\2\u010a\u0109\3"+
		"\2\2\2\u010b\u010c\3\2\2\2\u010c\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010d"+
		"\u0117\3\2\2\2\u010e\u0110\5E#\2\u010f\u0111\t\7\2\2\u0110\u010f\3\2\2"+
		"\2\u0110\u0111\3\2\2\2\u0111\u0113\3\2\2\2\u0112\u0114\5;\36\2\u0113\u0112"+
		"\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116"+
		"\u0118\3\2\2\2\u0117\u010e\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u011a\3\2"+
		"\2\2\u0119\u00f0\3\2\2\2\u0119\u0108\3\2\2\2\u011a,\3\2\2\2\u011b\u011f"+
		"\7A\2\2\u011c\u011e\5;\36\2\u011d\u011c\3\2\2\2\u011e\u0121\3\2\2\2\u011f"+
		"\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0125\3\2\2\2\u0121\u011f\3\2"+
		"\2\2\u0122\u0123\t\b\2\2\u0123\u0125\5)\25\2\u0124\u011b\3\2\2\2\u0124"+
		"\u0122\3\2\2\2\u0125.\3\2\2\2\u0126\u012c\7)\2\2\u0127\u012b\n\t\2\2\u0128"+
		"\u0129\7)\2\2\u0129\u012b\7)\2\2\u012a\u0127\3\2\2\2\u012a\u0128\3\2\2"+
		"\2\u012b\u012e\3\2\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012f"+
		"\3\2\2\2\u012e\u012c\3\2\2\2\u012f\u0130\7)\2\2\u0130\60\3\2\2\2\u0131"+
		"\u0132\5k\66\2\u0132\u0133\5/\30\2\u0133\62\3\2\2\2\u0134\u0135\7/\2\2"+
		"\u0135\u0136\7/\2\2\u0136\u013a\3\2\2\2\u0137\u0139\n\n\2\2\u0138\u0137"+
		"\3\2\2\2\u0139\u013c\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2\2\2\u013b"+
		"\u013d\3\2\2\2\u013c\u013a\3\2\2\2\u013d\u013e\b\32\2\2\u013e\64\3\2\2"+
		"\2\u013f\u0140\7\61\2\2\u0140\u0141\7,\2\2\u0141\u0145\3\2\2\2\u0142\u0144"+
		"\13\2\2\2\u0143\u0142\3\2\2\2\u0144\u0147\3\2\2\2\u0145\u0146\3\2\2\2"+
		"\u0145\u0143\3\2\2\2\u0146\u014b\3\2\2\2\u0147\u0145\3\2\2\2\u0148\u0149"+
		"\7,\2\2\u0149\u014c\7\61\2\2\u014a\u014c\7\2\2\3\u014b\u0148\3\2\2\2\u014b"+
		"\u014a\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014e\b\33\2\2\u014e\66\3\2\2"+
		"\2\u014f\u0150\t\13\2\2\u0150\u0151\3\2\2\2\u0151\u0152\b\34\2\2\u0152"+
		"8\3\2\2\2\u0153\u0154\13\2\2\2\u0154:\3\2\2\2\u0155\u0156\t\f\2\2\u0156"+
		"<\3\2\2\2\u0157\u0158\t\r\2\2\u0158>\3\2\2\2\u0159\u015a\t\16\2\2\u015a"+
		"@\3\2\2\2\u015b\u015c\t\17\2\2\u015cB\3\2\2\2\u015d\u015e\t\20\2\2\u015e"+
		"D\3\2\2\2\u015f\u0160\t\21\2\2\u0160F\3\2\2\2\u0161\u0162\t\22\2\2\u0162"+
		"H\3\2\2\2\u0163\u0164\t\23\2\2\u0164J\3\2\2\2\u0165\u0166\t\24\2\2\u0166"+
		"L\3\2\2\2\u0167\u0168\t\25\2\2\u0168N\3\2\2\2\u0169\u016a\t\26\2\2\u016a"+
		"P\3\2\2\2\u016b\u016c\t\27\2\2\u016cR\3\2\2\2\u016d\u016e\t\30\2\2\u016e"+
		"T\3\2\2\2\u016f\u0170\t\31\2\2\u0170V\3\2\2\2\u0171\u0172\t\32\2\2\u0172"+
		"X\3\2\2\2\u0173\u0174\t\33\2\2\u0174Z\3\2\2\2\u0175\u0176\t\34\2\2\u0176"+
		"\\\3\2\2\2\u0177\u0178\t\35\2\2\u0178^\3\2\2\2\u0179\u017a\t\36\2\2\u017a"+
		"`\3\2\2\2\u017b\u017c\t\37\2\2\u017cb\3\2\2\2\u017d\u017e\t \2\2\u017e"+
		"d\3\2\2\2\u017f\u0180\t!\2\2\u0180f\3\2\2\2\u0181\u0182\t\"\2\2\u0182"+
		"h\3\2\2\2\u0183\u0184\t#\2\2\u0184j\3\2\2\2\u0185\u0186\t$\2\2\u0186l"+
		"\3\2\2\2\u0187\u0188\t%\2\2\u0188n\3\2\2\2\u0189\u018a\t&\2\2\u018ap\3"+
		"\2\2\2\34\2\u00ce\u00d0\u00d8\u00da\u00e2\u00ea\u00ed\u00f2\u00f8\u00fb"+
		"\u00ff\u0104\u0106\u010c\u0110\u0115\u0117\u0119\u011f\u0124\u012a\u012c"+
		"\u013a\u0145\u014b\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}