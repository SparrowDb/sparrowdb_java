
    package org.sparrow.spql;

	import org.antlr.v4.runtime.*;
	import org.antlr.v4.runtime.atn.ATN;
	import org.antlr.v4.runtime.atn.ATNDeserializer;
	import org.antlr.v4.runtime.atn.LexerATNSimulator;
	import org.antlr.v4.runtime.atn.PredictionContextCache;
	import org.antlr.v4.runtime.dfa.DFA;

	import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SpqlLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "K_CREATE", "K_DROP", "K_DATABASE", 
		"K_DATABASES", "K_SHOW", "K_SELECT", "K_INSERT", "K_INTO", "K_DELETE", 
		"K_FROM", "K_WHERE", "IDENTIFIER", "NUMERIC_LITERAL", "BIND_PARAMETER", 
		"STRING_LITERAL", "BLOB_LITERAL", "SINGLE_LINE_COMMENT", "MULTILINE_COMMENT", 
		"SPACES", "UNEXPECTED_CHAR", "DIGIT", "A", "B", "C", "D", "E", "F", "G", 
		"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", 
		"V", "W", "X", "Y", "Z"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\33\u017f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\7\22\u00c3\n\22\f\22"+
		"\16\22\u00c6\13\22\3\22\3\22\3\22\3\22\3\22\7\22\u00cd\n\22\f\22\16\22"+
		"\u00d0\13\22\3\22\3\22\3\22\7\22\u00d5\n\22\f\22\16\22\u00d8\13\22\3\22"+
		"\3\22\3\22\7\22\u00dd\n\22\f\22\16\22\u00e0\13\22\5\22\u00e2\n\22\3\23"+
		"\6\23\u00e5\n\23\r\23\16\23\u00e6\3\23\3\23\7\23\u00eb\n\23\f\23\16\23"+
		"\u00ee\13\23\5\23\u00f0\n\23\3\23\3\23\5\23\u00f4\n\23\3\23\6\23\u00f7"+
		"\n\23\r\23\16\23\u00f8\5\23\u00fb\n\23\3\23\3\23\6\23\u00ff\n\23\r\23"+
		"\16\23\u0100\3\23\3\23\5\23\u0105\n\23\3\23\6\23\u0108\n\23\r\23\16\23"+
		"\u0109\5\23\u010c\n\23\5\23\u010e\n\23\3\24\3\24\7\24\u0112\n\24\f\24"+
		"\16\24\u0115\13\24\3\24\3\24\5\24\u0119\n\24\3\25\3\25\3\25\3\25\7\25"+
		"\u011f\n\25\f\25\16\25\u0122\13\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\3\27\7\27\u012d\n\27\f\27\16\27\u0130\13\27\3\27\3\27\3\30\3\30"+
		"\3\30\3\30\7\30\u0138\n\30\f\30\16\30\u013b\13\30\3\30\3\30\3\30\5\30"+
		"\u0140\n\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34"+
		"\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3"+
		"&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60"+
		"\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\u0139\2\66\3\3\5"+
		"\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\2\67\29\2;\2=\2?\2A\2"+
		"C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y\2[\2]\2_\2a\2c\2e\2g\2i\2\3\2\'\3\2"+
		"$$\3\2bb\3\2__\5\2C\\aac|\6\2\62;C\\aac|\4\2--//\5\2&&<<BB\3\2))\4\2\f"+
		"\f\17\17\5\2\13\r\17\17\"\"\3\2\62;\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4"+
		"\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOo"+
		"o\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2"+
		"XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\u017e\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\3k\3\2\2\2\5m\3\2\2\2\7o\3\2\2\2\tq\3\2\2\2\13s\3\2\2\2\ru\3\2\2\2"+
		"\17|\3\2\2\2\21\u0081\3\2\2\2\23\u008a\3\2\2\2\25\u0094\3\2\2\2\27\u0099"+
		"\3\2\2\2\31\u00a0\3\2\2\2\33\u00a7\3\2\2\2\35\u00ac\3\2\2\2\37\u00b3\3"+
		"\2\2\2!\u00b8\3\2\2\2#\u00e1\3\2\2\2%\u010d\3\2\2\2\'\u0118\3\2\2\2)\u011a"+
		"\3\2\2\2+\u0125\3\2\2\2-\u0128\3\2\2\2/\u0133\3\2\2\2\61\u0143\3\2\2\2"+
		"\63\u0147\3\2\2\2\65\u0149\3\2\2\2\67\u014b\3\2\2\29\u014d\3\2\2\2;\u014f"+
		"\3\2\2\2=\u0151\3\2\2\2?\u0153\3\2\2\2A\u0155\3\2\2\2C\u0157\3\2\2\2E"+
		"\u0159\3\2\2\2G\u015b\3\2\2\2I\u015d\3\2\2\2K\u015f\3\2\2\2M\u0161\3\2"+
		"\2\2O\u0163\3\2\2\2Q\u0165\3\2\2\2S\u0167\3\2\2\2U\u0169\3\2\2\2W\u016b"+
		"\3\2\2\2Y\u016d\3\2\2\2[\u016f\3\2\2\2]\u0171\3\2\2\2_\u0173\3\2\2\2a"+
		"\u0175\3\2\2\2c\u0177\3\2\2\2e\u0179\3\2\2\2g\u017b\3\2\2\2i\u017d\3\2"+
		"\2\2kl\7=\2\2l\4\3\2\2\2mn\7*\2\2n\6\3\2\2\2op\7+\2\2p\b\3\2\2\2qr\7."+
		"\2\2r\n\3\2\2\2st\7?\2\2t\f\3\2\2\2uv\5;\36\2vw\5Y-\2wx\5? \2xy\5\67\34"+
		"\2yz\5]/\2z{\5? \2{\16\3\2\2\2|}\5=\37\2}~\5Y-\2~\177\5S*\2\177\u0080"+
		"\5U+\2\u0080\20\3\2\2\2\u0081\u0082\5=\37\2\u0082\u0083\5\67\34\2\u0083"+
		"\u0084\5]/\2\u0084\u0085\5\67\34\2\u0085\u0086\59\35\2\u0086\u0087\5\67"+
		"\34\2\u0087\u0088\5[.\2\u0088\u0089\5? \2\u0089\22\3\2\2\2\u008a\u008b"+
		"\5=\37\2\u008b\u008c\5\67\34\2\u008c\u008d\5]/\2\u008d\u008e\5\67\34\2"+
		"\u008e\u008f\59\35\2\u008f\u0090\5\67\34\2\u0090\u0091\5[.\2\u0091\u0092"+
		"\5? \2\u0092\u0093\5[.\2\u0093\24\3\2\2\2\u0094\u0095\5[.\2\u0095\u0096"+
		"\5E#\2\u0096\u0097\5S*\2\u0097\u0098\5c\62\2\u0098\26\3\2\2\2\u0099\u009a"+
		"\5[.\2\u009a\u009b\5? \2\u009b\u009c\5M\'\2\u009c\u009d\5? \2\u009d\u009e"+
		"\5;\36\2\u009e\u009f\5]/\2\u009f\30\3\2\2\2\u00a0\u00a1\5G$\2\u00a1\u00a2"+
		"\5Q)\2\u00a2\u00a3\5[.\2\u00a3\u00a4\5? \2\u00a4\u00a5\5Y-\2\u00a5\u00a6"+
		"\5]/\2\u00a6\32\3\2\2\2\u00a7\u00a8\5G$\2\u00a8\u00a9\5Q)\2\u00a9\u00aa"+
		"\5]/\2\u00aa\u00ab\5S*\2\u00ab\34\3\2\2\2\u00ac\u00ad\5=\37\2\u00ad\u00ae"+
		"\5? \2\u00ae\u00af\5M\'\2\u00af\u00b0\5? \2\u00b0\u00b1\5]/\2\u00b1\u00b2"+
		"\5? \2\u00b2\36\3\2\2\2\u00b3\u00b4\5A!\2\u00b4\u00b5\5Y-\2\u00b5\u00b6"+
		"\5S*\2\u00b6\u00b7\5O(\2\u00b7 \3\2\2\2\u00b8\u00b9\5c\62\2\u00b9\u00ba"+
		"\5E#\2\u00ba\u00bb\5? \2\u00bb\u00bc\5Y-\2\u00bc\u00bd\5? \2\u00bd\"\3"+
		"\2\2\2\u00be\u00c4\7$\2\2\u00bf\u00c3\n\2\2\2\u00c0\u00c1\7$\2\2\u00c1"+
		"\u00c3\7$\2\2\u00c2\u00bf\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c3\u00c6\3\2"+
		"\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c7\3\2\2\2\u00c6"+
		"\u00c4\3\2\2\2\u00c7\u00e2\7$\2\2\u00c8\u00ce\7b\2\2\u00c9\u00cd\n\3\2"+
		"\2\u00ca\u00cb\7b\2\2\u00cb\u00cd\7b\2\2\u00cc\u00c9\3\2\2\2\u00cc\u00ca"+
		"\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf"+
		"\u00d1\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1\u00e2\7b\2\2\u00d2\u00d6\7]\2"+
		"\2\u00d3\u00d5\n\4\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4"+
		"\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d9\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9"+
		"\u00e2\7_\2\2\u00da\u00de\t\5\2\2\u00db\u00dd\t\6\2\2\u00dc\u00db\3\2"+
		"\2\2\u00dd\u00e0\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df"+
		"\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e1\u00be\3\2\2\2\u00e1\u00c8\3\2"+
		"\2\2\u00e1\u00d2\3\2\2\2\u00e1\u00da\3\2\2\2\u00e2$\3\2\2\2\u00e3\u00e5"+
		"\5\65\33\2\u00e4\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e4\3\2\2\2"+
		"\u00e6\u00e7\3\2\2\2\u00e7\u00ef\3\2\2\2\u00e8\u00ec\7\60\2\2\u00e9\u00eb"+
		"\5\65\33\2\u00ea\u00e9\3\2\2\2\u00eb\u00ee\3\2\2\2\u00ec\u00ea\3\2\2\2"+
		"\u00ec\u00ed\3\2\2\2\u00ed\u00f0\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ef\u00e8"+
		"\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00fa\3\2\2\2\u00f1\u00f3\5? \2\u00f2"+
		"\u00f4\t\7\2\2\u00f3\u00f2\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f6\3\2"+
		"\2\2\u00f5\u00f7\5\65\33\2\u00f6\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8"+
		"\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fb\3\2\2\2\u00fa\u00f1\3\2"+
		"\2\2\u00fa\u00fb\3\2\2\2\u00fb\u010e\3\2\2\2\u00fc\u00fe\7\60\2\2\u00fd"+
		"\u00ff\5\65\33\2\u00fe\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u00fe\3"+
		"\2\2\2\u0100\u0101\3\2\2\2\u0101\u010b\3\2\2\2\u0102\u0104\5? \2\u0103"+
		"\u0105\t\7\2\2\u0104\u0103\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0107\3\2"+
		"\2\2\u0106\u0108\5\65\33\2\u0107\u0106\3\2\2\2\u0108\u0109\3\2\2\2\u0109"+
		"\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010c\3\2\2\2\u010b\u0102\3\2"+
		"\2\2\u010b\u010c\3\2\2\2\u010c\u010e\3\2\2\2\u010d\u00e4\3\2\2\2\u010d"+
		"\u00fc\3\2\2\2\u010e&\3\2\2\2\u010f\u0113\7A\2\2\u0110\u0112\5\65\33\2"+
		"\u0111\u0110\3\2\2\2\u0112\u0115\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0114"+
		"\3\2\2\2\u0114\u0119\3\2\2\2\u0115\u0113\3\2\2\2\u0116\u0117\t\b\2\2\u0117"+
		"\u0119\5#\22\2\u0118\u010f\3\2\2\2\u0118\u0116\3\2\2\2\u0119(\3\2\2\2"+
		"\u011a\u0120\7)\2\2\u011b\u011f\n\t\2\2\u011c\u011d\7)\2\2\u011d\u011f"+
		"\7)\2\2\u011e\u011b\3\2\2\2\u011e\u011c\3\2\2\2\u011f\u0122\3\2\2\2\u0120"+
		"\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0123\3\2\2\2\u0122\u0120\3\2"+
		"\2\2\u0123\u0124\7)\2\2\u0124*\3\2\2\2\u0125\u0126\5e\63\2\u0126\u0127"+
		"\5)\25\2\u0127,\3\2\2\2\u0128\u0129\7/\2\2\u0129\u012a\7/\2\2\u012a\u012e"+
		"\3\2\2\2\u012b\u012d\n\n\2\2\u012c\u012b\3\2\2\2\u012d\u0130\3\2\2\2\u012e"+
		"\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u0131\3\2\2\2\u0130\u012e\3\2"+
		"\2\2\u0131\u0132\b\27\2\2\u0132.\3\2\2\2\u0133\u0134\7\61\2\2\u0134\u0135"+
		"\7,\2\2\u0135\u0139\3\2\2\2\u0136\u0138\13\2\2\2\u0137\u0136\3\2\2\2\u0138"+
		"\u013b\3\2\2\2\u0139\u013a\3\2\2\2\u0139\u0137\3\2\2\2\u013a\u013f\3\2"+
		"\2\2\u013b\u0139\3\2\2\2\u013c\u013d\7,\2\2\u013d\u0140\7\61\2\2\u013e"+
		"\u0140\7\2\2\3\u013f\u013c\3\2\2\2\u013f\u013e\3\2\2\2\u0140\u0141\3\2"+
		"\2\2\u0141\u0142\b\30\2\2\u0142\60\3\2\2\2\u0143\u0144\t\13\2\2\u0144"+
		"\u0145\3\2\2\2\u0145\u0146\b\31\2\2\u0146\62\3\2\2\2\u0147\u0148\13\2"+
		"\2\2\u0148\64\3\2\2\2\u0149\u014a\t\f\2\2\u014a\66\3\2\2\2\u014b\u014c"+
		"\t\r\2\2\u014c8\3\2\2\2\u014d\u014e\t\16\2\2\u014e:\3\2\2\2\u014f\u0150"+
		"\t\17\2\2\u0150<\3\2\2\2\u0151\u0152\t\20\2\2\u0152>\3\2\2\2\u0153\u0154"+
		"\t\21\2\2\u0154@\3\2\2\2\u0155\u0156\t\22\2\2\u0156B\3\2\2\2\u0157\u0158"+
		"\t\23\2\2\u0158D\3\2\2\2\u0159\u015a\t\24\2\2\u015aF\3\2\2\2\u015b\u015c"+
		"\t\25\2\2\u015cH\3\2\2\2\u015d\u015e\t\26\2\2\u015eJ\3\2\2\2\u015f\u0160"+
		"\t\27\2\2\u0160L\3\2\2\2\u0161\u0162\t\30\2\2\u0162N\3\2\2\2\u0163\u0164"+
		"\t\31\2\2\u0164P\3\2\2\2\u0165\u0166\t\32\2\2\u0166R\3\2\2\2\u0167\u0168"+
		"\t\33\2\2\u0168T\3\2\2\2\u0169\u016a\t\34\2\2\u016aV\3\2\2\2\u016b\u016c"+
		"\t\35\2\2\u016cX\3\2\2\2\u016d\u016e\t\36\2\2\u016eZ\3\2\2\2\u016f\u0170"+
		"\t\37\2\2\u0170\\\3\2\2\2\u0171\u0172\t \2\2\u0172^\3\2\2\2\u0173\u0174"+
		"\t!\2\2\u0174`\3\2\2\2\u0175\u0176\t\"\2\2\u0176b\3\2\2\2\u0177\u0178"+
		"\t#\2\2\u0178d\3\2\2\2\u0179\u017a\t$\2\2\u017af\3\2\2\2\u017b\u017c\t"+
		"%\2\2\u017ch\3\2\2\2\u017d\u017e\t&\2\2\u017ej\3\2\2\2\34\2\u00c2\u00c4"+
		"\u00cc\u00ce\u00d6\u00de\u00e1\u00e6\u00ec\u00ef\u00f3\u00f8\u00fa\u0100"+
		"\u0104\u0109\u010b\u010d\u0113\u0118\u011e\u0120\u012e\u0139\u013f\3\2"+
		"\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}