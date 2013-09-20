package com.mobile.mobileweb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class TestUtil extends DriverScript {
	public static String filename;
	static float marginLeft = 0f;
	static float marginRight = 0f;
	static float marginTop = 0f;
	static float marginBottom = 0f;

	public static void captureScreenShot(int filecount) {
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			filename = screenname + filecount + ".jpg";
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
					+ "//target//" + filename));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createPDF() throws DocumentException,
			MalformedURLException, IOException {
		document = new Document(PageSize.LETTER, marginLeft, marginRight,
				marginTop, marginBottom);
		PdfWriter.getInstance(document,
				new FileOutputStream(System.getProperty("user.dir")
						+ "\\src\\test\\java\\config\\images.pdf"));
		headerAndFooter();
		document.open();

	}

	public static void headerAndFooter() throws DocumentException, IOException {
		
		BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252",
				false);
		BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252",
				false);
		// headers and footers must be added before the document is opened
		HeaderFooter footer = new HeaderFooter(new Phrase("This is page: ",
				new Font(bf_courier)), true);
		footer.setBorder(Rectangle.NO_BORDER);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.setFooter(footer);

		HeaderFooter header = new HeaderFooter(new Phrase(
				"This is a header without a page number", new Font(bf_symbol)),
				false);
		
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);
	}

	public static void writeImagesToPdf(int i) throws MalformedURLException,
			IOException, DocumentException {

		Image image = Image.getInstance(System.getProperty("user.dir")
				+ "\\target\\" + filename);

		document.setPageCount(i);
		
		document.newPage();
		
		document.add(image);

	}

	public static void screenScrollByWindowSizeAndCaptureScreenShot()
			throws MalformedURLException, DocumentException, IOException {

		JavascriptExecutor jsx = (JavascriptExecutor) driver;
		// Will get the Screen height on the display
		Long y2 = (Long) jsx.executeScript("return window.innerHeight");

		System.out.println(y2);
		// Will get the complete scroll height on the screen
		Long y = (Long) jsx
				.executeScript("return document.documentElement.scrollHeight");

		long screenScrollSize = y / y2;

		System.out.println("Number of screens to scroll by :::"
				+ screenScrollSize);

		System.out.println(y);
		// Create a PDF
		createPDF();
		// Scroll exactly to next page
		for (int i = 1; i <= screenScrollSize; i++) {
			TestUtil.captureScreenShot(i);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"
					+ y2 + ")");

			writeImagesToPdf(i);
		}
		document.close();
	}

	public static void executeServer() throws IOException {

		String command = "cmd /C start" + System.getProperty("usr.dir")
				+ "//src//test//java//config//run_test.bat";
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(command);
	}

}
