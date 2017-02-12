package json.parser;

import java.util.List;
import json.utils.ContentType;
import json.utils.JsonElementFactory;
import json.utils.JsonTreeElement;
import json.utils.LocatedJsonException;
import json.utils.Partition;
import json.utils.StringStack;

/**
 * Factory for JSONElements of type String.
 *
 * @author James Tapsell
 */
public final class JsonStringFactory implements JsonElementFactory {
  private static final JsonStringFactory INSTANCE = new JsonStringFactory();

  public static JsonStringFactory getInstance() {
    return INSTANCE;
  }

  private JsonStringFactory() {}

  @Override
  public void read(
      final  List<Partition> partitions,
      final  StringStack stack,
      final  JsonTreeElement tree) throws LocatedJsonException {
    final int startIndex = stack.getIndex();
    stack.pop();
    boolean escaped = false;
    while (stack.isAvailable()) {
      final char c = stack.pop();
      if (c == '\\') {
        escaped = !escaped;
      } else if (c == '"' && !escaped) {
        partitions.add(new Partition(startIndex, stack.getIndex(), ContentType.STRING));
        return;
      }
    }
    throw new LocatedJsonException("Unterminated String", stack, startIndex);
  }

  @Override
  public boolean isNext(final  StringStack stack) {
    return stack.peek() == '"';
  }
}
