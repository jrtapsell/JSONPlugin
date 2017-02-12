package jsonviewer.setup;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import json.utils.LocatedJsonException;

public class DocumentSetupParticipant implements IDocumentSetupParticipant {

	@Override
	public void setup(IDocument document) {
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 idex3 = (IDocumentExtension3) document;
			final JSONPartitioner iDocumentPartitioner = new JSONPartitioner();
			iDocumentPartitioner.connect(document);
			idex3.setDocumentPartitioner(JSONPartitioner.ID, iDocumentPartitioner);
		}
	}

}
