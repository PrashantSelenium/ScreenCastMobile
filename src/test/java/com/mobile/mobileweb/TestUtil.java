package com.merck.mobileweb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class TestUtil extends DriverScript {
	public static String filename;

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
		document = new Document();
		PdfWriter.getInstance(document,
				new FileOutputStream(System.getProperty("user.dir")
						+ "\\src\\test\\java\\config\\images.pdf"));
		document.open();

		Image image = Image.getInstance(System.getProperty("user.dir")
				+ "\\target\\" + filename);

		/*
		 * Image image = Image
		 * .getInstance("E:\\Sikuli\\MerckScreenCast\\target\\screen2.jpg");
		 */

		image.scaleAbsolute(image.getHeight(), image.getWidth());// image
																	// width,height
		// document.add(new Paragraph("TestImagePdf"));
		document.add(image);

	}
}
