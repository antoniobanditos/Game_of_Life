import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.AbstractCollection;
import java.util.HashSet;

import javax.swing.*;


//TODO add buttons "next" and "prev" that will able to change situation on game board 
//to next and previous generation respectively
public class Frame extends JFrame {
	
	private static final long serialVersionUID = 673328914430461208L;
	private final int button_size=19;
	private final int rows;
	private final int columns;
	
	private final String missing_value_error_message = "Podaj wartosc";
	private final String wrong_value_error_message = "Podano niepoprawne parametry";
	
	private HashSet<Integer> revival_rules= new HashSet<Integer>();
	private HashSet<Integer> dying_rules = new HashSet<Integer>();
	private boolean is_work =false;
	JButton[][] game_Buttons_Array;
	private Thread thread;
	
	
	//GUI variables                   
    private JPanel game_board_Panel;
    private JPanel control_Panel;
    private JLabel ozywa_Label;
    private JTextField revival_rules_TextField;
    private JButton start_Button;
    private JButton stop_Button;
    private JLabel umiera_Label;
    private JTextField dying_rules_TextField;
    private JButton wprowadz_Button;
    private JButton wyczysc_Button;
	
	
public Frame(int rows,int columns) {
        this.rows=rows;
        this.columns=columns;
        game_Buttons_Array=new JButton[rows][columns];
        
        initComponents();
        
    }
                         
