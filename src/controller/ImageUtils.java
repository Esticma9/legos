package controller;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;

public abstract class ImageUtils {
    
    public static final String CAM_URL = "192.168.0.106:8585";
    
    private static Image image = null;
    private static URL url;
    
    private static final int OUTPUT_WIDTH = 400;
    private static final int OUTPUT_HEIGHT = 300;
    
    public static void InitializeUrl() throws MalformedURLException {
		url = new URL("http://" + CAM_URL + "/shot.jpg");
    }
    
    public static Image getImage() throws IOException {
    	if(url!= null) {
            image = ImageIO.read(url);
            return image;
    	}
    	else {
    		return null;
    	}
    }
    
    public static Mat getFilteredImage(Scalar lowHsv, Scalar highHsv, int threshold, Size finalSize, Mat image) {
    	Mat solved = image.clone();

		Imgproc.cvtColor(image, solved, Imgproc.COLOR_BGR2HSV);
    	Core.inRange(solved, lowHsv, highHsv, solved);
    	Imgproc.resize(solved, solved, finalSize, 0, 0, Imgproc.INTER_LINEAR_EXACT);
		Imgproc.threshold(solved, solved, threshold, 255, Imgproc.THRESH_BINARY);
    	return solved;
    }
    
    public static void getArray(Mat src, String path, Size size) {
    	try {
        	Files.write(Paths.get(path), getStandardDump(src,size).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static String getStandardDump(Mat src, Size size) throws Exception {
    	StringBuilder output = new StringBuilder();
    	for(int i=0; i<size.height; i++) {
    		for(int j=0;j<size.width;j++) {
    			int value = (int)src.get(i,j)[0];
    			switch (value) {
	    			case 0:
	    				output.append(' ');
	    				break;
	    			case 75:
	    				output.append('S');
	    				break;
	    			case 150:
	    				output.append('E');
	    				break;
	    			case 255:
	    				output.append('#');
	    				break;
    				default:
    					output.append(' ');
    			}
    		}
    		output.append("\r\n");
    	}
    	return output.toString();
    }
    
    public static Image getScaledImage(Mat image) {
    	return HighGui.toBufferedImage(image).getScaledInstance(OUTPUT_WIDTH, OUTPUT_HEIGHT, Image.SCALE_FAST);
    }
    
    public static Mat cropImage(Mat image, int startX, int width, int startY,  int height) {
    	return image.submat(startY, (startY + height), startX, (startX + width));
    }
    
    public static Size getFirstPixel(Mat src, int width, int height, int compare) {
    	for(int i=0; i<height; i++) {
    		for(int j=0;j<width;j++) {
    			double value = src.get(i,j)[0];
    			if(value == compare) {
    				return new Size(i,j);
    			}
    		}
    	}
    	return null;
    }
}
