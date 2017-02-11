package jsonviewer.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class JSONEditor extends TextEditor {

	public JSONEditor() {
		super();
		setSourceViewerConfiguration(new SVC());
	}
	@Override
	public void dispose() {
		super.dispose();
	}

}
