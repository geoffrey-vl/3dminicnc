/*
 * GUI
 * 
 * Created on Apr 8, 2012, 4:31:02 PM
 */
package PresentationLayer;

// <editor-fold defaultstate="collapsed" desc="Imports">
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imgscalr.Scalr;
import LogicLayer.BL_Communication;
import LogicLayer.BL_ImageHandler;
import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import static org.imgscalr.Scalr.*;
// </editor-fold>

/**
 *
 * @author Dempsey, Geoffrey, Jens
 * 
 */
public class GUI extends javax.swing.JFrame {
        
        // <editor-fold defaultstate="collapsed" desc="FIELDS">
	// Business Layer object
	private BL_Communication bl;
	// Business Layer image handler
	private BL_ImageHandler ih;
	// Distance to move the machine head
	private double distance;
	// The image that is loaded
	private BufferedImage bufferedImage;
	// List with gcode commands
	private ArrayList<String> gcode;
	private static final String newline = "\n";
        // </editor-fold>


	
        // <editor-fold defaultstate="collapsed" desc="Initialization">
	/** Creates new form NewJFrame */
	public GUI() {
		initComponents();
                
                //WAIT FOR WINDOW IS LOADED AND DISPLAYED (via event listener)
                this.addWindowListener(new WindowListener() {

                        @Override
                        public void windowOpened(WindowEvent e) {

                            System.out.println("Application opened");
                            init();
                        }

                        @Override
                        public void windowClosing(WindowEvent e) {}

                        @Override
                        public void windowClosed(WindowEvent e) {}

                        @Override
                        public void windowIconified(WindowEvent e) {}

                        @Override
                        public void windowDeiconified(WindowEvent e) {}

                        @Override
                        public void windowActivated(WindowEvent e) {}

                        @Override
                        public void windowDeactivated(WindowEvent e) {}
                });
                
		this.distance = 0.1;
                jButtonSend.setEnabled(false);
                jButtonConvert.setEnabled(false);
		
                jTextPaneMessages.setEditable(false);
                appendText("Scanning for serial ports, this may take a while... Meanwhile the program may not respond", Color.BLUE);
	}
        
        
        
        
        
        /**
         * Method inits the datalayer AFTER the window is showed
         */
        private void init() {         
            //wait a while for text to add to displaybox
            try {Thread.sleep(1000);} 
            catch (Exception evt) {}

            //init datalayer
            bl = new BL_Communication();


            //get all available ports from business layer and add them to dropdown box
            ArrayList<String> ports = bl.getPortList();

            for(int i = 0; i < ports.size(); i++) {
                    jComboBoxPorts.addItem(ports.get(i));
            }

            appendText("Scanning complete, you can now select the COM port above", Color.GREEN);
        }
        // </editor-fold>
        
	
        
        
        
