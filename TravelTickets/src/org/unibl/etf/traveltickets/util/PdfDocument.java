package org.unibl.etf.traveltickets.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class PdfDocument {
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private static final Random rand = new Random();

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public static String makePdf(String name, String surname, String date, String destination,String type) {
		String fileName = "";
		try {
			PdfPTable table = new PdfPTable(2);
		    table.setWidthPercentage(100);
		    table.setWidths(new int[]{50, 50});
		    table.addCell(createTextCell(name, surname, date, destination,type));
		    table.addCell(createImageCell());
		    table.completeRow();
		    Document document = new Document(new Rectangle(350, 225));
			File file = new File(new File("").getAbsolutePath(), System.currentTimeMillis() + ".pdf");
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
		    document.add(table);
			document.close();
			fileName = file.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return fileName;
	}
	public static PdfPCell createImageCell() throws DocumentException, IOException {
		Integer number = 5 + rand.nextInt(100);
		File file = QRCode.from(number.toString()).to(ImageType.JPG).withSize(150, 150).file();
		Image img = Image.getInstance(file.getAbsolutePath());
	    PdfPCell cell = new PdfPCell(img, true);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	    return cell;
	}
	public static PdfPCell createTextCell(String name, String surname, String date, String destination,String type) throws DocumentException, IOException {
	    PdfPCell cell = new PdfPCell();
	    Paragraph p=new Paragraph(type+" Ticket",catFont);
	    addEmptyLine(p, 1);
		p.add(new Paragraph("Name:" + name, subFont));
		p.add(new Paragraph("Surname:" + surname, subFont));
		p.add(new Paragraph("Destination:" + destination, subFont));
		p.add(new Paragraph("Date:" + date, subFont));
		p.setAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	    cell.addElement(p);
	    return cell;
	}
	
}
