package com.java.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.gdata.util.AuthenticationException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

/**
 * Remarque : Ce code s'inspire d'un tutorial disponible a l'adresse suivante : http://thierry-leriche-dessirier.developpez.com/tutoriels/java/creer-qrcode-zxing-java2d-5-min/
 */

public class QRcode {
	
	/**
	 * Method which creates the matrix which contains the data for the QR Code drawing 
	 * @param data
	 * @param level
	 * @return
	 * @throws WriterException
	 */
    public static ByteMatrix generateMatrix(final String data, final ErrorCorrectionLevel level) throws WriterException {
        final QRCode qr = new QRCode();
        Encoder.encode(data, level, qr);
        final ByteMatrix matrix = qr.getMatrix();
        return matrix;
    }
    
    /**
     * Method which create the QR Code
     * @param outputFileName
     * @param imageFormat
     * @param matrix
     * @param size
     * @param name
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeImage(final String outputFileName, final String imageFormat, final ByteMatrix matrix, final int size, String name)
            throws FileNotFoundException, IOException {

        /**
         * Java 2D Traitement de Area
         */
        Area a = new Area(); 
        Area module = new Area(new Rectangle2D.Float(0.05f, 0.05f, 0.9f, 0.9f));

        AffineTransform at = new AffineTransform(); // pour déplacer le module
        int width = matrix.getWidth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix.get(j, i) == 1) {
                    a.add(module); // on ajoute le module
                }
                at.setToTranslation(1, 0); // on décale vers la droite
                module.transform(at);
            }
            at.setToTranslation(-width, 1); // on saute une ligne on revient au début
            module.transform(at);
        }

        // agrandissement de l'Area pour le remplissage de l'image
        double ratio = size / (double) width;
        
        double adjustment = width / (double) (width + 8);
        ratio = ratio * adjustment;

        at.setToTranslation(4, 4); // à cause de la quietzone
        a.transform(at);

        at.setToScale(ratio, ratio); // on agrandit
        a.transform(at);

        /**
         * Java 2D Traitement l'image
         */
        BufferedImage im = new BufferedImage(size, size+50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        Color couleur1 = new Color(0xFF0000);
        g.setPaint(couleur1);

        g.setBackground(new Color(0xFFFFFF));
        g.clearRect(0, 0, size, size+50); 
        g.fill(a); // remplissage des modules
        
        FontMetrics fm = g.getFontMetrics();
        String s = name;
        int x = (size - fm.stringWidth(s)) / 2;
        int y = size;
        g.setColor(Color.black);
        Font h = new Font("Helvetica", Font.PLAIN, 18);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        g.setFont(h);
        g.drawString(s, x, y);
        g.drawString(date,x,y+30);

        // Ecriture sur le disque
        File f = new File(outputFileName);
        f.setWritable(true);
        try {
            ImageIO.write(im, imageFormat, f);
            f.createNewFile();
        } catch (Exception e) {
        }

    }
}