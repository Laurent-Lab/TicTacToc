package laurent.benard.tictactoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button      mPionButton0, mPionButton1, mPionButton2, mPionButton3, mPionButton4,
                mPionButton5, mPionButton6, mPionButton7, mPionButton8;
    Button      mBtGamePlayButton;
    ImageView   mImageView;
    TextView    mTextViewTvGameStatus;
    //Création d'un tableau de bouton (pions)
    Button[]    pions = new Button[9];
    //Création d'une variable pour déterminer si le jeu est terminé
    boolean gameIsOver = false;

    int currentTurn = 0;
    //Création d'un tableau de chaines de caractères pour afficher les symboles
    String players[] = {"X", "O"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Appel de la méthode pour initialiser mes vues d'objets
        initialize();
        //Appel de la méthode créant des évènements au click boutons
        bindEventsListenerOnButtons();


    }

    //Création d'une méthode pour récupérer des évènments au click des boutons
    private void bindEventsListenerOnButtons() {
        //Pour chaque pion de mes pions, ajoute un setOnclick qui s'appel pionListener
        for(Button pion : pions){

            pion.setOnClickListener(pionListener);
        }

        //Je crée un evènement au clique sur mon bouton GamePlay
        mBtGamePlayButton.setOnClickListener(replayListener);
    }


    private View.OnClickListener pionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Trouver le bouton sur lequel on a cliqué
            //Donne moi l'ID de la vue sur laquelle l'ut a cliqué
            Button button = findViewById(v.getId());

            //Est ce que le jeu est terminé
            //La variable GameIsOver détermine si le jeu est terminé, par défaut false
            if (gameIsOver){
                // Si le jeu est terminé, return; permet de mettre fin à la boucle
                //On ne fait donc rien
                return;

            }

            //Vérifier si emplacement valide
            //Création d'une méthode pour savoir si l'emplacement button est valide
            // ! , signifie : si ce n'est pas valide
            if (! isValid(button)){

                //Afficher l'animation
                //Afficher emplacement invalide en modifiant le texte par défaut
                mTextViewTvGameStatus.setText("Déplacement invalide !");
                //Sinon, si emplacement disponible
                //Affiche le symbole sur le bouton en question
            }else {
                //J'affiche le symbole qui correspond au joueur en cour
                setSymbol(button, players[currentTurn]);

                gameIsOver = winnerExists();

                //Si le jeu est terminé, message de félicitation au joueur en cours
                if (gameIsOver){
                    mTextViewTvGameStatus.setText("le joueur " + players[currentTurn] + " a gagné !");
                    //Mettre fin au jeu
                    return;
                }
                //Si le tableau est plein
                if (boardIsFull()){
                    mTextViewTvGameStatus.setText("Match nul");
                    //Signifier que le jeu est terminé
                    gameIsOver = true;
                    //Mettre fin au jeu
                    return;
                }

                //Si currenTurn == 0 , changer à 1
                currentTurn = currentTurn ^ 1;
                //On informe le joueur suivant que c'est à son tour
                mTextViewTvGameStatus.setText("Joueur " + players[currentTurn] + " à votre tour");

            }

            //Sinon, on informe le joueur suivant





        }


    };

    private boolean winnerExists() {

        //Si le texte présent à l'emplacement 0 et égal à celui du joueur en cours...
        if (pions[0].getText().toString() == players[currentTurn]
                && pions[1].getText().toString() == players[currentTurn]
                && pions[2].getText().toString() == players[currentTurn]){
            //Appel la méthode pour changer le bouton de couleur
            changeColorOfButton(0,1,2);
            //Alors retourne que le joueur en cours à gagné
            return true;
        }

        if (pions[3].getText().toString() == players[currentTurn]
                && pions[4].getText().toString() == players[currentTurn]
                && pions[5].getText().toString() == players[currentTurn]){

            changeColorOfButton(3,4,5);
            //Alors retourne que le joueur en cours à gagné
            return true;
        }

        if (pions[6].getText().toString() == players[currentTurn]
                && pions[7].getText().toString() == players[currentTurn]
                && pions[8].getText().toString() == players[currentTurn]){

            changeColorOfButton(6,7,8);
            //Alors retourne que le joueur en cours à gagné
            return true;
        }

        if (pions[0].getText().toString() == players[currentTurn]
                && pions[3].getText().toString() == players[currentTurn]
                && pions[6].getText().toString() == players[currentTurn]){

            changeColorOfButton(0,3,6);
            //Alors retourne que le joueur en cours à gagné
            return true;
        }

        if (pions[1].getText().toString() == players[currentTurn]
                && pions[4].getText().toString() == players[currentTurn]
                && pions[7].getText().toString() == players[currentTurn]){

            changeColorOfButton(1,4,7);
            //Alors retourne que le joueur en cours à gagné
            return true;
        }

        if (pions[2].getText().toString() == players[currentTurn]
                && pions[5].getText().toString() == players[currentTurn]
                && pions[8].getText().toString() == players[currentTurn]){

            changeColorOfButton(2,5,8);
            //Alors retourne que le joueur en cours à gagné
            return true;
        }

        if (pions[0].getText().toString() == players[currentTurn]
                && pions[4].getText().toString() == players[currentTurn]
                && pions[8].getText().toString() == players[currentTurn]){

            changeColorOfButton(0,4,8);
            //Alors retourne que le joueur en cours à gagné
            return true;
        }

        if (pions[2].getText().toString() == players[currentTurn]
                && pions[4].getText().toString() == players[currentTurn]
                && pions[6].getText().toString() == players[currentTurn]){

            changeColorOfButton(2,4,6);
            //Alors retourne que le joueur en cours à gagné
            return true;
        }

        //Sinon retourne que le joueur en cours n'a pas gagné
        return false;

    }

    private void changeColorOfButton(int i, int j, int k) {

        //Pour le pion i, changer la couleur du texte
        //argb(opacité, red, green, blue)
        pions[i].setTextColor(Color.argb(255,107, 187, 177 ));
        pions[j].setTextColor(Color.argb(255, 213, 177, 47));
        pions[k].setTextColor(Color.argb(255, 213, 100, 47));
    }

    private boolean boardIsFull() {
        //Pour chaque pion appartenant au tableau pions
        for (Button pion : pions){
            //Si le texte d'un pion est vide
            if (pion.getText().toString().length() == 0){
                //Alors retourne que le tableau n'est pas plein
                return false;
            }
        }

        //Sinon retourne que le tableau est plein
        return true;
    }

    //Méthode pour ajouter un symbole au bouton
    //La méthode prend en paramêtre le bouton et la chaine de caractêres correspondante au symbole
    private void setSymbol(Button button, String players) {

        button.setText(players);

    }

    private boolean isValid(Button button) {
        //Je dois vérifier si le bouton contient du texte
        //toString() me permet de convertir ce que je reçois en chaine de caractere
        //Puis je vérifie si la longueur de ma chaine de caractêres est égale à O
        //Donc mon bouton est valide si la longueur de la chaine de caractères est égale à 0
        return button.getText().toString().length() == 0;
    }

    //Méthode pour relancer le jeu au clique de l'utilisateur
    private View.OnClickListener replayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Pour chaque bouton, vide le te texte
            //Change la couleur en noir
            for(Button pion : pions){
                pion.setText("");
                pion.setTextColor(Color.BLACK);
                //Modifie le texte en remplaçant par le texte présent dans le fichier String
                mTextViewTvGameStatus.setText(R.string.game_status_text);
                currentTurn = 0;
                //Enfin le jeu n'est plus terminé
                gameIsOver = false;

            }
        }
    };

    //Méthode pour initialiser mes différentes vues d'objets
    private void initialize(){

        mPionButton0 =          findViewById(R.id.pionButton0);
        mPionButton1 =          findViewById(R.id.pionButton1);
        mPionButton2 =          findViewById(R.id.pionButton2);
        mPionButton3 =          findViewById(R.id.pionButton3);
        mPionButton4 =          findViewById(R.id.pionButton4);
        mPionButton5 =          findViewById(R.id.pionButton5);
        mPionButton6 =          findViewById(R.id.pionButton6);
        mPionButton7 =          findViewById(R.id.pionButton7);
        mPionButton8 =          findViewById(R.id.pionButton8);
        mBtGamePlayButton =     findViewById(R.id.btGamePlayButton);
        mImageView =            findViewById(R.id.imageView);
        mTextViewTvGameStatus = findViewById(R.id.tvGameStatus);

        //Je référence chaque index du tableau de pions
        pions[0] = mPionButton0;
        pions[1] = mPionButton1;
        pions[2] = mPionButton2;
        pions[3] = mPionButton3;
        pions[4] = mPionButton4;
        pions[5] = mPionButton5;
        pions[6] = mPionButton6;
        pions[7] = mPionButton7;
        pions[8] = mPionButton8;
    }

}
