package json.utils;

import java.util.List;

/**
 * Factory for JSON elements.
 *
 * @author James Tapsell
 */
public interface JsonElementFactory {
  boolean isNext( StringStack stack);

  void read( List<Partition> partitions,
             StringStack stack,
             JsonTreeElement parent) throws LocatedJsonException;
}
