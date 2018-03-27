import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.naming.event.NamespaceChangeListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.omg.Messaging.SyncScopeHelper;



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
	
	
	public Fenetre(int nbBouton, String[] names){
		// Base de la Fenetre 
		this.setTitle("View");
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		/* Debut version dynamique
		for(int i=0;i<nbBouton;i++) {
			
			combos.get(i).add(new JComboBox(names.));// Reparer tab2 par tab dynamique
		}
		for(JComboBox combo : combos) {
			combo.setPreferredSize(new Dimension(100, 20));
		}
		*/
		// Ajout bouton Ville
		JPanel top = new JPanel();
		top.add(label);
		top.add(combo);
		container.add(top, BorderLayout.NORTH);
		this.setContentPane(container);
		this.setVisible(true);
		
		// Ajouton Bouton Age
		JPanel top2 = new JPanel();
		top2.add(label2);
		top2.add(combo2);
		container.add(top2, BorderLayout.CENTER);
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
	}
	 //Classe interne implémentant l'interface ItemListener
	  class ItemState implements ItemListener{
	    public void itemStateChanged(ItemEvent e) {
	      System.out.println("événement déclenché sur : " + e.getItem());
;	      // TODO Envoyer le resultat vers la view?
	    }               
	  }
	  class ItemAction implements ActionListener{
		    public void actionPerformed(ActionEvent e) {
		      System.out.println("ActionListener : action sur " + combo.getSelectedItem());
		   // TODO Envoyer le resultat vers la view ivi aussi non?
		      	Fenetre.this.toString(combo.getSelectedItem());// Envoi ça à la Vue OU stock le et creer un bouton qui ordonne l'envoi
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