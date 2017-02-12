package jsonviewer.editors;

import json.utils.ContentType;
import jsonviewer.setup.JSONPartitioner;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @author James Tapsell
 */
public class SVC extends SourceViewerConfiguration {

  private static final IToken STRING = makeToken(100, 0, 0);
  private static final IToken BOOLEAN = makeToken(0, 100, 100);
  private static final IToken NUMBER = makeToken(0, 0, 100);
  private static final IToken NULL = makeToken(100, 100, 100);
  private static final IToken OBJECT = makeToken(200, 100, 100);
  private static final IToken ARRAY = makeToken(0, 100, 0);

  @Override
  public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
    PresentationReconciler pr = new PresentationReconciler();
    link(pr, STRING, ContentType.STRING);
    link(pr, BOOLEAN, ContentType.BOOLEAN);
    link(pr, NUMBER, ContentType.NUMBER);
    link(pr, NULL, ContentType.NULL);
    link(pr, OBJECT, ContentType.OBJECT);
    link(pr, ARRAY, ContentType.ARRAY);
    pr.setDocumentPartitioning(JSONPartitioner.ID);
    return pr;
  }

  private void link(PresentationReconciler pr, IToken string, ContentType string1) {
    final RuleBasedScanner rbs = new RuleBasedScanner();
    DefaultDamagerRepairer ddr = new DefaultDamagerRepairer(rbs);
    rbs.setDefaultReturnToken(string);
    pr.setDamager(ddr, string1.name());
    pr.setRepairer(ddr, string1.name());
  }


  private static IToken makeToken(int i, int i1, int i2) {
    return new Token(new TextAttribute(new Color(Display.getDefault(), i, i1, i2)));
  }
}