        /**
         * Method which appends text to the GUI console textfield
         * @param text
         * @param color 
         */
	public static void appendText(String text, Color color) {
		try {
			SimpleAttributeSet set = new SimpleAttributeSet();
			jTextPaneMessages.setCharacterAttributes(set, true);
			StyleConstants.setForeground(set, color);
			Document doc = jTextPaneMessages.getStyledDocument();
			doc.insertString(doc.getLength(), text + newline, set);	
		}
		catch (BadLocationException ex) {
		}
	}
        
        
        /**
         * Method which appends text to the Load gcode textfield
         * @param text
         * @param color 
         */
	public void appendText2(String text, Color color) {
		try {
			SimpleAttributeSet set = new SimpleAttributeSet();
			jTextPaneGCodeLoaded.setCharacterAttributes(set, true);
			StyleConstants.setForeground(set, color);
			Document doc = jTextPaneGCodeLoaded.getStyledDocument();
			doc.insertString(doc.getLength(), text + newline, set);	
		}
		catch (BadLocationException ex) {
		}
	}
        
        
        
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jButtonLoadImage = new javax.swing.JButton();
        jLabelImage = new javax.swing.JLabel();
        jLabelConvertedImage = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonConvert = new javax.swing.JButton();
        jButtonSend = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneLayers = new javax.swing.JTextPane();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPaneDepth = new javax.swing.JTextPane();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPaneDiameter = new javax.swing.JTextPane();
        jLabelHeight = new javax.swing.JLabel();
        jLabelWidth1 = new javax.swing.JLabel();
        jSliderScale = new javax.swing.JSlider();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabelWidth = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonUp = new javax.swing.JButton();
        leftButton = new javax.swing.JButton();
        jButtonDown = new javax.swing.JButton();
        rightButton = new javax.swing.JButton();
        dropdownDistance = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButtonZUP = new javax.swing.JButton();
        jButtonZDOWN = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextPaneOneLine = new javax.swing.JTextPane();
        jButtonSendOne = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButtonBrowseGcode = new javax.swing.JButton();
        jLabelSelectedFile = new javax.swing.JLabel();
        jButtonSendGcode = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextPaneGCodeLoaded = new javax.swing.JTextPane();
        jLabel25 = new javax.swing.JLabel();
        jPanelMachineText = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPaneMessages = new javax.swing.JTextPane();
        jButtonConnect = new javax.swing.JButton();
        jComboBoxPorts = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxFeed = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jScrollPane4.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonLoadImage.setText("Browse Image..");
        jButtonLoadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadImageActionPerformed(evt);
            }
        });

        jLabelImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelImage.setMaximumSize(new java.awt.Dimension(18, 20));

        jLabelConvertedImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabel2.setText("Preview");

        jButtonConvert.setText("Convert");
        jButtonConvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConvertActionPerformed(evt);
            }
        });

        jButtonSend.setText("Send");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });

        jLabel12.setText("Click 'Browse Image' to select a picture, set parameters, click 'Convert' and finally 'Send' to machine");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabel10.setText("Parameters");

        jLabel13.setText("Layers");

        jTextPaneLayers.setText("1");
        jScrollPane1.setViewportView(jTextPaneLayers);

        jLabel14.setText("Depth");

        jTextPaneDepth.setText("2");
        jScrollPane2.setViewportView(jTextPaneDepth);

        jLabel15.setText("Diameter");

        jTextPaneDiameter.setText("2");
        jScrollPane3.setViewportView(jTextPaneDiameter);

        jLabelHeight.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jSliderScale.setMaximum(180);
        jSliderScale.setMinimum(10);
        jSliderScale.setPaintLabels(true);
        jSliderScale.setPaintTicks(true);
        jSliderScale.setSnapToTicks(true);
        jSliderScale.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderScaleStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel19.setText("mm");

        jLabel20.setText("mm");

        jLabel16.setText("Width:");

        jLabel17.setText("Height:");

        jLabel21.setText("mm");

        jLabel22.setText("mm");

        jLabelWidth.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabel24.setText("Image output size");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addContainerGap(166, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addContainerGap(622, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonLoadImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                                .addComponent(jButtonConvert))
                            .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabelWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel14))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel22)
                                            .addComponent(jLabel21))
                                        .addGap(18, 18, 18)
                                        .addComponent(jSliderScale, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabelConvertedImage, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonSend))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(58, 58, 58))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGap(349, 349, 349)
                                    .addComponent(jLabelWidth1)
                                    .addGap(377, 377, 377)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addContainerGap())))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButtonLoadImage)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelConvertedImage, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButtonConvert)
                                            .addComponent(jButtonSend))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(9, 9, 9)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel13)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel20))
                                    .addComponent(jLabel14)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel19))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabelWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabelWidth1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSliderScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41))
        );

        jTabbedPane1.addTab("X SCAN", jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonUp.setText("UP");
        jButtonUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpActionPerformed(evt);
            }
        });

        leftButton.setText("LEFT");
        leftButton.setName("leftButton"); // NOI18N
        leftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftButtonActionPerformed(evt);
            }
        });

        jButtonDown.setText("DOWN");
        jButtonDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownActionPerformed(evt);
            }
        });

        rightButton.setText("RIGHT");
        rightButton.setName("rightButton"); // NOI18N
        rightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightButtonActionPerformed(evt);
            }
        });

        dropdownDistance.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.1", "0.2", "0.5", "1.0", "5.0", "10.0" }));
        dropdownDistance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropdownDistanceActionPerformed(evt);
            }
        });

        jLabel1.setText("cm");

        jLabel5.setText("X / Y Positioning");

        jLabel6.setText("Z Positioning");

        jButtonZUP.setText("UP");
        jButtonZUP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZUPActionPerformed(evt);
            }
        });

        jButtonZDOWN.setText("DOWN");
        jButtonZDOWN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZDOWNActionPerformed(evt);
            }
        });

        jLabel7.setText("Parameters");

        jLabel8.setText("Distance");

        jLabel11.setText("Here you can control the machine by setting a distance and pressing the control buttons");

        jLabel18.setText("Send Code");

        jScrollPane6.setViewportView(jTextPaneOneLine);

        jButtonSendOne.setText("Send");
        jButtonSendOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendOneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel18)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addComponent(dropdownDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(56, 56, 56)
                                                .addComponent(jLabel5))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(68, 68, 68)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jButtonUp, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jButtonDown))))
                                        .addGap(106, 106, 106)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jButtonZUP, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButtonZDOWN)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(leftButton)
                                        .addGap(75, 75, 75)
                                        .addComponent(rightButton))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButtonSendOne)))))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonUp)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(leftButton)
                                    .addComponent(rightButton))))
                        .addGap(14, 14, 14)
                        .addComponent(jButtonDown))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonZUP)
                        .addGap(14, 14, 14)
                        .addComponent(jButtonZDOWN))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(dropdownDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSendOne))
                .addGap(91, 91, 91))
        );

        jTabbedPane1.addTab("Motor Control", jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setText("Here you can just select your .Gcode file to execute");

        jButtonBrowseGcode.setText("Browse..");
        jButtonBrowseGcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseGcodeActionPerformed(evt);
            }
        });

        jLabelSelectedFile.setText("selected File");

        jButtonSendGcode.setText("Send");
        jButtonSendGcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendGcodeActionPerformed(evt);
            }
        });

        jTextPaneGCodeLoaded.setEditable(false);
        jTextPaneGCodeLoaded.setMaximumSize(new java.awt.Dimension(730, 570));
        jTextPaneGCodeLoaded.setMinimumSize(new java.awt.Dimension(730, 570));
        jScrollPane7.setViewportView(jTextPaneGCodeLoaded);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel25.setText("G Code:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jButtonBrowseGcode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelSelectedFile)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonSendGcode)))
                        .addGap(448, 448, 448))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addContainerGap(690, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSelectedFile)
                    .addComponent(jButtonBrowseGcode)
                    .addComponent(jButtonSendGcode))
                .addGap(23, 23, 23)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Load Gcode", jPanel4);

        jPanelMachineText.setBackground(new java.awt.Color(102, 102, 102));

        jTextPaneMessages.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane5.setViewportView(jTextPaneMessages);

        javax.swing.GroupLayout jPanelMachineTextLayout = new javax.swing.GroupLayout(jPanelMachineText);
        jPanelMachineText.setLayout(jPanelMachineTextLayout);
        jPanelMachineTextLayout.setHorizontalGroup(
            jPanelMachineTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMachineTextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelMachineTextLayout.setVerticalGroup(
            jPanelMachineTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMachineTextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonConnect.setLabel("CONNECT");
        jButtonConnect.setName("connect"); // NOI18N
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("Please select a COM port from the Dropdown list and click Connect.");

        jComboBoxFeed.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "50", "75", "100", "125", "150" }));
        jComboBoxFeed.setSelectedIndex(4);
        jComboBoxFeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFeedActionPerformed(evt);
            }
        });

        jLabel4.setText("Feedrate");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabel23.setText("CONSOLE");

        jMenu1.setText("Menu");

        jMenuItemSave.setLabel("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemSave);

        jMenuItem2.setText("About");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setLabel("Exit");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jPanelMachineText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBoxPorts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jButtonConnect)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxPorts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConnect)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxFeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelMachineText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("frame1");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
