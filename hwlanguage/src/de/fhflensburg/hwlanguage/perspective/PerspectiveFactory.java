/*
 * Created on 02.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * @author user
 *
 * 
 */
public class PerspectiveFactory implements IPerspectiveFactory
{

	public PerspectiveFactory()
	{
	}

	public void createInitialLayout(IPageLayout layout)
	{
		defineActions(layout);
		defineLayout(layout);
	}

	public void defineActions(IPageLayout layout)
	{
		layout.addNewWizardShortcut("de.fhflensburg.hwlanguage.new.hwlangfile");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");
		layout.addShowViewShortcut("org.eclipse.ui.views.ResourceNavigator");
		layout.addShowViewShortcut("org.eclipse.ui.views.BookmarkNavigator");
		layout.addShowViewShortcut("org.eclipse.ui.views.ContentOutline");
		layout.addShowViewShortcut("org.eclipse.ui.views.PropertySheet");
		layout.addShowViewShortcut("org.eclipse.ui.views.TaskList");
		layout.addShowViewShortcut("org.eclipse.search.SearchResultView");
	}

	public void defineLayout(IPageLayout layout)
	{
		String editorArea = layout.getEditorArea();
		IFolderLayout topLeft = layout.createFolder("topLeft", 1, 0.26F, editorArea);
		topLeft.addView("org.eclipse.ui.views.ResourceNavigator");
		IFolderLayout bottomLeft = layout.createFolder("bottomLeft", 4, 0.5F, "topLeft");
		bottomLeft.addView("org.eclipse.ui.views.ContentOutline");
		IFolderLayout bottomRight = layout.createFolder("bottomRight", 4, 0.66F, editorArea);
		bottomRight.addView("org.eclipse.ui.views.TaskList");
		bottomRight.addPlaceholder("org.eclipse.search.SearchResultView");
		bottomRight.addPlaceholder("org.eclipse.ui.views.BookmarkNavigator");
	}
}
