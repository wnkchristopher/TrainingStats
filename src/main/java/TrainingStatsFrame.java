import com.sun.javafx.scene.traversal.Direction;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class TrainingStatsFrame {
    JFrame frame;
    PlaceholderTextField txtDate;
    DataManger dataManger;
    Map<ExerciseSet, PlaceholderTextField[]> txtFields = new HashMap<>();//1 for Reps, 2 for Weight
    List<String> exerciseOrder = new ArrayList<>();
    JPanel contentPanel;
    Map<String, JPanel> exercisePanels = new HashMap<>();
    JButton btnSubmit;

    public TrainingStatsFrame() {
        dataManger = new DataManger();
    }

    public void createFrame() {
        this.exerciseOrder = this.dataManger.getExerciseList();
        this.createFrame(1000, 1000);
    }

    public void createFrame(int width, int height) {
        frame = new JFrame();
        frame.getContentPane().setBackground(Color.decode("#ffddc1"));
        frame.setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(null);
        frame.setTitle("Add your training stats");

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        this.addHeadline();
        this.addInputDate();
        this.addAddButton();
        this.addContentPanel();
        this.addInfo();

        frame.getRootPane().setDefaultButton(btnSubmit); //enter for button

        frame.setVisible(true);
    }

    private void addInfo() {
        int width = 30;
        int top = 50;
        int distanceRight = 40;

        JLabel lblInfo = new JLabel();
        ImageIcon tmpImageHelp = new ImageIcon("resources/img/help_small.png");
        Image image = tmpImageHelp.getImage(); // transform it
        Image newImg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        final ImageIcon imageHelp = new ImageIcon(newImg);
        lblInfo.setIcon(imageHelp);

        ImageIcon tmpImageHelpHover = new ImageIcon("resources/img/help_hover_small.png");
        image = tmpImageHelpHover.getImage(); // transform it
        newImg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        final ImageIcon imageHelpHover = new ImageIcon(newImg);

        JLabel tmpLblText = new JLabel();
        tmpLblText.setOpaque(true);
        tmpLblText.setBackground(Color.white);
        tmpLblText.setFont(new Font("Serif", Font.BOLD, 16));
        tmpLblText.setText("<html><body>For body weight exercises " +
                "enter a 'b' or leave it empty<br>" +
                "extra weight: b+extra weight <br>" +
                "support weight: b-support weight");
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        Border margin = new EmptyBorder(10, 10, 10, 10);
        tmpLblText.setBorder(new CompoundBorder(border, margin));
        tmpLblText.setBounds(this.frame.getWidth() - width - distanceRight - 273, top - 38, 270, 90);

        final JLabel lblText = tmpLblText;
        lblText.setVisible(false);
        frame.add(lblText);


        lblInfo.setBounds(this.frame.getWidth() - width - distanceRight, top, width, 30);

        lblInfo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblInfo.setIcon(imageHelpHover);
                frame.add(lblText);
                lblText.setVisible(true);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblInfo.setIcon(imageHelp);
                lblText.setVisible(false);
            }
        });

        lblInfo.setVisible(true);


        this.frame.add(lblInfo);
    }

    private void addHeadline() {
        JLabel label = new JLabel();
        label.setBounds(350, 10, 300, 40);
        label.setText("Add your workout");
        label.setFont(new Font("ITALIC", 2, 27));
        label.setVisible(true);

        this.frame.add(label);
    }


    private void addInputDate() {
        JPanel pnl = new JPanel();
        this.txtDate = new PlaceholderTextField();
        this.txtDate.setPlaceholder("dd.mm.yyyy");
        JLabel lbl = new JLabel();
        lbl.setBounds(0, 0, 50, 30);
        lbl.setText("Date: ");
        lbl.setFont(new Font("Helvetica", 3, 16));
        this.txtDate.setBounds(50, 0, 200, 30);
        pnl.setLayout(null);
        pnl.add(lbl);
        pnl.add(this.txtDate);
        pnl.setBounds(30, 50, 260, 40);
        pnl.setBackground(Color.decode("#ffddc1"));
        this.frame.add(pnl);
    }

    private void addAddButton() {
        this.btnSubmit = new JButton();
        btnSubmit.setBounds(350, 850, 300, 50);
        btnSubmit.setText("Add to your training stats");
        btnSubmit.setFont(new Font("Helvetica", 3, 16));
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dateOfTraining = dataManger.convertToDate(txtDate.getText());
                if (dateOfTraining == null) {
                    JOptionPane.showMessageDialog(null, "Format of date is wrong",
                            "Error: Date", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Map<ExerciseSet, String[]> tmpMap = new HashMap<>();
                for (ExerciseSet exerciseSet : txtFields.keySet()) {    //Get content of textFields
                    String reps = txtFields.get(exerciseSet)[0].getText();
                    String weight = txtFields.get(exerciseSet)[1].getText();
                    String[] s = new String[2];
                    s[0] = reps;
                    s[1] = weight;
                    if (!s[0].isEmpty()) {
                        if (s[1].isEmpty() || s[1].contains("b")) {
                            String digits = s[1].replaceAll("\\D+", "");
                            if (s[1].contains("+")) {
                                s[1] = "b+" + digits;
                                tmpMap.put(exerciseSet, s);
                            } else if (s[1].contains("-")) {
                                s[1] = "b-" + digits;
                                tmpMap.put(exerciseSet, s);
                            }
                        } else {
                            tmpMap.put(exerciseSet, s);
                        }
                    }

                    //refresh for next entry
                    txtFields.get(exerciseSet)[0].setText("");
                    txtFields.get(exerciseSet)[1].setText("");
                }
                dataManger.addWorkout(tmpMap, dateOfTraining);

            }
        });


        this.frame.add(btnSubmit);

    }

    private void addContentPanel() {
        this.contentPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
        contentPanel.setLayout(boxlayout);

        JScrollPane sPExercises = new JScrollPane(contentPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sPExercises.setLocation(10, 90);
        sPExercises.setSize(960, 700);
        sPExercises.setVisible(true);

        sPExercises.getViewport().revalidate();

        for (String s : this.exerciseOrder) {
            //add textfields to maps
            ExerciseSet e1 = new ExerciseSet(s, 1);
            txtFields.put(e1, new PlaceholderTextField[2]);
            ExerciseSet e2 = new ExerciseSet(s, 2);
            txtFields.put(e2, new PlaceholderTextField[2]);

            this.exercisePanels.put(s, this.exercisePanel(s));
            contentPanel.add(this.exercisePanels.get(s));
        }

        this.contentPanel.setBackground(Color.decode("#F0F8FF"));

        this.contentPanel.add(Box.createVerticalGlue());
        this.frame.add(sPExercises);
    }


    private JPanel exercisePanel(String exercise) {
        JPanel panel = new JPanel();
        JTextField txtSaveSetNumber = new JTextField();
        txtSaveSetNumber.setText("2");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(boxLayout);
        panel.setPreferredSize(new Dimension(930, 100));
        panel.setMinimumSize(new Dimension(930, 100));
        panel.setMaximumSize(new Dimension(930, 100));

        JLabel lblExerciseTitle = new JLabel();
        lblExerciseTitle.setText(exercise);
        lblExerciseTitle.setFont(new Font("Helvetica", 1, 16));
        lblExerciseTitle.setPreferredSize(new Dimension(200, 30));
        lblExerciseTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pnlMove = new JPanel();
        pnlMove.setPreferredSize(new Dimension(30, 30));
        pnlMove.setMaximumSize(new Dimension(30, 30));
        pnlMove.setBackground(Color.decode("#A4A4A4"));

        JPanel pnlSpacer = new JPanel();

        JPanel btnUpDown = this.moveUpDownButton(exercise);
        btnUpDown.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton btnPlus = new JButton("+");
        btnPlus.setFont(new Font("Helvetica", 1, 60));
        btnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        //out commented because functionality is not implemented and without a function it is senseless
        //panel.add(pnlMove);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(lblExerciseTitle);
        panel.add(this.getSetPanel(exercise, 1));
        panel.add(this.getSetPanel(exercise, 2));
        panel.add(btnPlus);
        panel.add(pnlSpacer);
        panel.add(btnUpDown);

        pnlSpacer.setBackground(Color.decode("#F0F8FF"));
        panel.setBackground(Color.decode("#F0F8FF"));

        btnPlus.setFocusable(false);

        return panel;
    }

    private JPanel getSetPanel(String exercise, int set) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setPreferredSize(new Dimension(90, 80));
        panel.setMaximumSize(new Dimension(90, 80));

        JLabel lblSet = new JLabel("set " + set);
        lblSet.setPreferredSize(new Dimension(70, 30));
        PlaceholderTextField txtReps = new PlaceholderTextField();
        txtReps.setPreferredSize(new Dimension(70, 20));
        txtReps.setPlaceholder("Reps");
        PlaceholderTextField txtWeight = new PlaceholderTextField();
        txtWeight.setPreferredSize(new Dimension(70, 20));
        txtWeight.setPlaceholder("Weight in kg");


        PlaceholderTextField[] pTF = new PlaceholderTextField[2];
        pTF[0] = txtReps;
        pTF[1] = txtWeight;

        panel.add(lblSet);
        for (ExerciseSet exerciseSet : this.txtFields.keySet()) {
            if (exerciseSet.getExercise().equals(exercise) && exerciseSet.getSet() == set) {
                this.txtFields.put(exerciseSet, pTF);
                panel.add(this.txtFields.get(exerciseSet)[0]);
                panel.add(this.txtFields.get(exerciseSet)[1]);
                break;
            }
        }
        return panel;
    }

    private JPanel moveUpDownButton(String exercise) {
        int img_width, img_height;
        img_width = 30;
        img_height = 30;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setPreferredSize(new Dimension(30, 80));

        JButton btnUp = new JButton();
        ImageIcon imageIcon = new ImageIcon("resources/img/angle_up.png");
        Image image = imageIcon.getImage(); // transform it
        Image newImg = image.getScaledInstance(img_width, img_height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newImg);
        btnUp.setIcon(imageIcon);

        JButton btnDown = new JButton();
        imageIcon = new ImageIcon("resources/img/angle_down.png");
        image = imageIcon.getImage();
        newImg = image.getScaledInstance(img_width, img_height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newImg);
        imageIcon = new ImageIcon(newImg);
        btnDown.setIcon(imageIcon);

        btnDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapExerciseOrder(exercise, Direction.DOWN);
            }
        });

        btnUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapExerciseOrder(exercise, Direction.UP);
            }
        });

        panel.setPreferredSize(new Dimension(40, 80));
        panel.setMaximumSize(new Dimension(40, 80));

        panel.add(btnUp);
        panel.add(btnDown);
        panel.validate();

        btnUp.setFocusable(false);
        btnDown.setFocusable(false);

        return panel;
    }

    private void swapExerciseOrder(String exercise, Direction direction) {
        int position = this.exerciseOrder.indexOf(exercise);
        int max = this.exerciseOrder.size() - 1;
        if (direction == Direction.UP) {
            if (position > 0) {
                Collections.swap(this.exerciseOrder, position, position - 1);
            }
        } else if (direction == Direction.DOWN) {
            if (position < max) {
                Collections.swap(this.exerciseOrder, position, position + 1);
            }
        }
        dataManger.changeExerciseOrder(this.exerciseOrder);
        this.refresh(exercise);
    }

    private void refresh(String exercise) {
        this.contentPanel.removeAll();
        for (String e : this.exerciseOrder) {
            this.contentPanel.add(this.exercisePanels.get(e));
        }
        this.contentPanel.validate();
        this.contentPanel.repaint();
    }

}