// <editor-fold defaultstate="expanded" desc="BUTTONS Code">
//LEFT BUTTON
private void leftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftButtonActionPerformed
	//rekening houden met dropdown van afstand
	bl.setCommand("LEFT", this.distance);
}//GEN-LAST:event_leftButtonActionPerformed


// RIGHT BUTTON
private void rightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightButtonActionPerformed
	bl.setCommand("RIGHT", this.distance);
}//GEN-LAST:event_rightButtonActionPerformed



private void dropdownDistanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropdownDistanceActionPerformed
	this.distance = Double.parseDouble(dropdownDistance.getSelectedItem().toString());
}//GEN-LAST:event_dropdownDistanceActionPerformed



private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
	bl.startConnection((String)jComboBoxPorts.getSelectedItem());
        jLabel3.setText("");
}//GEN-LAST:event_jButtonConnectActionPerformed


// UP BUTTON
private void jButtonUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpActionPerformed
	bl.setCommand("UP", this.distance);
}//GEN-LAST:event_jButtonUpActionPerformed


// DOWN BUTTON
private void jButtonDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDownActionPerformed
	bl.setCommand("DOWN", this.distance);
}//GEN-LAST:event_jButtonDownActionPerformed


// BROWSE FOR IMAGE ON DISC
private void jButtonLoadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadImageActionPerformed
	File inputFile = null;
	JFileChooser fd = new JFileChooser(".");
	int returnVal = fd.showOpenDialog(null);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
		inputFile = fd.getSelectedFile();
	}
   
	try {
		this.bufferedImage = ImageIO.read(inputFile);
		BufferedImage thumbnail = Scalr.resize(bufferedImage, 346);
		jLabelImage.setIcon(new ImageIcon(thumbnail));
		jLabelHeight.setText(Integer.toString(bufferedImage.getHeight()));
		jLabelWidth.setText(Integer.toString(bufferedImage.getWidth()));
		
		double width = this.bufferedImage.getWidth();
		double height = this.bufferedImage.getHeight();	

		if (width > height) {
			double scaleImage = height / width;
			this.jLabelWidth.setText(Integer.toString(jSliderScale.getValue()));
			this.jLabelHeight.setText(Integer.toString((int)Math.round(scaleImage * (double)jSliderScale.getValue())));
		}
		else {
			double scaleImage = width / height;
			this.jLabelHeight.setText(Integer.toString(jSliderScale.getValue()));
			this.jLabelWidth.setText(Integer.toString((int)Math.round(scaleImage * (double)jSliderScale.getValue())));		
		}
                
                jButtonConvert.setEnabled(true);
                
	} catch (IOException ex) {
		JOptionPane.showMessageDialog( null, ex.toString() );
		appendText(ex.toString(), Color.red);
	}
}//GEN-LAST:event_jButtonLoadImageActionPerformed


