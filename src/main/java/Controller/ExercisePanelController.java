package Controller;

import Model.DataManger;
import Model.ExerciseSet;
import View.ExercisePanel;
import View.Extensions.PlaceholderTextField;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ExercisePanelController implements Observer {
    private DataManger dataManger;
    private ExercisePanel exercisePanel;

    public ExercisePanelController(DataManger dataManger, ExercisePanel exercisePanel) {
        this.dataManger = dataManger;
        this.exercisePanel = exercisePanel;

        this.exercisePanel.getBtnPlus().addActionListener(e -> {
            System.out.println("plus");
            this.exercisePanel.getPnlExercise().remove(this.exercisePanel.getBtnPlus());
            this.exercisePanel.getPnlExercise().remove(this.exercisePanel.getPnlSpacer());
            this.exercisePanel.getPnlExercise().remove(this.exercisePanel.getPnlBtnUpDown());
           // ExerciseSet e1 = new ExerciseSet(this.exercisePanel.getExercise(), set);

        });
        /*btnPlus.addActionListener(e -> {
            panel.remove(btnPlus);
            panel.remove(pnlSpacer);
            panel.remove(btnUpDown);
            int set = Integer.valueOf(txtSaveSetNumber.getText());
            set++;
            ExerciseSet e1 = new ExerciseSet(exercise, set);
            txtFields.put(e1, new PlaceholderTextField[2]);
            panel.add(getSetPanel(exercise, set));
            txtSaveSetNumber.setText(String.valueOf(set));
            panel.add(btnPlus);
            panel.add(pnlSpacer);
            panel.add(btnUpDown);
            panel.updateUI();
        });*/

        this.exercisePanel.getBtnUp().addActionListener(e -> {
            System.out.println("Up");
            //swapExerciseOrder(exercise, Direction.UP);
        });

        this.exercisePanel.getBtnDown().addActionListener(e -> {
            System.out.println("Down");
            //swapExerciseOrder(exercise, Direction.DOWN);
        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
