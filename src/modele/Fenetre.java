package modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.naming.event.NamespaceChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre extends JFrame {
	private JPanel container = new JPanel();
	// Bouton Ville
	String[] tab = {"New York", "Atlanta", "Chicago", "More"};
	private JComboBox combo = new JComboBox(tab);
	private JLabel label = new JLabel("Ville");
	// Bouton Age
	String[] tab2 = {"10", "20", "30", "More"};
	private JComboBox combo2 = new JComboBox(tab2);
	private JLabel label2 = new JLabel("Age");
	
	String[] tab3 = {"male","female"};
	private JComboBox combo3 = new JComboBox(tab3);
	private JLabel label3 = new JLabel("Genre");
	// Bouton Requete
	private JButton bouton = new JButton("Lancer Requete");

	public Fenetre(int nbBouton, String[] names){
		// Base de la Fenetre 
		this.setTitle("View");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());

		// Ajout bouton Ville
		JPanel top = new JPanel();
		top.add(label);
		top.add(combo);
		container.setLayout(new BoxLayout(top, BoxLayout.PAGE_AXIS));
		container.add(top);
		this.setContentPane(container);
		this.setVisible(true);
		
		// Ajout Bouton Age
		JPanel top2 = new JPanel();
		top2.add(label2);
		top2.add(combo2);
		container.setLayout(new BoxLayout(top2, BoxLayout.PAGE_AXIS));
		container.add(top2);
		this.setContentPane(container);
		this.setVisible(true);
		
		//Ajout Bouton genre
		JPanel top3 = new JPanel();
		top3.add(label3);
		top3.add(combo3);
		container.setLayout(new BoxLayout(top3, BoxLayout.PAGE_AXIS));
		container.add(top3 );
		this.setContentPane(container);
		this.setVisible(true);
		
		// Ajouter Bouton Requete
		JPanel panel = new JPanel();
		panel.add(bouton);
		panel.setLayout(new FlowLayout());
		container.add(panel,BoxLayout.X_AXIS );
		this.setContentPane(container);
		this.setVisible(true); 
		
		// Ecoute bouton Ville
	    combo.addItemListener(new ItemState());
	    combo.addActionListener(new ItemAction());
	    combo.setPreferredSize(new Dimension(100, 20));
	    combo.setForeground(Color.blue);
	    
	    // Ecoute Bouton Age
	    combo2.addItemListener(new ItemState());
	    combo2.addActionListener(new ItemAction());
	    combo2.setPreferredSize(new Dimension(100, 20));
	    combo2.setForeground(Color.green);
	    // Ecoute Bouton Requete
	    bouton.addActionListener(new ItemAction2());
	}
	 //Classe interne impl�mentant l'interface ItemListener
	  class ItemState implements ItemListener{
	    public void itemStateChanged(ItemEvent e) {
	      System.out.println("�v�nement d�clench� sur : " + e.getItem());
;	      // TODO Envoyer le résultat vers la view?
	    }               
	  }
	  class ItemAction implements ActionListener{
		 
		    public void actionPerformed(ActionEvent e ) {
		    	JComboBox combo =(JComboBox) e.getSource();
		      System.out.println("ActionListener : action sur " + combo.getSelectedItem()); // Changer combo par le bon bouton qui change
		   // TODO Envoyer le résultat vers la view ici aussi non?
		      	Fenetre.this.toString(combo.getSelectedItem());// Envoi �a � la Vue OU stock le et creer un bouton qui ordonne l'envoi
		    } 
	  }
	  class ItemAction2 implements ActionListener{
			 
		    public void actionPerformed(ActionEvent e ) {
		    	JButton button =(JButton) e.getSource();
		      System.out.println("ActionListener : action sur " + button.getActionCommand()); // Changer combo par le bon bouton qui change
		   // TODO Envoyer le résultat vers la view ici aussi non?
		      	Fenetre.this.toString(button.getActionCommand());// Envoi �a � la Vue OU stock le et creer un bouton qui ordonne l'envoi
		    } 
	  }
	  
	  
	public void toString(Object objet) {
		// TODO Auto-generated method stub
		System.out.println(objet.toString()); 
	}
	public static void main(String[] Args)
    {
		String[] names = {"ville","age"};
        Fenetre fenetre1 = new Fenetre(2, names);
    }

}