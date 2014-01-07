package coffeescript_editor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;

public class CoffeescriptConfiguration extends SourceViewerConfiguration {
	private ColorManager colorManager;

	public CoffeescriptConfiguration( ColorManager colorManager ) {
		this.colorManager = colorManager;
	}

	@Override
	public String[] getConfiguredContentTypes( ISourceViewer sourceViewer ) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			CoffeescriptPartitionScanner.COMMENT,
		};
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		// Default
		{
			RGB c = new RGB(0, 0, 0);
			CoffeescriptScanner scanner = new CoffeescriptScanner(colorManager);
			scanner.setDefaultReturnToken( new Token(new TextAttribute(colorManager.getColor(c))));

			DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
			reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
			reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		}

		// Comment
		{
			RGB c = new RGB( 0x8E, 0x90, 0x8C );
			NonRuleBasedDamagerRepairer dr = new NonRuleBasedDamagerRepairer(new TextAttribute(colorManager.getColor(c)));
			reconciler.setDamager(dr, CoffeescriptPartitionScanner.COMMENT);
			reconciler.setRepairer(dr, CoffeescriptPartitionScanner.COMMENT);
		}

		// String
		{
			RGB c = new RGB( 0x71, 0x8C, 0x00 );
			NonRuleBasedDamagerRepairer dr = new NonRuleBasedDamagerRepairer(new TextAttribute(colorManager.getColor(c)));
			reconciler.setDamager(dr, CoffeescriptPartitionScanner.STRING);
			reconciler.setRepairer(dr, CoffeescriptPartitionScanner.STRING);
		}

		return reconciler;
	}
}