// CONVERT IMAGE TO GCODE
private void jButtonConvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConvertActionPerformed
	if (this.jLabelImage.getIcon() == null) {
		appendText("Please first load an image", Color.red);
	}
	else {
		appendText("Converting image to gCode, this might take a while", Color.blue);
		// width in mm, diameter frees in mm, aantal lagen, diepte)
		int diameter = Integer.parseInt(this.jTextPaneDiameter.getText());
		int layers = Integer.parseInt(this.jTextPaneLayers.getText());
		int depth = Integer.parseInt(this.jTextPaneDepth.getText());
		int width = Integer.parseInt(jLabelWidth.getText());
		this.gcode = bl.createImage(this.bufferedImage, diameter, layers, depth, width);
		BufferedImage img = bl.getImage();
		BufferedImage thumbnail = Scalr.resize(img, 346);
		jLabelConvertedImage.setIcon(new ImageIcon(thumbnail));
                
                //show gcode in console
                appendText(newline, Color.blue);
                appendText("CONVERTED CODE ************", Color.blue);

                for (int k=0; k<this.gcode.size(); k++) {
                    String s = this.gcode.get(k);
                    appendText(s, Color.blue);
                }
                
                
                //enable send button
                jButtonSend.setEnabled(true);
	}
}//GEN-LAST:event_jButtonConvertActionPerformed


private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jMenuItem1MouseClicked

// CLOSE
private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
	System.exit(0);
}//GEN-LAST:event_jMenuItem1ActionPerformed

// Z UP BUTTON
private void jButtonZUPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZUPActionPerformed
	bl.setCommand("ZUP", this.distance);
}//GEN-LAST:event_jButtonZUPActionPerformed

// Z DOWN BUTTON
private void jButtonZDOWNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZDOWNActionPerformed
	bl.setCommand("ZDOWN", this.distance);
}//GEN-LAST:event_jButtonZDOWNActionPerformed



private void jButtonBrowseGcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseGcodeActionPerformed
	//erase current gcode display text
        jTextPaneGCodeLoaded.setText(null);
            
        JFileChooser fc = new JFileChooser(".");
	this.gcode = new ArrayList<String>();
	
	int returnVal = fc.showOpenDialog(null);
	
	if (returnVal == JFileChooser.APPROVE_OPTION) {
		File f = fc.getSelectedFile();
		jLabelSelectedFile.setText(f.getName());
		//if (file.getName().endsWith("gcode")) {	
		//}
		try {
			BufferedReader inFile = new BufferedReader(new FileReader(f));
			String in = inFile.readLine();
			while (in != null || in.equals("null")) {
				this.gcode.add(in);
				in = inFile.readLine();
                                appendText2(in, Color.black);
			}
			inFile.close();
			
		}
		catch(FileNotFoundException exc) {
			// Should not trigger
		}
		catch(IOException exc) {
			appendText("Error reading file", Color.red);
		}
	}
}//GEN-LAST:event_jButtonBrowseGcodeActionPerformed


