package br.com.simulado;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class TestPDF {

	public static void main(String[] args) {
		try {
			String pdfFile = "C:\\Users\\O Javoso\\Music\\Provas do POSCOMP\\2019\\Prova.pdf";

			File filePDF = new File(pdfFile);
			FileInputStream fileInputStream = new FileInputStream(filePDF);

			PDDocument pdfDocument = null;
			try {
				PDFParser parser = new PDFParser(fileInputStream);
				parser.parse();
				pdfDocument = parser.getPDDocument();
				PDFTextStripper stripper = new PDFTextStripper();

				info("Arquivo PDF: ");
				info("");
				info(stripper.getText(pdfDocument));
			} finally {
				if (pdfDocument != null) {
					try {
						pdfDocument.close();
					} catch (IOException e) {
					}
				}
			}
		} catch (Exception e) {
			error(e.toString());
		}
	}

	/**
	 * Log Info.
	 * 
	 * @param log
	 */
	private static void info(String log) {
		System.out.println("INFO: " + log);
	}

	/**
	 * Log Error.
	 * 
	 * @param log
	 */
	private static void error(String log) {
		System.out.println("ERROR: " + log);
	}

}
