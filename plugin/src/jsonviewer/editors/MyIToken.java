package jsonviewer.editors;

import org.eclipse.jface.text.rules.IToken;

/**
 * @author James Tapsell
 */
class MyIToken implements IToken {
  @Override
  public boolean isUndefined() {
    return false;
  }

  @Override
  public boolean isWhitespace() {
    return false;
  }

  @Override
  public boolean isEOF() {
    return true;
  }

  @Override
  public boolean isOther() {
    return false;
  }

  @Override
  public Object getData() {
    return null;
  }
}
