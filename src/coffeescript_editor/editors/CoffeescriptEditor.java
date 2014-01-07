package coffeescript_editor.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class CoffeescriptEditor extends TextEditor {

	private ColorManager colorManager;

	public CoffeescriptEditor() {
		colorManager = new ColorManager();
		setDocumentProvider( new CoffeescriptDocumentProvider() );
		setSourceViewerConfiguration(new CoffeescriptConfiguration( colorManager ));
	}

	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
