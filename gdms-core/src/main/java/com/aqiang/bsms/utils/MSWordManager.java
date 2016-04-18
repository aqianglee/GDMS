package com.aqiang.bsms.utils;

import java.util.ArrayList;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class MSWordManager {
	private Dispatch doc;
	private ActiveXComponent word;
	private Dispatch documents;
	private Dispatch selection;
	private boolean saveonExit = true;

	public MSWordManager(boolean visible) {

		if (word == null) {
			word = new ActiveXComponent("Word.Application");
			word.setProperty("visible", new Variant(visible));
		}
		if (documents == null) {
			documents = word.getProperty("Documents").toDispatch();
		}
		word.setProperty("Visible", new Variant(visible));
	}

	public void setSaveOnExit(boolean saveOnExit) {
		this.saveonExit = saveOnExit;
	}

	public void TypeParagraph(int pos) {
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "TypeParagraph");
		}
	}

	public void createNewDocument() {
		doc = Dispatch.call(documents, "Add").toDispatch();
		selection = Dispatch.get(word, "Selection").toDispatch();

	}

	public void openDocument(String docPath) {
		closeDocument();
		doc = Dispatch.call(documents, "Open", docPath).toDispatch();
		selection = Dispatch.get(word, "Selection").toDispatch();
	}

	public void moveUp(int pos) {
		if (selection == null) {
			selection = Dispatch.get(word, "Selection").toDispatch();

		}
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "MoveUp");
		}
	}

	public void moveDown(int pos) {
		if (selection == null) {
			selection = Dispatch.get(word, "Selection").toDispatch();
		}
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "MoveDown");
		}
	}

	public void moveLeft(int pos) {
		if (selection == null) {
			selection = Dispatch.get(word, "Selection").toDispatch();
		}
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "MoveLeft");
		}
	}

	public void moveRight(int pos) {
		if (selection == null) {
			selection = Dispatch.get(word, "Selection").toDispatch();
		}
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "MoveRight");
		}
	}

	public void moveStart() {
		if (selection == null) {
			selection = Dispatch.get(word, "Selection").toDispatch();
		}
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}

	public void moveEnd() {
		if (selection == null) {
			selection = Dispatch.get(word, "Selection").toDispatch();
		}
		Dispatch.call(selection, "EndKey", new Variant(6));
	}

	public boolean find(String toFindText) {
		if (toFindText == null || toFindText.equals(""))
			return false;
		@SuppressWarnings("static-access")
		Dispatch find = word.call(selection, "Find").toDispatch();
		Dispatch.put(find, "Text", toFindText);
		Dispatch.put(find, "Forward", "True");
		Dispatch.put(find, "Format", "True");
		Dispatch.put(find, "MatchCase", "True");
		Dispatch.put(find, "MatchWholeWord", "True");
		return Dispatch.call(find, "Execute").getBoolean();
	}

	public boolean replaceText(String toFildText, String newText) {
		if (!find(toFildText)) {
			return false;
		}
		Dispatch.put(selection, "Text", newText);
		return true;
	}

	public void replaceAllText(String toFindText, String newText) {
		while (find(toFindText)) {
			Dispatch.put(selection, "Text", newText);
			Dispatch.call(selection, "MoveRight");
		}
	}

	public void insertTextAtPrePoint(String newText) {
		Dispatch.put(selection, "Text", newText);
	}

	public void insertText(String textToInsert) {
		Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		Dispatch.put(selection, "Text", textToInsert);
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
	}

	public Dispatch insertTextAlign(String textToInsert) {

		Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		Dispatch.put(selection, "Text", textToInsert);
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		return selection;
	}

	public void insertFormatTextToWord(String txt, int fontSize, boolean bold,
			int textAlign, String fontFamily, String color, boolean indent) {
		Dispatch.put(selection, "Text", txt);
		Dispatch paragraphs = Dispatch.get(selection, "Paragraphs")
				.toDispatch();
		Dispatch.put(paragraphs, "Alignment", new Variant(textAlign));
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();
		if (indent) {
			Dispatch.put(paragraphs, "CharacterUnitFirstLineIndent",
					new Variant(2));
		}
		Dispatch.put(font, "Name", new Variant(fontFamily));
		Dispatch.put(font, "Size", fontSize);
		if (bold) {
			Dispatch.put(font, "Bold", new Variant(bold));
		} else {
			Dispatch.put(font, "Bold", new Variant(false));
		}
		Dispatch.put(font, "Color", color);

		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
	}

	public void insertFormatTextToTable(int tableIndex, int row, int col,
			String txt, String fontSize, boolean bold, int textAlign,
			String fontFamily, String color, int verticalAlign) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch cell = Dispatch.call(table, "Cell", new Variant(row),
				new Variant(col)).toDispatch();
		Dispatch.put(cell, "VerticalAlignment", new Variant(verticalAlign));
		Dispatch.call(cell, "Select");
		Dispatch.put(selection, "Text", txt);
		Dispatch paragraphs = Dispatch.get(selection, "Paragraphs")
				.toDispatch();
		Dispatch.put(paragraphs, "Alignment", new Variant(textAlign));
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font, "Name", new Variant(fontFamily));
		Dispatch.put(font, "Size", fontSize);
		if (bold) {
			Dispatch.put(font, "Bold", new Variant(bold));
		} else {
			Dispatch.put(font, "Bold", new Variant(false));
		}
		Dispatch.put(font, "Color", color);

		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
	}

	public void setCellsWidth(int tableIndex, int[] cols) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch columns = Dispatch.get(table, "Columns").toDispatch();
		for (int i = 0; i < cols.length; i++) {
			Dispatch column = Dispatch
					.call(columns, "Item", new Variant(i + 1)).toDispatch();
			Dispatch.put(column, "Width", new Variant(cols[i]));
		}
	}

	public void setCellsHight(int tableIndex, int[] rows) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch rowLines = Dispatch.get(table, "Rows").toDispatch();
		for (int i = 0; i < rows.length; i++) {
			Dispatch rowLine = Dispatch.call(rowLines, "Item",
					new Variant(i + 1)).toDispatch();
			Dispatch.put(rowLine, "Height", new Variant(rows[i]));
		}
	}

	public void setRowHight(int tableIndex, int rowIndex, int cellHeight) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch rowLines = Dispatch.get(table, "Rows").toDispatch();
		Dispatch rowLine = Dispatch.call(rowLines, "Item",
				new Variant(tableIndex)).toDispatch();
		Dispatch.put(rowLine, "Height", new Variant(cellHeight));
	}

	public boolean replaceImage(String toFindText, String imagePath) {
		if (!find(toFindText)) {
			return false;
		}
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddPicture", imagePath);
		return true;
	}

	public void replaceAllImage(String toFindText, String imagePath) {
		while (find(toFindText)) {
			Dispatch.call(Dispatch.get(selection, "InLineShape").toDispatch(),
					"AddPicture", imagePath);
			Dispatch.call(selection, "MoveRight");
		}
	}

	public void insertImage(String imagePath) {
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddPicture", imagePath);
	}

	public void mergeCell(int tableIndex, int fstCellRowIdx, int fstCellColIdx,
			int secCellRowIdx, int secCellColIdx) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch fstCell = Dispatch.call(table, "Cell",
				new Variant(fstCellRowIdx), new Variant(fstCellColIdx))
				.toDispatch();
		Dispatch secCell = Dispatch.call(table, "Cell",
				new Variant(secCellRowIdx), new Variant(secCellColIdx))
				.toDispatch();
		Dispatch.call(fstCell, "Merge", secCell);
	}

	public void mergeRowCell(int talbeIndex, int fstCellRowIdex,
			int fstCellColIdex, int colnums) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(talbeIndex))
				.toDispatch();
		Dispatch fstCell = Dispatch.call(table, "Cell",
				new Variant(fstCellRowIdex), new Variant(fstCellColIdex))
				.toDispatch();
		for (int i = 1; i < colnums; i++) {
			Dispatch secCell = Dispatch.call(table, "Cell",
					new Variant(fstCellRowIdex),
					new Variant(fstCellColIdex + 1)).toDispatch();
			Dispatch.call(fstCell, "Merge", secCell);
		}
	}

	public void mergeColCell(int tableIndex, int fstCellRowIdex,
			int fstCellIdx, int rowNums) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch fstCell = Dispatch.call(table, "Cell",
				new Variant(fstCellRowIdex), new Variant(fstCellIdx))
				.toDispatch();
		for (int i = 1; i < rowNums; i++) {
			Dispatch secCell = Dispatch.call(table, "Cell",
					new Variant(fstCellRowIdex + 1), new Variant(fstCellIdx))
					.toDispatch();
			Dispatch.call(fstCell, "Merge", secCell);
		}
	}

	public void putTxtToCell(int tableIndex, int cellRowIdx, int cellColIdx,
			String txt) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),
				new Variant(cellColIdx)).toDispatch();
		Dispatch.call(cell, "Select");
		Dispatch.put(selection, "Text", txt);
	}

	public void copy(String toCopyText) {
		moveStart();
		if (this.find(toCopyText)) {
			Dispatch textRange = Dispatch.get(selection, "Range").toDispatch();
			Dispatch.call(textRange, "Copy");
		}
	}

	public void paste(String pos) {
		moveStart();
		if (this.find(pos)) {
			Dispatch textRange = Dispatch.get(selection, "Range").toDispatch();
			Dispatch.call(textRange, "Paste");
		}
	}

	public void copyTable(String pos, int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch range = Dispatch.get(table, "Range").toDispatch();
		Dispatch.call(range, "Copy");
		if (this.find(pos)) {
			Dispatch textRange = Dispatch.get(selection, "Range").toDispatch();
			Dispatch.call(textRange, "Paste");
		}
	}

	public void copyParagraphFromAnotherDoc(String anotherDocPath,
			int paragraphIndex) {
		Dispatch wordContent = Dispatch.get(doc, "Content").toDispatch();
		Dispatch.call(wordContent, "InsertAfter", "$selection$");
		copyParaphFromAnotherDoc(anotherDocPath, paragraphIndex, "$selection$");
	}

	public void copyParaphFromAnotherDoc(String anotherDocPath,
			int paragraphIndex, String pos) {
		Dispatch doc2 = null;
		try {
			doc2 = Dispatch.call(documents, "Open", anotherDocPath)
					.toDispatch();
			Dispatch paragraphs = Dispatch.get(doc2, "Paragraphs").toDispatch();
			Dispatch paragraph = Dispatch.call(paragraphs, "Item",
					new Variant(paragraphIndex)).toDispatch();
			Dispatch range = Dispatch.get(paragraph, "Range").toDispatch();
			Dispatch.call(range, "Copy");
			if (this.find(pos)) {
				Dispatch textRange = Dispatch.get(selection, "Range")
						.toDispatch();
				Dispatch.call(textRange, "Paste");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc2 != null) {
				Dispatch.call(doc2, "close", new Variant(saveonExit));
				doc2 = null;
			}
		}
	}

	public void copyTableFromAnotherDoc(String anotherDocPath, int tableIndex,
			String pos) {
		Dispatch doc2 = null;
		try {
			doc2 = Dispatch.call(documents, "Open", anotherDocPath)
					.toDispatch();
			Dispatch tables = Dispatch.get(doc2, "Tables").toDispatch();
			Dispatch table = Dispatch.call(tables, "Item",
					new Variant(tableIndex)).toDispatch();
			Dispatch range = Dispatch.get(table, "range").toDispatch();
			Dispatch.call(range, "Copy");
			if (this.find(pos)) {
				Dispatch textRange = Dispatch.get(selection, "Range")
						.toDispatch();
				Dispatch.call(textRange, "Paste");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc2 != null) {
				Dispatch.call(doc2, "Close", new Variant(saveonExit));
				doc2 = null;
			}
		}
	}

	public void copyImageFromAnotherDoc(String anotherDocPath, int shapeIndex,
			String pos) {
		Dispatch doc2 = null;
		try {
			doc2 = Dispatch.call(documents, "Open", anotherDocPath)
					.toDispatch();
			Dispatch shapes = Dispatch.get(doc2, "InLineShapes").toDispatch();
			Dispatch shape = Dispatch.call(shapes, "Item",
					new Variant(shapeIndex)).toDispatch();
			Dispatch imageRange = Dispatch.get(shape, "Range").toDispatch();
			Dispatch.call(imageRange, "Copy");
			if (this.find(pos)) {
				Dispatch textRange = Dispatch.get(selection, "range")
						.toDispatch();
				Dispatch.call(textRange, "Paste");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc2 != null) {
				Dispatch.call(doc2, "Close", new Variant(saveonExit));
			}
		}
	}

	public void CreateTable(String tableTitle, int row, int column) {
		Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch.call(selection, "TypeText", tableTitle);
		Dispatch.call(selection, "TypeParagraph");
		Dispatch.call(selection, "MoveDown");
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch range = Dispatch.get(selection, "Range").toDispatch();
		Dispatch.call(tables, "Add", range, new Variant(row),
				new Variant(column), new Variant(1)).toDispatch();
	}

	public void createTable(int rows, int cols) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch range = Dispatch.get(selection, "Range").toDispatch();
		Dispatch.call(tables, "Add", range, new Variant(rows),
				new Variant(cols), new Variant(1)).toDispatch();
		Dispatch.call(selection, "MoveRight");
	}

	public void createTable(int numCols, int numRows, String pos) {
		if (!find(pos)) {
			Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
			Dispatch range = Dispatch.get(selection, "Range").toDispatch();
			Dispatch.call(tables, "Add", range, new Variant(numRows),
					new Variant(numCols)).toDispatch();
			Dispatch.call(selection, "MoveRight");
			moveEnd();
		}
	}

	public void addTableRow(int tableIndex, int rowIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.call(rows, "Item", new Variant(rowIndex))
				.toDispatch();
		Dispatch.call(rows, "Add", new Variant(row));
	}

	public Dispatch getTableRowDispatch(int tableIndex, int rowIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.call(rows, "Item", new Variant(rowIndex))
				.toDispatch();
		return row;
	}

	public List<Dispatch> getTableRowSet(int tableIndex, int[] rowIndex) {
		if (tableIndex <= 0) {
			return null;
		}
		List<Dispatch> dispatch = new ArrayList<Dispatch>();
		for (int i = 0; i < rowIndex.length; i++) {
			dispatch.add(getTableRowDispatch(tableIndex, rowIndex[i]));
		}
		return dispatch;
	}

	public void setHeight(Dispatch dispatch, int height) throws Exception {
		try {
			if (dispatch != null) {
				Dispatch.put(dispatch, "Height", new Variant(height));
			}
		} catch (ComFailException e) {
			e.printStackTrace();
		}
	}

	public void setHeight(List<Dispatch> dispatch, int height) throws Exception {
		for (Dispatch d : dispatch) {
			setHeight(d, height);
		}
	}

	public void addFirstTableRow(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch rows = Dispatch.get(table, "rows").toDispatch();
		Dispatch row = Dispatch.get(rows, "First").toDispatch();
		Dispatch.call(rows, "Add", new Variant(row));
	}

	public void addLastTableRow(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.get(rows, "Last").toDispatch();
		Dispatch.call(rows, "Add", new Variant(row));
	}

	public void addRow(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch.call(rows, "Add");
	}

	public void addCol(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		Dispatch.call(cols, "Add").toDispatch();
		Dispatch.call(cols, "AutoFit");
	}

	public void addTableCol(int tableIndex, int colIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		Dispatch col = Dispatch.call(cols, "Item", new Variant(colIndex))
				.toDispatch();
		Dispatch.call(col, "Add", col).toDispatch();
		Dispatch.call(cols, "AutoFit");
	}

	public void addFirstTableCol(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		@SuppressWarnings("unused")
		Dispatch col = Dispatch.get(cols, "First").toDispatch();
		Dispatch.call(cols, "AutoFit");
	}

	public void addLastTableCol(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		Dispatch col = Dispatch.get(cols, "Last").toDispatch();
		Dispatch.call(cols, "Add", col).toDispatch();
		Dispatch.call(cols, "AutoFit");
	}

	public void autoFitTable() {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		@SuppressWarnings("deprecation")
		int count = Dispatch.get(tables, "Count").toInt();
		for (int i = 0; i < count; i++) {
			Dispatch table = Dispatch.call(tables, "Item", new Variant(i + 1))
					.toDispatch();
			Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
			Dispatch.call(cols, "AutoFit");
		}
	}

	public void callWordMacro() {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		@SuppressWarnings({ "unused", "deprecation" })
		int count = Dispatch.get(tables, "Count").toInt();
		Variant vMacroName = new Variant("Normal.newMacros.tableFit");
		@SuppressWarnings("unused")
		Variant vParam = new Variant("param1");
		Variant para[] = new Variant[] { vMacroName };
		for (int i = 0; i < para.length; i++) {
			Dispatch table = Dispatch.call(tables, "Item", new Variant(i + 1))
					.toDispatch();
			Dispatch.call(table, "Select");
			Dispatch.call(word, "Run", "tableFitContent");
		}
	}

	public void setPageOrientation(int orientation) {
		Dispatch pageSetup = Dispatch.get(doc, "PageSetup").toDispatch();
		Dispatch.put(pageSetup, "Orientation", orientation);
	}

	public void setFont(boolean bold, boolean italic, boolean underLine,
			String colorSize, String size, String name) {
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font, "Name", new Variant(name));
		Dispatch.put(font, "Bold", new Variant(bold));
		Dispatch.put(font, "Italic", new Variant(italic));
		Dispatch.put(font, "Underline", new Variant(underLine));
		Dispatch.put(font, "Color", colorSize);
		Dispatch.put(font, "Size", size);
	}

	public void setAlignment(Dispatch dispatch, String vertical,
			String Horizontal) {
		Dispatch font = Dispatch.get(dispatch, "Font").toDispatch();
		Dispatch.put(font, "Alignment", vertical);
	}

	public void save(String savePath) {
		Dispatch.call(
				(Dispatch) Dispatch.call(word, "WordBasic").getDispatch(),
				"FileSaveAs", savePath);
	}
	
	public void saveAs(String savePath, int saveType) {
		Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
				savePath, new Variant(saveType) }, new int[1]);
	}

	public void closeDocument() {
		if (doc != null) {
			Dispatch.call(doc, "Save");
			Dispatch.call(doc, "Close", new Variant(saveonExit));
			doc = null;
		}
	}

	public void close() {
		closeDocument();
		if (word != null) {
			Dispatch.call(word, "Quit");
			word = null;
		}
		selection = null;
		documents = null;
	}

	public void printFile() {
		if (doc != null) {
			Dispatch.call(doc, "PrintOut");
		}
	}

}
