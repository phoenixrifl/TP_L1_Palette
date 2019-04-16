import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class Palette extends JFrame {
	private Vue vue;
	private Modele modele;
	private Controleur c1;
	
	public Palette() {
		this.modele = new Modele();
		this.c1 = new Controleur();
		this.vue = new Vue();
		vue.setTitle("La Palette");
		vue.pack();
		vue.setVisible(true);// permet de rendre visible la fenetre
	}


	class Vue extends JFrame {
		private JPanel panneauChoix, panneauColore;
		private JSlider rouge, vert, bleu, gris;
		private JMenuBar m;
		private JMenu m1, m2; 
		private JRadioButtonMenuItem m1_1, m1_2;
		private JMenuItem m2_1, m2_2;
		private ButtonGroup m2bis;
		private JLabel hexa;
		
		public Vue() {
			this.panneauChoix = new JPanel(); //Creer le panneau de choix
			this.panneauColore = new JPanel();  
			this.hexa = new JLabel(); // creer le label qui permettra de voir la couleur en hexadecimal
			this.hexa.setForeground(Color.WHITE);
			this.hexa.setBackground(Color.BLACK);
			this.hexa.setOpaque(true);
			this.panneauColore.add(hexa);
			this.setLayout(new GridLayout(1, 0)); //creer un Layout de type GridLayout o il y a 1 ligne et 0 colonne
			this.getContentPane().add(panneauChoix).setBackground(Color.YELLOW);//on ajoute au containeur le panneau de choix avec la couleur jaune
			this.getContentPane().add(panneauColore).setBackground(Color.BLACK);
			m = new JMenuBar();   // creation d'une barre de menu 
			m1 = new JMenu("Affichage"); //creation d'un sous menu dans la barre de menu
			m2 = new JMenu("Selection");
			m2_1 = new JMenuItem("Gris50"); //creation d'un sous menu dans le sous menu de la barre de menu
			m2_2 = new JMenuItem("Complementaire");
			m1_1 = new JRadioButtonMenuItem("RVB"); //creation de bouton radio
			m1_2 = new JRadioButtonMenuItem("gris");
			m2bis = new ButtonGroup();
			m2bis.add(m1_1);
			m2bis.add(m1_2);
			m2.add(m2_1).addActionListener(c1); //ajout de l'item au menu avec ecoute si l'utilisateur l'utilise
			m2.add(m2_2).addActionListener(c1);
			m1.add(m1_1).addActionListener(c1);
			m1.add(m1_2).addActionListener(c1);
			m.add(m1);
			m.add(m2);
			this.setJMenuBar(m);
			m1_1.setActionCommand("RVB");
			m2_2.setActionCommand("gris");
			m2_1.setActionCommand("item 50%");
			m2_2.setActionCommand("compl");
			m1_1.setSelected(true);
			this.miseEnCouleur();
			
		}
		
		public void miseEnGris(){  //quand on choisit le bouton radio Gris
			this.panneauChoix.remove(rouge); // on efface les anciens sliders
			this.panneauChoix.remove(bleu);
			this.panneauChoix.remove(vert);
			this.panneauChoix.setLayout(new GridLayout(1, 0));
			int grisV = (modele.getValR()+modele.getValB()+modele.getValV())/3;  //on donne la valeur du gris qui est la moyenne des 3 couleurs princapales
			this.gris = new JSlider(0,0,255,grisV);
			modele.setValR(grisV);
			modele.setValB(grisV);
			modele.setValV(grisV);
			this.gris.setMajorTickSpacing(255);
			this.gris.setMinorTickSpacing(15);
			this.gris.setPaintTicks(true);
			this.gris.setPaintLabels(true);
			this.gris.setOpaque(false);
			this.gris.setBorder(BorderFactory.createTitledBorder("Gris"));
			this.panneauChoix.add(gris);
			this.gris.addChangeListener(c1);
			this.panneauChoix.validate();
			this.miseAJour();
		}
		
		public void miseEnCouleur(){ //Quand on choisit le bonton radio RVB
			this.panneauChoix.removeAll();
			this.rouge = new JSlider(0, 0, 255, modele.getValR()); //creation d'un slider de la couleur rouge qui va de 0 a 255
			this.rouge.setBorder(BorderFactory.createTitledBorder("Rouge"));
			this.vert = new JSlider(0, 0, 255, modele.getValV());
			this.vert.setBorder(BorderFactory.createTitledBorder("Vert"));
			this.bleu = new JSlider(0, 0, 255, modele.getValB());
			this.bleu.setBorder(BorderFactory.createTitledBorder("Bleu"));
			this.panneauChoix.setLayout(new GridLayout(3, 0)); 
			this.panneauChoix.add(rouge); 
			this.panneauChoix.add(vert);
			this.panneauChoix.add(bleu);
			this.rouge.setMajorTickSpacing(255); //permet de graduer le slider
			this.rouge.setMinorTickSpacing(15);
			this.rouge.setPaintTicks(true);
			this.rouge.setPaintLabels(true);
			this.vert.setMajorTickSpacing(255);
			this.vert.setMinorTickSpacing(15);
			this.vert.setPaintTicks(true);
			this.vert.setPaintLabels(true);
			this.bleu.setMajorTickSpacing(255);
			this.bleu.setMinorTickSpacing(15);
			this.bleu.setPaintTicks(true);
			this.bleu.setPaintLabels(true);
			this.rouge.setOpaque(false);
			this.vert.setOpaque(false);
			this.bleu.setOpaque(false);
			this.rouge.addChangeListener(c1); //on regarde si l'utilisateur a fait action sur le slider
			this.vert.addChangeListener(c1);
			this.bleu.addChangeListener(c1);
			this.panneauChoix.validate();
			this.miseAJour();
		}

		public void miseAJour() { // fonction qui permet de changer la couleur
			this.panneauColore.setBackground(new Color(modele.getValR(), modele.getValV(), modele.getValB()));
			
			String rouge = Integer.toString(modele.getValR(), 16); //on convertie en hexadecimal la couleur
			String vert = Integer.toString(modele.getValV(), 16);
			String bleu = Integer.toString(modele.getValB(), 16);
			if(rouge.length()==1)
				rouge = "0"+rouge;
			if(vert.length()==1)
				vert = "0"+vert;
			if(bleu.length()==1)
				bleu = "0"+bleu;
			this.hexa.setText(rouge+vert+bleu); //Change la valeur hexadecimal de la couleur
			
		}
		
		public void miseAJourCurseur(){ //fonction qui permet de mettre  jour les curseurs
			if(this.m1_1.isSelected()){
				vue.rouge.setValue(modele.getValR());
				vue.vert.setValue(modele.getValV());
				vue.bleu.setValue(modele.getValB());
			}
			else{
				vue.gris.setValue((modele.getValR()+modele.getValB()+modele.getValV())/3);
			}
		}
		
	}

	class Controleur implements ChangeListener, ActionListener {//fonction qui permet de dire ce que l'on va faire si l'utilisateur touche un slider 
		public void stateChanged(ChangeEvent event) {	
			JSlider source = (JSlider) event.getSource();
			if(vue.m1_1.isSelected()){
				if (source == vue.rouge)
					modele.setValR(vue.rouge.getValue());
				if (source == vue.vert)
					modele.setValV(vue.vert.getValue());
				if (source == vue.bleu)
					modele.setValB(vue.bleu.getValue());
			}
			else{
				modele.setValR(vue.gris.getValue());
				modele.setValV(vue.gris.getValue());
				modele.setValB(vue.gris.getValue());
			}
			vue.miseAJourCurseur();
			vue.miseAJour();
		}
		
		public void actionPerformed(ActionEvent arg0) { //fonction qui permet de dire ce que l'on va faire si l'utilisateur clique sur un sousmenu
			if(arg0.getActionCommand().equals("item 50%")){
				modele.setValR(127);
				modele.setValV(127);
				modele.setValB(127);
				vue.miseAJour();
				vue.miseAJourCurseur();
			}
			
			else if(arg0.getActionCommand().equals("compl")){
				modele.setValR(255-modele.getValR());
				modele.setValV(255-modele.getValV());
				modele.setValB(255-modele.getValB());
				vue.miseAJour();
				vue.miseAJourCurseur();
			}
			
			else if(arg0.getActionCommand().equals("gris")){
				vue.miseEnGris();
			}
			
			else if(arg0.getActionCommand().equals("RVB")){
				vue.miseEnCouleur();
			}
		}
	}

	class Modele {
		private int valR, valV, valB;

		public int getValR() {
			return valR;
		}

		public void setValR(int valR) {
			this.valR = valR;
		}

		public int getValV() {
			return valV;
		}

		public void setValV(int valV) {
			this.valV = valV;
		}

		public int getValB() {
			return valB;
		}

		public void setValB(int valB) {
			this.valB = valB;
		}

	}

	public static void main(String[] args) {
		new Palette(); 
	}
}