// SEND GCODE FROM FILE
private void jButtonSendGcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendGcodeActionPerformed
	if (this.gcode == null) {
		appendText("You have to select a file first", Color.red);
	}
	else {
		bl.sendFile(this.gcode);
	}
}//GEN-LAST:event_jButtonSendGcodeActionPerformed


// SIZE SLIDER
private void jSliderScaleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderScaleStateChanged
	double width = this.bufferedImage.getWidth();
	double height = this.bufferedImage.getHeight();	
	
	if (width > height) {
		double scaleImage = height / width;
		this.jLabelWidth.setText(Integer.toString(jSliderScale.getValue()));
		this.jLabelHeight.setText(Integer.toString((int)Math.round(scaleImage * (double)jSliderScale.getValue())));
	}
	else {
		double scaleImage = width / height;
		this.jLabelHeight.setText(Integer.toString(jSliderScale.getValue()));
		this.jLabelWidth.setText(Integer.toString((int)Math.round(scaleImage * (double)jSliderScale.getValue())));		
	}
}//GEN-LAST:event_jSliderScaleStateChanged


// SELECTED A FEEDRATE
private void jComboBoxFeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFeedActionPerformed
	bl.setFeedRate("F" + jComboBoxFeed.getSelectedItem().toString());
}//GEN-LAST:event_jComboBoxFeedActionPerformed



private void jButtonSendOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendOneActionPerformed
	if (this.jTextPaneOneLine.getText() == null || this.jTextPaneOneLine.getText().trim().equals( "" )) {
		appendText("You have to enter a line of code first", Color.red);
	}
	else {
		bl.sendCommand(this.jTextPaneOneLine.getText());
	}
}//GEN-LAST:event_jButtonSendOneActionPerformed



    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
        if (this.gcode != null) {
            bl.sendFile(this.gcode);
        }
    }//GEN-LAST:event_jButtonSendActionPerformed

    
   
// SAVE GCODE TO DISK 
private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
 	try {
		FileWriter writer = new FileWriter("save.gcode");
                for (String str: gcode) {
                        writer.write(str);
                        writer.write("\n");
                }

                writer.close();
        } catch (IOException ex) {
                appendText(ex.toString(), Color.red);
        }
}//GEN-LAST:event_jMenuItemSaveActionPerformed



//ABOUT
private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
	JOptionPane.showMessageDialog(this,
    "Made by Geoffrey, Jens & Dempsey.",
    "3D Mini CNC Software",
    JOptionPane.INFORMATION_MESSAGE);
}//GEN-LAST:event_jMenuItem2ActionPerformed
// </editor-fold>




// <editor-fold defaultstate="collapsed" desc="Generated Code: Fields + Main method">
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
                /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new GUI().setVisible(true);
			}
		});
	}
	
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox dropdownDistance;
    private javax.swing.JButton jButtonBrowseGcode;
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JButton jButtonConvert;
    private javax.swing.JButton jButtonDown;
    private javax.swing.JButton jButtonLoadImage;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JButton jButtonSendGcode;
    private javax.swing.JButton jButtonSendOne;
    private javax.swing.JButton jButtonUp;
    private javax.swing.JButton jButtonZDOWN;
    private javax.swing.JButton jButtonZUP;
    private javax.swing.JComboBox jComboBoxFeed;
    private javax.swing.JComboBox jComboBoxPorts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelConvertedImage;
    private javax.swing.JLabel jLabelHeight;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelSelectedFile;
    private javax.swing.JLabel jLabelWidth;
    private javax.swing.JLabel jLabelWidth1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelMachineText;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSlider jSliderScale;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPaneDepth;
    private javax.swing.JTextPane jTextPaneDiameter;
    private javax.swing.JTextPane jTextPaneGCodeLoaded;
    private javax.swing.JTextPane jTextPaneLayers;
    private static javax.swing.JTextPane jTextPaneMessages;
    private javax.swing.JTextPane jTextPaneOneLine;
    private javax.swing.JButton leftButton;
    private javax.swing.JButton rightButton;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
