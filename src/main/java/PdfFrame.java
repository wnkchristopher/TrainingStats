import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PdfFrame {
    private JFrame frame;
    private JTextField txtFrom;
    private JTextField txtTo;
    private JRadioButton rbMorePdfs;
    private JRadioButton rbOnePdf;
    private JScrollPane sPExercises;
    private GeneratePdf generatePdf;
    private DataManger dataManger;
    private List<JCheckBox> cBExercises;
    private JButton btnGeneratePdf;
    private JPanel pnlExercises;


    public PdfFrame() {
        generatePdf = new GeneratePdf();
        dataManger = new DataManger();
    }

    public void createFrame() {
        frame = new JFrame();
        cBExercises = new ArrayList<>();
        this.pnlExercises = new JPanel();
        this.createFrame(500, 500);

    }

    public void createFrame(int width, int height) {
        frame.setSize(width, height);
        frame.getContentPane().setBackground(Color.decode("#ffddc1"));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Generate PDF");

        ImageIcon imageIcon = new ImageIcon("resources/img/logo.png");
        this.frame.setIconImage(imageIcon.getImage());

        this.addPanel();
        this.addToggleButtons();
        this.addTextFields();
        this.addSendButton();


        frame.getRootPane().setDefaultButton(this.btnGeneratePdf);

        frame.repaint();
        frame.setVisible(true);
    }

    private void addPanel() {
        BoxLayout boxlayout = new BoxLayout(pnlExercises, BoxLayout.Y_AXIS);
        pnlExercises.setLayout(boxlayout);

        sPExercises = new JScrollPane(pnlExercises,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sPExercises.setLocation(50, 50);
        sPExercises.setSize(150, 300);
        sPExercises.setVisible(true);

        sPExercises.getViewport().revalidate();

        List<String> exercises;
        exercises = dataManger.getExerciseList();
        exercises.add(0, Main.bodyWeight);
        pnlExercises.removeAll();
        for (String exercise : exercises) {
            JCheckBox cb = new JCheckBox();
            cb.setText(exercise);
            cb.setBackground(Color.white);
            cb.setSize(150, 30);
            this.cBExercises.add(cb);
            pnlExercises.add(cb);
        }
        pnlExercises.validate();
        pnlExercises.repaint();

        pnlExercises.setBackground(Color.white);
        pnlExercises.setVisible(true);


        this.frame.add(sPExercises);

        pnlExercises.setLocation(50, 50);
        pnlExercises.setSize(150, 300);
        pnlExercises.setVisible(true);
        this.frame.add(sPExercises);

    }

    private void addToggleButtons() {
        rbMorePdfs = new JRadioButton("One PDF for each selected exercise");
        rbOnePdf = new JRadioButton("One PDF for all selected exercises");
        ButtonGroup bgPdf = new ButtonGroup();
        rbMorePdfs.setBounds(50, 370, 240, 20);
        rbOnePdf.setBounds(50, 390, 240, 20);
        bgPdf.add(rbMorePdfs);
        bgPdf.add(rbOnePdf);
        rbMorePdfs.setSelected(true);

        rbMorePdfs.setBackground(Color.decode("#ffddc1"));
        rbMorePdfs.setVisible(true);
        rbOnePdf.setBackground(Color.decode("#ffddc1"));
        rbOnePdf.setVisible(true);
        this.frame.add(rbMorePdfs);
        this.frame.add(rbOnePdf);
    }

    private void addTextFields() {
        JPanel pnlGroupFromTo = new JPanel();

        JLabel lblFrom = new JLabel("From: (dd.mm.yyyy)");
        txtFrom = new JTextField();

        JLabel lblTo = new JLabel("To: (dd.mm.yyyy)");
        txtTo = new JTextField();

        pnlGroupFromTo.setLayout(null);

        lblFrom.setBounds(0, 0, 200, 30);
        txtFrom.setBounds(0, 50, 200, 30);

        lblTo.setBounds(0, 90, 200, 30);
        txtTo.setBounds(0, 130, 200, 30);

        pnlGroupFromTo.setBounds(250, 50, 200, 200);

        pnlGroupFromTo.setBackground(Color.decode("#ffddc1"));

        pnlGroupFromTo.add(lblFrom);
        pnlGroupFromTo.add(txtFrom);
        pnlGroupFromTo.add(lblTo);
        pnlGroupFromTo.add(txtTo);
        pnlGroupFromTo.setVisible(true);
        this.frame.add(pnlGroupFromTo);
    }

    private void addSendButton() {
        this.btnGeneratePdf = new JButton("Generate PDF");

        btnGeneratePdf.setBounds(275, 250, 150, 40);

        btnGeneratePdf.addActionListener(e -> {
            ArrayList<String> exercises = new ArrayList<>();
            Date from = dataManger.convertToDate(txtFrom.getText());
            Date to = dataManger.convertToDate(txtTo.getText());
            if (from == null || to == null) {
                JOptionPane.showMessageDialog(null, "Format of date is wrong",
                        "Error: Date", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(from);
            txtFrom.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." +
                    (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR));
            calendar.setTime(to);
            txtTo.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." +
                    (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR));

            if (from.after(to)) {
                JOptionPane.showMessageDialog(null, "Your last date has to be after " +
                        "your first");
            } else {
                for (JCheckBox cb : cBExercises) {
                    if (cb.isSelected()) {
                        exercises.add(cb.getText());
                    }
                }
                if (rbOnePdf.isSelected()) {
                    generatePdf.generatePdf(from, to, exercises, PdfType.ONE_PDF_FOR_ALL_EXERCISES);
                } else if (rbMorePdfs.isSelected()) {
                    generatePdf.generatePdf(from, to, exercises, PdfType.ONE_PDF_FOR_EACH_EXERCISE);
                }
            }
        });

        btnGeneratePdf.setVisible(true);
        this.frame.add(btnGeneratePdf);
    }

}