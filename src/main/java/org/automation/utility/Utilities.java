package org.automation.utility;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;


public class Utilities {
	
	private String buildScreenshotFilePath(String path, String testMethodName) {
		path = System.getProperty("user.dir") + File.separator + path + getCurrentDate() + File.separator + "temp";
		String fileName = testMethodName + "_" + UUID.randomUUID() + ".png";
		return path + fileName;
	}
	
	public String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	public static String captureScreenshotWithRobot(String testMethodName, String path) {
		try {
			Robot robotClassObject = new Robot();
			Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage tmp = robotClassObject.createScreenCapture(screenSize);

			path = new Utilities().buildScreenshotFilePath(path, testMethodName);
			File screenshotFile = new File(path);
			FileUtils.touch(screenshotFile);
			// To copy temp image in to permanent file
			ImageIO.write(tmp, "png", screenshotFile);
		} catch (Exception e) {
			//BitrixLogger.fatal("Screenshot using Robot Failed :", e);
		}

		return path;
	}

}
