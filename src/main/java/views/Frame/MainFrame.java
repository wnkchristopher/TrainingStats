package views.Frame;

import configuration.Constants;
import enums.PanelType;
import views.PdfPanel;
import views.StartPanel;
import views.TrainingStatsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainFrame implements FrameContentChanger{

    private JFrame frame;
    private StartPanel startPanel;
    private TrainingStatsPanel trainingStatsPanel;
    private PdfPanel pdfPanel;
    private JPanel pnlStart;
    private JPanel pnlTrainingStats;
    private JPanel pnlPdf;

    public MainFrame(StartPanel startPanel, TrainingStatsPanel trainingStatsPanel, PdfPanel pdfPanel){
        this.startPanel = startPanel;
        this.trainingStatsPanel = trainingStatsPanel;
        this.pdfPanel = pdfPanel;

        this.pnlStart = this.startPanel.getPnlMain();
        this.pnlTrainingStats = this.trainingStatsPanel.getPnlTrainingStats();
        this.pnlPdf = this.pdfPanel.getPnlPdfGeneration();

        this.createFrame();

    }

    private void createFrame() {
        this.frame = new JFrame();
        this.frame.setLayout(new BorderLayout());
        this.frame.setTitle(Constants.StartPanelTitle);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(Constants.PathLogoImage));
        this.frame.setIconImage(imageIcon.getImage());

        this.changeGui(PanelType.START_PANEL);

        this.frame.setVisible(true);

        this.pack();
        this.setMinimumSize();
        this.centerFrame();
    }


    private void centerFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation
                (dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    private void pack() {
        this.frame.pack();
        Dimension dimension = new Dimension();

        //uncomment part, is to set minimum preferred size of all panels

   /*     Dimension dimStart = this.pnlStart.getPreferredSize();
        Dimension dimTrain = this.pnlTrainingStats.getPreferredSize();
        Dimension dimPdf = this.pnlPdf.getPreferredSize();

        dimension.width = dimStart.width;
        if(dimension.width < dimTrain.width) {
            dimension.width = dimTrain.width;
        }
        if(dimension.width < dimPdf.width) {
            dimension.width = dimPdf.width;
        }

        dimension.height = dimStart.height;
        if(dimension.height < dimTrain.height) {
            dimension.height = dimTrain.height;
        }
        if(dimension.height < dimPdf.height) {
            dimension.height = dimPdf.height;
        }*/

        //set size manual because it looks better
        dimension.height = 610;
        dimension.width = 720;

        this.frame.setSize(dimension);
    }

    private void setMinimumSize() {
        Dimension dimension = new Dimension();
        Dimension dimStart = this.pnlStart.getMinimumSize();
        Dimension dimTrain = this.pnlTrainingStats.getMinimumSize();
        Dimension dimPdf = this.pnlPdf.getMinimumSize();

        dimension.width = dimStart.width;
        if(dimension.width < dimTrain.width) {
            dimension.width = dimTrain.width;
        }
        if(dimension.width < dimPdf.width) {
            dimension.width = dimPdf.width;
        }

        dimension.height = dimStart.height;
        if(dimension.height < dimTrain.height) {
            dimension.height = dimTrain.height;
        }
        if(dimension.height < dimPdf.height) {
            dimension.height = dimPdf.height;
        }

        Dimension minSize = dimension;
        minSize.width += this.frame.getInsets().left + this.frame.getInsets().right;
        minSize.height += this.frame.getInsets().top + this.frame.getInsets().bottom;
        this.frame.setMinimumSize(minSize);
    }

    @Override
    public void changeGui(PanelType panelType) {
        this.frame.getContentPane().removeAll();
        if(panelType == PanelType.START_PANEL){
            this.frame.add(this.pnlStart, BorderLayout.CENTER);
            this.pnlStart.setVisible(true);
        }else if(panelType == PanelType.TRAINING_STATS_PANEL) {
            this.frame.add(this.pnlTrainingStats, BorderLayout.CENTER);
            this.pnlTrainingStats.setVisible(true);
        }else if(panelType == PanelType.PDF_PANEL) {
            this.frame.add(this.pnlPdf, BorderLayout.CENTER);
            this.pnlPdf.setVisible(true);
        }
        this.frame.getContentPane().revalidate();
        this.frame.getContentPane().repaint();
    }

}
