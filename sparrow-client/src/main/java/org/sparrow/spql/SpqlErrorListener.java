package org.sparrow.spql;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * Created by mauricio on 6/3/16.
 */
public class SpqlErrorListener extends BaseErrorListener
{
    public static SpqlErrorListener instance = new SpqlErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e)
    {
        throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
    }
}
