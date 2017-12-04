package com.mao.tess;

import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class App {

	public static void main(String[] args) throws IOException {
		// 先对图片进行处理
		File original = new File("E:/tessdata/pic");
		String destDir = "E:/tessdata/pic/new";
		File[] images = original.listFiles();
		for (File file : images) {
			if(file.isFile()){
				CopyOfCleanLines.cleanLinesInImage(file, destDir);
			}
		}

		//识别
		File dest = new File(destDir);
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath("E:/tessdata");
		try {
			File[] files = dest.listFiles();
			for (File file : files) {
				String result = tesseract.doOCR(file);
				String fileName = file.getName().substring(0, file.getName().length()-4);
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<result.length();i++){
					char tmp = result.charAt(i);
					if(Character.isLetterOrDigit(tmp)){
						sb.append(tmp);
					}
				}
				System.out.println(fileName + ":" + sb.toString());
			}
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
	}
}
