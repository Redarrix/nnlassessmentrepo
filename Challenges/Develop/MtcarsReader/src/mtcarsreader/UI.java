package mtcarsreader;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class UI extends JFrame {
    
    private final int SCREEN_INITIAL_WIDTH = 1080;
    private final int SCREEN_INITIAL_HEIGHT = 600;
    private final int EDIT_WIDTH = 340;
    
    private final JPanel csvdisplay, displayPanel, editPanel;
    private final JTextField editInput;
    private final JComboBox columnCombo, rowCombo;
    private final JButton editButton, saveButton;
    private final JTable csvtable;
    private final JScrollPane csvscroll;
    
    String csv = "mtcars.csv";
    String line = "";
    int a = 0;
    int tablecol = 12;
    int tablerow = 32;
    
    public String[][] csvarray;
    public String[] csvheader;
    public Integer[] rowarray;
    
    BufferedReader csvreader;
    BufferedWriter csvwriter;

    class editListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
            String column = "";
            int row = 0;
            String edit = "";
            int rownum = 0;
            int colnum = 0;
            try{
                column = (String)columnCombo.getSelectedItem();
                switch (column){
                    case "model":
                        colnum = 0;
                    break;
                    case "mpg":
                        colnum = 1;
                    break;
                    case "cyl":
                        colnum = 2;
                    break;
                    case "disp":
                        colnum = 3;
                    break;
                    case "hp":
                        colnum = 4;
                    break;
                    case "drat":
                        colnum = 5;
                    break;
                    case "wt":
                        colnum = 6;
                    break;
                    case "qsec":
                        colnum = 7;
                    break;
                    case "vs":
                        colnum = 8;
                    break;
                    case "am":
                        colnum = 9;
                    break;
                    case "gear":
                        colnum = 10;
                    break;
                    case "carb":
                        colnum = 11;
                    break;
                }
                row = (Integer)rowCombo.getSelectedItem();
                rownum = row-1;
                edit = editInput.getText();
                System.out.println("Done! Saved column " + column+" ("+colnum+") row "+row+" ("+rownum+") as '"+edit+"'");
                csvarray[rownum][colnum] = edit;
            }
            catch(NullPointerException e){
                System.out.println("At least 1 field is empty");
            }
        }
        
    }
    
    class saveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                StringBuilder csvsave = new StringBuilder();
                for(int i = 0; i < tablecol; i++){
                    csvsave.append(csvheader[i]);
                    if(i < tablecol-1){
                        csvsave.append(",");
                    }
                }
                csvsave.append("\n");
                for(int i = 0; i < tablerow; i++){
                    for(int j = 0; j < tablecol; j++){
                        csvsave.append(csvarray[i][j]);
                        if(j < tablecol-1){
                            csvsave.append(",");
                        }
                    }
                    csvsave.append("\n");
                }
                System.out.println(csvsave);
                csvwriter = new BufferedWriter(new FileWriter(csv));
                csvwriter.write(csvsave.toString());
                csvwriter.close();
                System.out.println("Edits Saved!");
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public UI() {
        
        csvarray = new String[tablerow][tablecol];
        csvheader = new String[12];
        
        rowarray = new Integer[32];
        for (int i=1; i<33; i++){
            rowarray[i-1] = i;
        }
        
        try {
            csvreader = new BufferedReader(new FileReader(csv));
            
            while((line = csvreader.readLine()) != null ){
                String[] input = line.split(",");
                if (a < 1){
                    for (int i=0; i < 12; i++){
                    csvheader[i] = input[i];
                    }
                } else {
                for (int i=0; i < 12; i++){
                    csvarray[a-1][i] = input[i];
                }
                }
                a++;
            }
            csvreader.close();
                    
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    csvdisplay = new JPanel();
    csvdisplay.setBorder(new TitledBorder(new EtchedBorder(), "Mtcars Reader"));
    csvdisplay.setPreferredSize(new Dimension(SCREEN_INITIAL_WIDTH, SCREEN_INITIAL_HEIGHT));
    csvdisplay.setLayout(new FlowLayout());
    add(csvdisplay);
    
    displayPanel = new JPanel();
    displayPanel.setBorder(new TitledBorder(new EtchedBorder(), "Display CSV"));
    displayPanel.setPreferredSize(new Dimension(700, SCREEN_INITIAL_HEIGHT-50));
    csvdisplay.add(displayPanel);
    
    csvtable = new JTable(csvarray,csvheader);
    csvscroll = new JScrollPane(csvtable);
    csvscroll.setPreferredSize(new Dimension(680, SCREEN_INITIAL_HEIGHT-100));
    displayPanel.add(csvscroll);
        
    editPanel = new JPanel();
    editPanel.setBorder(new TitledBorder(new EtchedBorder(), "Edit CSV"));
    editPanel.setPreferredSize(new Dimension(EDIT_WIDTH, SCREEN_INITIAL_HEIGHT-50));
    csvdisplay.add(editPanel);
    
    columnCombo = new JComboBox(csvheader);
    columnCombo.setSelectedIndex(0);
    editPanel.add(columnCombo);
        
    rowCombo = new JComboBox(rowarray);
    rowCombo.setSelectedIndex(0);
    editPanel.add(rowCombo);
        
    editInput = new JTextField();
    editInput.setPreferredSize(new Dimension(EDIT_WIDTH - 20, 50));
    editPanel.add(editInput);
    
    editButton = new JButton("Confirm Edit");
    editButton.setPreferredSize(new Dimension(EDIT_WIDTH - 200, 50));
    editPanel.add(editButton);
    editButton.addActionListener(new editListener());
    
    saveButton = new JButton("Save Edit to CSV");
    saveButton.setPreferredSize(new Dimension(EDIT_WIDTH - 200, 50));
    editPanel.add(saveButton);
    saveButton.addActionListener(new saveListener());
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
    
    }
}
