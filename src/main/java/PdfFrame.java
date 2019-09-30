import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PdfFrame {
    private JFrame frame = new JFrame();
    private JTextField txtFrom;
    private JTextField txtTo;
    private JRadioButton rbMorePdfs;
    private JRadioButton rbOnePdf;
    private GeneratePdf generatePdf;
    private DataManger dataManger;
    private List<JCheckBox> cBExercises;
    private JButton btnGeneratePdf;

    public PdfFrame(){
        generatePdf = new GeneratePdf();
        dataManger = new DataManger();
        cBExercises = new ArrayList<>();
    }

    public void createFrame(){
        this.createFrame(500,500);
    }
    public void createFrame(int width, int height){
        frame.setSize(500,500);
        frame.getContentPane().setBackground(Color.decode("#ffddc1"));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
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

        frame.setVisible(true);
    }

    private void addPanel(){
        JPanel pnlExercises = new JPanel();
        BoxLayout boxlayout = new BoxLayout(pnlExercises, BoxLayout.Y_AXIS);
        pnlExercises.setLayout(boxlayout);
        pnlExercises.setBackground(Color.white);
        pnlExercises.setVisible(true);

        List<String> exercises = new ArrayList<>();
        exercises = dataManger.getExerciseList();

        for(String exercise : exercises){
            JCheckBox cb = new JCheckBox();
            cb.setText(exercise);
            cb.setBackground(Color.white);
            cb.setSize(150, 30);
            this.cBExercises.add(cb);
           pnlExercises.add(cb);
        }


        JScrollPane sPExercises = new JScrollPane(pnlExercises,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.frame.add(sPExercises);
        sPExercises.setLocation(50,50);
        sPExercises.setSize(150,300);
        sPExercises.setVisible(true);

        sPExercises.getViewport().revalidate();

    }

    private void addToggleButtons(){
        rbMorePdfs = new JRadioButton("More PDFs");
        rbOnePdf = new JRadioButton("One PDF");
        ButtonGroup bgPdf = new ButtonGroup();
        rbMorePdfs.setBounds(50,370, 150, 20);
        rbOnePdf.setBounds(50,390, 150, 20);
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

    private void addTextFields(){
        JPanel pnlGroupFromTo = new JPanel();

        JLabel lblFrom = new JLabel("From: (dd.mm.yyyy)");
        txtFrom = new JTextField();

        JLabel lblTo = new JLabel("To: (dd.mm.yyyy)");
        txtTo = new JTextField();

        pnlGroupFromTo.setLayout(null);

        lblFrom.setBounds(0,0,200, 30);
        txtFrom.setBounds(0,50, 200, 30);

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

    private void addSendButton(){
        this.btnGeneratePdf = new JButton("Generate PDF");
        btnGeneratePdf.setBounds(275, 250, 150, 40);

        btnGeneratePdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date from = dataManger.convertToDate(txtFrom.getText());
                Date to = dataManger.convertToDate(txtTo.getText());
                if(from == null || to == null){
                    JOptionPane.showMessageDialog(null,"Format of date is wrong",
                            "Error: Date", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(from);
                txtFrom.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." +
                        (calendar.get(Calendar.MONTH)+1)+ "." + calendar.get(Calendar.YEAR));
                calendar.setTime(to);
                txtTo.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." +
                        (calendar.get(Calendar.MONTH)+1)+ "." + calendar.get(Calendar.YEAR));

                if(from.after(to)){
                    JOptionPane.showMessageDialog(null, "Your last date has to be after " +
                            "your first");
                }else{
                    for(JCheckBox cb : cBExercises){
                        if(cb.isSelected()){
                            generatePdf.generatePdf(from, to, cb.getText());
                        }

                    }
                }
            }
        });

        btnGeneratePdf.setVisible(true);
        this.frame.add(btnGeneratePdf);
    }

}