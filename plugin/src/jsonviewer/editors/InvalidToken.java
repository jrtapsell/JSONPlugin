package jsonviewer.editors;

import org.eclipse.jface.text.rules.IToken;

/**
 * @author James Tapsell
 */
class InvalidToken implements IToken {
  @Override
  public boolean isUndefined() {
    return true;
  }

  @Override
  public boolean isWhitespace() {
    return false;
  }

  @Override
  public boolean isEOF() {
    return false;
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
