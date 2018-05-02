/**
 * 
 */
package com.xa3ti.blackcat.base.util;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ThumbnailImage {
	
	
	  /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param imagePath    原图片路径
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param prevfix    生成缩略图的前缀
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public static boolean scale(String imagepath,String newpath, int w, int h){
       
            try {
            	BufferedImage image=null; 
        		try { 
        			image = ImageIO.read(new File(imagepath));
        			
        		} catch (IOException e) { 
        			System.out.println("读取图片文件出错！"+e.getMessage()); 
        			return false; 
        		} 
        		

//        		Image Itemp = image.getScaledInstance(100, 100, image.SCALE_SMOOTH); 
        		double Ratio = 0.0; 

                if ((image.getHeight() > h) ||(image.getWidth() > w)) { 
                    if (image.getHeight() > image.getWidth()) 
//        				图片要缩放的比例 
                    	Ratio = h / image.getHeight(); 
                    else 
                        Ratio = w / image.getWidth(); 
                } else {
                	return false; 
                }
                
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(image, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                
                FileOutputStream out=null; 
        		try { 
        			out = new FileOutputStream(newpath); 
        			ImageIO.write((BufferedImage)bi,"jpg",out);
        			out.flush();
        			out.close(); 
        		} catch (Exception e) { 
        			System.out.println("写图片文件出错!!"+e.getMessage()); 
        			return false; 
        		} 
        		
            
            } catch (Exception e) {
            	System.out.println("generate thumbnail image failed."+e.getMessage());
            }
            return true; 
       
    }
    
	
	public static boolean scale(String imagepath,String newpath, double w, double h){ 
//		返回一个 BufferedImage，作为使用从当前已注册 ImageReader 中自动选择的 ImageReader 解码所提供 File 的结果 
		BufferedImage image=null; 
		try { 
			image = ImageIO.read(new File(imagepath));
			
		} catch (IOException e) { 
			System.out.println("读取图片文件出错！"+e.getMessage()); 
			return false; 
		} 

        
//		Image Itemp = image.getScaledInstance(100, 100, image.SCALE_SMOOTH); 
		double Ratio = 0.0; 

        if ((image.getHeight() > h) ||(image.getWidth() > w)) { 
            if (image.getHeight() > image.getWidth()) 
//				图片要缩放的比例 
            	Ratio = h / image.getHeight(); 
            else 
                Ratio = w / image.getWidth(); 
        } else {
        	return false; 
        }
//		根据仿射转换和插值类型构造一个 AffineTransformOp。 
        AffineTransformOp op = new AffineTransformOp(AffineTransform 
	                .getScaleInstance(Ratio, Ratio), null); 
//		转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
        
        //image = op.filter(image,null); 
        BufferedImage targetImage=new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_RGB);
        op.filter(image,targetImage); 
//      image.getScaledInstance(100,100,image.SCALE_SMOOTH); 
		FileOutputStream out=null; 
		try { 
			out = new FileOutputStream(newpath); 
			ImageIO.write((BufferedImage)targetImage,"bmp",out);
			out.flush();
			out.close(); 
		} catch (Exception e) { 
			System.out.println("写图片文件出错!!"+e.getMessage()); 
			return false; 
		} 
		return true; 
	}
	
	
	public static String createThumbnailImage(String thumbnailAbsolutePath,String relativePath, String originalPath) {
		String url = relativePath;
		double w = Double.parseDouble(Settings.getInstance().getString("thumbnail.width"));
		double h = Double.parseDouble(Settings.getInstance().getString("thumbnail.height"));
		if (!ThumbnailImage.scale(originalPath, thumbnailAbsolutePath, w, h)) {
			return null;
		}
		return url;
	}
	
	
	public static String createThumbnailImage2(String thumbnailAbsolutePath,String relativePath, String originalPath) {
		String url = relativePath;
		int w =(int) Double.parseDouble(Settings.getInstance().getString("thumbnail.width"));
		int h = (int)Double.parseDouble(Settings.getInstance().getString("thumbnail.height"));
		if (!ThumbnailImage.scale(originalPath, thumbnailAbsolutePath, w, h)) {
			return null;
		}
		return url;
	}
}