    private void initComponents() {
        control_Panel =new JPanel();
        wprowadz_Button = new JButton();
        ozywa_Label = new JLabel();
        umiera_Label = new JLabel();
        revival_rules_TextField = new JTextField();
        dying_rules_TextField = new JTextField();
        wyczysc_Button = new JButton();
        start_Button = new JButton();
        stop_Button = new JButton();
        game_board_Panel =new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        wprowadz_Button.setFont(new Font("Tahoma", 1, 15)); // NOI18N
        wprowadz_Button.setText("Wprowadz regoly");
        wprowadz_Button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                wprowadz_ButtonMousePressed();
            }
        });
        wprowadz_Button.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                wprowadz_ButtonKeyPressed(evt);
            }
        });

        ozywa_Label.setFont(new Font("Yu Gothic UI", 1, 24)); // NOI18N
        ozywa_Label.setText("Ozywa:");

        umiera_Label.setFont(new Font("Yu Gothic UI", 1, 24)); // NOI18N
        umiera_Label.setText("Umiera:");

        revival_rules_TextField.setFont(new Font("Calisto MT", 1, 24)); // NOI18N
        revival_rules_TextField.addFocusListener(new FocusListener() {
        	
        	@Override
			public void focusLost(FocusEvent e) {
			}
			@Override
			public void focusGained(FocusEvent e) {
				String entered_text = revival_rules_TextField.getText();
				if(entered_text.equals(wrong_value_error_message)||entered_text.equals(missing_value_error_message)) {
					revival_rules_TextField.setText("");
				}
			}
		});
        revival_rules_TextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                wprowadz_ButtonKeyPressed(evt);
            }
        });

        dying_rules_TextField.setFont(new Font("Calisto MT", 1, 24)); // NOI18N
        dying_rules_TextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}
			@Override
			public void focusGained(FocusEvent e) {
				String entered_text=dying_rules_TextField.getText();
				if(entered_text.equals(wrong_value_error_message)||entered_text.equals(missing_value_error_message)) {
					dying_rules_TextField.setText("");
				}
			}
		});
        dying_rules_TextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                wprowadz_ButtonKeyPressed(evt);
            }
        });

        wyczysc_Button.setFont(new Font("Tahoma", 1, 15)); // NOI18N
        wyczysc_Button.setText("Wyczysc");
        wyczysc_Button.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		for(int i=0;i<game_Buttons_Array.length;i++) {
        			for(int j =0; j < game_Buttons_Array[i].length;j++) {
        				game_Buttons_Array[i][j].setBackground(Color.BLACK);
        			}
        		}
        	}
		});

        start_Button.setText("Start");
        start_Button.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		start_ButtonMousePressed();
        	}
		});

        stop_Button.setText("Stop");
        stop_Button.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		if(is_work) {
        			thread.interrupt();
        			is_work=false;
        		}
        	}
		});
        
        GroupLayout control_PanelLayout = new GroupLayout(control_Panel);
        control_Panel.setLayout(control_PanelLayout);
        control_PanelLayout.setHorizontalGroup(
            control_PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(control_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(control_PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(control_PanelLayout.createSequentialGroup()
                        .addComponent(ozywa_Label, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(revival_rules_TextField, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
                    .addGroup(control_PanelLayout.createSequentialGroup()
                        .addComponent(umiera_Label, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dying_rules_TextField, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(wprowadz_Button, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(wyczysc_Button, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(control_PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(start_Button, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(stop_Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        control_PanelLayout.setVerticalGroup(
            control_PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(control_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(control_PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(control_PanelLayout.createSequentialGroup()
                        .addComponent(start_Button, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stop_Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(wyczysc_Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, control_PanelLayout.createSequentialGroup()
                        .addGroup(control_PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(ozywa_Label, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                            .addComponent(revival_rules_TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(control_PanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(umiera_Label)
                            .addComponent(dying_rules_TextField, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(wprowadz_Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );	
        
        getContentPane().add(control_Panel, BorderLayout.PAGE_START);
        pack();
        
        game_board_Panel.setBackground(Color.RED);
        
//        TODO adequate window size settings
        if(columns*button_size<getSize().getWidth()) {
        	game_board_Panel.setPreferredSize(new Dimension((int)getSize().getWidth(), (int)getSize().getWidth()));
        	game_board_Panel.setMaximumSize(new Dimension((int)getSize().getWidth(), (int)getSize().getWidth()));
        }else
        game_board_Panel.setPreferredSize(new Dimension(columns*button_size, rows*button_size-190));
        
        for(int i=0;i<rows;i++) {
        	for(int j=0; j<columns;j++) {
        		
        		JButton game_Button=new JButton("");
        		game_Buttons_Array[i][j]=game_Button;
        				game_Button.setBackground(Color.BLACK);
        				game_Button.addMouseListener(new MouseAdapter() {
        		            public void mousePressed(MouseEvent evt) {
        		            	game_ButtonMousePressed(evt);
        		            }
        		        });
        		game_board_Panel.add(game_Button);
        	}
        }
        
        game_board_Panel.setLayout(new GridLayout(rows,columns));
        
        
        getContentPane().add(game_board_Panel, BorderLayout.CENTER);
        pack();
        setResizable(true);
    }                   

    
    private void wprowadz_ButtonMousePressed() {
    	revival_rules.clear();
    	dying_rules.clear();
    	String revival_rules_text=revival_rules_TextField.getText();
    	String dying_rules_text=dying_rules_TextField.getText();
    	if(revival_rules_text.equals("")){
    		revival_rules_TextField.setText(missing_value_error_message);
    		return;
    	}else if(dying_rules_TextField.getText().equals("")) {
    		dying_rules_TextField.setText(missing_value_error_message);
    		return;
    	}
		if(revival_rules_text.indexOf(',')!=-1) {//if in given text exist "," and theoretically "-"
			String [] splited_revival_rules= revival_rules_text.split(",");
			
			for(int i = 0; i<splited_revival_rules.length;i++) {
				if(splited_revival_rules[i].indexOf('-')!=-1) {
					String rangeValues[]=splited_revival_rules[i].split("-");
					if(addRange(revival_rules, rangeValues[0], rangeValues[1])!=1) {
						revival_rules.clear();
						revival_rules_TextField.setText(wrong_value_error_message);
						return;
					}
				}
				else {
					if(addValue(revival_rules, splited_revival_rules[i])!=1) {
						revival_rules.clear();
						revival_rules_TextField.setText(wrong_value_error_message);
						return;
					}
				}
			}
		}
		else if(revival_rules_text.indexOf('-')!=-1) {//if in given text exist only "-"
			String rangeValues[]=revival_rules_text.split("-");
			if(addRange(revival_rules, rangeValues[0], rangeValues[1])!=1) {
				revival_rules.clear();
				revival_rules_TextField.setText(wrong_value_error_message);
				return;
			}
		}
		else {//if in given text are no special symbols
			if(addValue(revival_rules, revival_rules_text)!=1){
				revival_rules.clear();
				revival_rules_TextField.setText(wrong_value_error_message);
				return;
			}
		}
		
		
		///same as before but for dying rules
		if(dying_rules_text.indexOf(',')!=-1) {//if "," and probably "-"
			String [] splited_dying_rules= dying_rules_text.split(",");
			for(int i = 0; i<splited_dying_rules.length;i++) {
				if(splited_dying_rules[i].indexOf('-')!=-1) {
					String rangeValues[]= splited_dying_rules[i].split("-");
					if(addRange(dying_rules, rangeValues[0], rangeValues[1])!=1) {
						dying_rules.clear();
						revival_rules.clear();
						dying_rules_TextField.setText(wrong_value_error_message);
						return;
					}
				}
				else {
					if(addValue(dying_rules, splited_dying_rules[i])!=1) {
						revival_rules.clear();
						dying_rules.clear();
						dying_rules_TextField.setText(wrong_value_error_message);
						return;
					}
				}
			}
		}
		else if(dying_rules_text.indexOf('-')!=-1){// if only "-"
			String rangeValues[]=dying_rules_text.split("-");
			if(addRange(dying_rules, rangeValues[0], rangeValues[1])!=1) {
				revival_rules.clear();
				dying_rules.clear();
				dying_rules_TextField.setText(wrong_value_error_message);
				return;
			}
		}
		else {//if no special characters
			if(addValue(dying_rules, dying_rules_text)!=1){
				revival_rules.clear();
				dying_rules.clear();
				dying_rules_TextField.setText(wrong_value_error_message);
				return;
			}
		}
    }                                     

    
    private void wprowadz_ButtonKeyPressed(KeyEvent evt) {
    	if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
    		wprowadz_ButtonMousePressed();
    	}
    }
    private void start_ButtonMousePressed(){
    	if(is_work==true) {
			return;
		}
		
		thread=new Thread(new Runnable() {
			@Override
			public void run() {
				while(is_work) {
					goToNextDay();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {	}
				}
			}
		});
		is_work=true;
		thread.start();
    }
    
    private void game_ButtonMousePressed(MouseEvent evt) {  
    	if(evt.getComponent().getBackground().equals(Color.BLACK)) {
    		evt.getComponent().setBackground(Color.WHITE);
    	}
    	else {
    		evt.getComponent().setBackground(Color.BLACK);
    	}	
    	
    }  
    
    public void goToNextDay() {
    	boolean buttons_state_Array[][]=getButtonsStateArray();
    	
    	for (int rows_counter = 0; rows_counter < rows; rows_counter++) {
    		for (int columns_counter = 0; columns_counter < columns; columns_counter++) {
				int alive_neighbors_counter = 0;
				
				int prev_row;
				if(rows_counter==0) {
					prev_row=rows-1;
				}else{
					prev_row=rows_counter-1;
				}
				
				int next_row;
				if(rows_counter==rows-1) {
					next_row=0;
				}else {
					next_row=rows_counter+1;
				}
				
				int start_point;
				if(columns_counter==0) {
					start_point=columns-1;
				}else {
					start_point=columns_counter-1;
				}
				
				int end_point;
				if(columns_counter+1==columns) {
					end_point=0;
				}else {
					end_point=columns_counter+1;
				}
				System.out.println("prev_row:"+prev_row+";\tnext_row:"+next_row+";\tstart_point:"+start_point+";\tend_point:"+end_point);
				for(int x=start_point;x!=end_point+1;x++) {
					if(x==columns) {
						x=-1;
						continue;
					}
					if(buttons_state_Array[prev_row][x]) {
						alive_neighbors_counter++;
					}
					if(buttons_state_Array[next_row][x]) {
						alive_neighbors_counter++;
					}
				}
				if(buttons_state_Array[rows_counter][start_point]) {
					alive_neighbors_counter++;
				}
				if(buttons_state_Array[rows_counter][end_point]) {
					alive_neighbors_counter++;
				}
				
				if(revival_rules.contains(alive_neighbors_counter)) {
					game_Buttons_Array[rows_counter][columns_counter].setBackground(Color.WHITE);
				}
				else if(dying_rules.contains(alive_neighbors_counter)) {
					game_Buttons_Array[rows_counter][columns_counter].setBackground(Color.BLACK);
				}
			}
    	}
    }

    

    /**
     * @param list collection where values should be added
     * @param startValue first value of range
     * @param endValue last value of range
     * @return -1 if start or end value is higher 8 or start value is smaller 0<br>
     * 			0 if end value is smaller start value<br>
     * 			1 if method completed successfully and values were added to list
     */
    private int addRange(AbstractCollection<Integer> list, String startValue, String endValue) {
    	int start = new Integer(startValue);
    	int end = new Integer(endValue);
    	if(start>8||end>8||start<0) {
    		return -1;
    	}else if(start>end) {
    		return 0;
    	}
    	++end;
    	for(;start<end;start++) {
    		list.add(start);
    	}    	
    	return 1;
    }
    /**
     * @param list collection where value should be added
     * @param value value that should be added to rules list
     * @return -1 if given value is smaller 0 or higher 8<br>
     * 			1  if method completed successfully and value was added to list
     */
    private int addValue(AbstractCollection<Integer> list, String value) {
    	int int_value=new Integer(value);
		if(int_value>8||int_value<0) {
			return -1;
		}else {
			list.add(int_value);
			return 1;
		}
    }
    private boolean[][] getButtonsStateArray(){
    	boolean buttons_state_Array[][]=new boolean[rows][columns];
    	for (int i=0;i<rows;i++) {
			for (int j =0;j<columns;j++) {
				if(game_Buttons_Array[i][j].getBackground().equals(Color.WHITE)) {
					buttons_state_Array[i][j]=true;
				}else {
					buttons_state_Array[i][j]=false;
				}
			}
		}
    	return buttons_state_Array;
    }
}
