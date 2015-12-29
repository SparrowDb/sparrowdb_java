package org.sparrow.spql.statements;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by mauricio on 25/12/2015.
 */
public interface SpqlStatement
{
    List<String> execute(Matcher matcher, ByteBuffer buffer);
}
