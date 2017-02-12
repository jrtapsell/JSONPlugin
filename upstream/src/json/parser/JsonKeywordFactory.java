package json.parser;

import java.util.Collection;
import java.util.List;
import json.utils.ContentType;
import json.utils.JsonElementFactory;
import json.utils.JsonTreeElement;
import json.utils.LocatedJsonException;
import json.utils.Partition;
import json.utils.StringStack;

/**
 * Factory for JSONElements of types Boolean or Null.
 *
 * @author James Tapsell
 */
public final class JsonKeywordFactory implements JsonElementFactory {

  private static final JsonKeywordFactory INSTANCE = new JsonKeywordFactory();

  public static JsonElementFactory getInstance() {
    return INSTANCE;
  }

  private JsonKeywordFactory() {}

  @Override
  public boolean isNext(final  StringStack stack) {
    return stack.isNext("true")
        || stack.isNext("false")
        || stack.isNext("null");
  }

  @Override
  public void read(
      final  List<Partition> partitions,
      final  StringStack stack,
      final  JsonTreeElement parent) throws LocatedJsonException {
    if (checkKeyword(partitions, stack, "true", ContentType.BOOLEAN, parent)) {
      return;
    }
    if (checkKeyword(partitions, stack, "null", ContentType.NULL, parent)) {
      return;
    }
    if (checkKeyword(partitions, stack, "false", ContentType.BOOLEAN, parent)) {
      return;
    }
    throw new LocatedJsonException("Unknown keyword", stack);
  }

  private static boolean checkKeyword(
      final  Collection<Partition> partitions,
      final  StringStack ss,
      final  CharSequence keyword,
      final  ContentType type,
      final  JsonTreeElement root) {
    if (ss.isNext(keyword)) {
      final int start = ss.getIndex();
      final int end = start + keyword.length();
      JsonTreeElement jte = new JsonTreeElement(type, start);
      jte.finalise(end, ss.getText(start, end));
      partitions.add(new Partition(start, end, type));
      root.addChild(jte);
      ss.consume(keyword);
      return true;
    }
    return false;
  }
}
