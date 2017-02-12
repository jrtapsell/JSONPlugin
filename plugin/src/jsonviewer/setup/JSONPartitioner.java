package jsonviewer.setup;

import java.util.Arrays;
import java.util.List;
import javafx.util.Pair;
import json.parser.Json;
import json.utils.JsonTreeElement;
import json.utils.LocatedJsonException;
import json.utils.Partition;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;

import json.utils.ContentType;
import org.eclipse.jface.text.TypedRegion;

/**
 * @author James Tapsell
 */
public class JSONPartitioner implements IDocumentPartitioner {
  public static final String ID = "JSONPartitioner";
  public static final String UNDEFINED = "UNDEFINED";
  private IDocument document;
  private Pair<List<Partition>, JsonTreeElement> data;

  @Override
  public void connect(IDocument iDocument) {
    document = iDocument;
  }

  @Override
  public void disconnect() {
    document = null;
  }

  @Override
  public void documentAboutToBeChanged(DocumentEvent documentEvent) {}

  @Override
  public boolean documentChanged(DocumentEvent documentEvent) {
    reparse();
    return true;
  }

  private void reparse() {
    final String text = document.get();
    if (text == null || "".equals(text)) {
      data = null;
      return;
    }
    try {
      data = Json.parseString(text);
    } catch (LocatedJsonException | StringIndexOutOfBoundsException ex) {
      data = null;
    }
    System.out.println("Parsed");
  }

  @Override
  public String[] getLegalContentTypes() {
    final String[] types = Arrays.stream(ContentType.values()).map(ContentType::name).toArray(p -> new String[p + 1]);
    types[types.length - 1] = UNDEFINED;
    return types;
  }

  @Override
  public String getContentType(int i) {
    return null;
  }

  @Override
  public ITypedRegion[] computePartitioning(int i, int i1) {
    if (data == null) {
      return new ITypedRegion[]{ new TypedRegion(i, i1, UNDEFINED)};
    }
    List<Partition> partitions = data.getKey();
    final TypedRegion[] ret = partitions.stream()
        .map(p -> new TypedRegion(p.getStart(), p.getEnd() - p.getStart(), p.getName()))
        .toArray(TypedRegion[]::new);
    return ret;
  }

  @Override
  public ITypedRegion getPartition(int i) {
	if (data == null) {
		return new TypedRegion(i, 0, UNDEFINED);
	}
    final Partition partition = data.getKey().stream()
        .filter(p -> p.getEnd() > i)
        .filter(p -> p.getStart() <= i)
        .findFirst()
        .get();
    return new TypedRegion(partition.getStart(), partition.getEnd() - partition.getStart(), partition.getName());
  }
}
