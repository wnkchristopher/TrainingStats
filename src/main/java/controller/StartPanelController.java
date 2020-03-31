package controller;

import configuration.Constants;
import controller.validation.DateValidator;
import enums.PanelType;
import models.DataManager;
import models.DateManager;
import views.*;
import views.Frame.FrameContentChanger;
import views.Frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class StartPanelController implements Observer {
    private DataManager dataManager;
    private StartPanel startPanel;
    private FrameContentChanger frame;
    private PdfPanel pdfPanel;

    public StartPanelController(DataManager dataManager, StartPanel startPanel,
                                FrameContentChanger frame) {
        this.dataManager = dataManager;
        this.startPanel = startPanel;
        this.frame = frame;
        this.pdfPanel = pdfPanel;
        this.dataManager.addObserver(this);

        this.startPanel.getBtnAddExercise().addActionListener(e -> {
            String inputExercise = JOptionPane.showInputDialog("New exercise:");
            if (inputExercise == null || inputExercise.equals("")) {
                return;
            }
            this.dataManager.addExercise(inputExercise);
        });

        this.startPanel.getBtnEditExercise().addActionListener(e -> {
            String exercise = startPanel.getcBExercises().getSelectedItem().toString();
            String newName = JOptionPane.showInputDialog("Rename " + exercise + " to: ");
            if (newName != null) {
                if (dataManager.changeExerciseName(exercise, newName)) {
                } else {
                    JOptionPane.showMessageDialog(null, newName + " already exists");
                }
            }
        });

        this.startPanel.getBtnDeleteExercise().addActionListener(e -> {
            String exercise = startPanel.getcBExercises().getSelectedItem().toString();
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete " + exercise + "?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                dataManager.deleteExercise(exercise);
            }
        });

        this.startPanel.getBtnAddTraining().addActionListener(e ->
            this.frame.changeGui(PanelType.TRAINING_STATS_PANEL)
        );

        this.startPanel.getBtnGeneratePdf().addActionListener(e ->
            this.frame.changeGui(PanelType.PDF_PANEL)
        );

        this.startPanel.getBtnSubmitWeight().addActionListener(e -> {
            String strDate = startPanel.getTxtDate().getText();


            boolean valid = true;
            DateValidator dateValidator = new DateValidator();
            if(!dateValidator.verify(strDate)) {
                startPanel.getTxtDate().setBorder(BorderFactory.createLineBorder(Color.red));
                valid = false;
            }

            Double weight = 0.0;
            try{
                weight = Double.valueOf(startPanel.getTxtWeight().getText());
            }catch(NumberFormatException n) {
                startPanel.getTxtWeight().setBorder(BorderFactory.createLineBorder(Color.red));
                valid = false;
            }
            if(!valid) {
                return;
            }

            Date date = DateManager.convertStringToDate(startPanel.getTxtDate().getText());

            this.dataManager.addWeight(date, weight);

            startPanel.getTxtDate().
                    setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
            startPanel.getTxtWeight().
                    setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o.equals(dataManager)){
            this.startPanel.refresh(this.dataManager.getExercises());
            if(arg.equals(Constants.StartProgram)){
                Double lastWeight =
                        this.dataManager.getWeight(DateManager.convertStringToDate(DateManager.getCurrentDate()));
                this.startPanel.getTxtWeight().setText(String.valueOf(lastWeight));
            }
            if(arg.equals(Constants.AddedExercise)) {
                this.startPanel.getcBExercises().setSelectedItem(this.dataManager.getNewExercise());
            }
        }
    }
}
