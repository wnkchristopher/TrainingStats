
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;

public class Frame {
    private JFrame frame;
    DataManger dataManger;
    TrainingStatsFrame trainingStatsFrame;
    PdfFrame pdfFrame;

    public Frame() {
        dataManger = new DataManger();
    }

    public JFrame createFrame(String title, int width, int height) {

        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle(title);
        frame.getContentPane().setBackground(Color.decode("#ffddc1"));
        frame.setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame = frame;

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        showMainPage();

        frame.setVisible(true);
        return frame;
    }

    public void showMainPage() {
        JLabel label = new JLabel();
        label.setBounds(70, 20, 250, 30);
        label.setText("Your training manager");
        label.setFont(new Font("ITALIC", 2, 25));
        label.setVisible(true);

        JPanel pnlExercises = new JPanel();
        pnlExercises.setLayout(null);
        pnlExercises.setBounds(30, 80, 350, 110);
        pnlExercises.setBackground(Color.decode("#ffddc1"));

        JComboBox cBExercises = new JComboBox();
        cBExercises.setFont(new Font("Helvetica", 3, 16));
        cBExercises.setBounds(0, 0, 270, 50);
        cBExercises.setVisible(true);
        Iterator iterator = dataManger.getExerciseList().iterator();
        while (iterator.hasNext()) {
            cBExercises.addItem(iterator.next());
        }

        JButton btnDeleteExercise = new JButton();
        btnDeleteExercise.setBounds(273, 0, 37, 50);
        ImageIcon imageIcon = new ImageIcon("resources/img/delete_small.png");
        Image image = imageIcon.getImage(); // transform it
        Image newImg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newImg);
        btnDeleteExercise.setIcon(imageIcon);

        //TODO: error message if deletion did not work
        btnDeleteExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String exercise = cBExercises.getSelectedItem().toString();
                int dialogResult = JOptionPane.showConfirmDialog (null,
                        "Are you sure to delete " + exercise + "?","Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    dataManger.deleteExercise(exercise);
                    cBExercises.removeItem(exercise);
                }
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDeleteExercise.setBackground(Color.GREEN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDeleteExercise.setBackground(UIManager.getColor("control"));
            }
        });


        pnlExercises.add(btnDeleteExercise);

        JButton btnAddExercise = new JButton();
        btnAddExercise.setText("Add exercise");
        btnAddExercise.setBounds(0, 60, 310, 50);
        btnAddExercise.setFont(new Font("Helvetica", 1, 16));
        btnAddExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputExercise = JOptionPane.showInputDialog("New exercise:");
                if (inputExercise == null || inputExercise.equals("")) {
                    return;
                }
                if (!dataManger.proveExerciseExists(inputExercise)) {
                    cBExercises.addItem(inputExercise);
                }
                dataManger.addNewExercise(inputExercise);
            }
        });

        pnlExercises.add(cBExercises);
        pnlExercises.add(btnAddExercise);

        JButton btnAddTraining = new JButton();
        btnAddTraining.setBounds(30, 200, 310, 50);
        btnAddTraining.setText("Add training stats");
        btnAddTraining.setFont(new Font("Helvetica", 1, 16));
        btnAddTraining.setVisible(true);
        btnAddTraining.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trainingStatsFrame = new TrainingStatsFrame();
                trainingStatsFrame.createFrame();
            }
        });

        JButton btnGeneratePdf = new JButton();
        btnGeneratePdf.setBounds(30, 260, 310, 50);
        btnGeneratePdf.setFont(new Font("Helvetica", 1, 16));
        btnGeneratePdf.setText("Generate PDFs");
        btnGeneratePdf.setVisible(true);
        btnGeneratePdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdfFrame = new PdfFrame();
                pdfFrame.createFrame();
            }
        });

        pnlExercises.setVisible(true);
        this.frame.add(btnAddTraining);
        this.frame.add(btnGeneratePdf);
        this.frame.add(pnlExercises);
        this.frame.add(label);

        this.addWeightPanel();

    }

    private void addWeightPanel(){


        JPanel pnlWeight = new JPanel();
        pnlWeight.setOpaque(false);
        pnlWeight.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlWeight.setBounds(30,320,310,80);

        GroupLayout groupLayout = new GroupLayout(pnlWeight);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        pnlWeight.setLayout(groupLayout);

        JLabel lblWeight = new JLabel();
        lblWeight.setText("Your Weight");

        JTextField txtWeight = new JTextField();
        txtWeight.setText("80");

        JLabel lblKilo = new JLabel();
        lblKilo.setText("kg");

        JTextField txtDate = new JTextField();
        txtDate.setText("25.02.2020");

        JButton btnSubmitWeight = new JButton();
        btnSubmitWeight.setText("update");
        Dimension dimension = btnSubmitWeight.getPreferredSize();
        dimension.height = 50;
        btnSubmitWeight.setPreferredSize(dimension);
        btnSubmitWeight.setMinimumSize(dimension);
        btnSubmitWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = dataManger.convertToDate(txtDate.getText());
                if (date == null) {
                    JOptionPane.showMessageDialog(null, "Format of date is wrong",
                            "Error: Date", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String entry = txtDate.getText() + "|0|" + txtWeight.getText();
                dataManger.writeExerciseStats("weight", entry, date, ExerciseType.BODYWEIGHT);
            }
        });

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                    .addComponent(lblWeight)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(txtWeight)
                                        .addComponent(lblKilo)
                                )
                            .addComponent(txtDate)
                        )
                        .addComponent(btnSubmitWeight)

        );

        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblWeight)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(txtWeight)
                                        .addComponent(lblKilo)
                                )
                            .addComponent(txtDate)
                        )
                        .addComponent(btnSubmitWeight)
        );


        pnlWeight.setVisible(true);

        frame.add(pnlWeight);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}