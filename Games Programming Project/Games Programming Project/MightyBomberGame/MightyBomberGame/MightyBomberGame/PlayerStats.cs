using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MightyBomberGame
{
    
    public class PlayerStats
    {
        protected int score;
        protected int amountOfBombs;
        protected bool hasKick;
        protected int bombPower;
        protected bool MightyTNT;
        protected bool timedBombs;
        protected int roundsWon;
        protected int speedUpgrade;

        public void Initialise()
        {
            score = 0;
            amountOfBombs = 1;
            hasKick = false;
            bombPower = 1;
            MightyTNT = false;
            timedBombs = false;
            roundsWon = 0;
            speedUpgrade = 0;
        }

        
        public void Update()
        {
            //add switch statement for which upgrade
            AddBombs();
        }

        public void AddBombs()
        {
            if (amountOfBombs < 5)
                amountOfBombs++;
            else if (amountOfBombs >= 5)
                amountOfBombs = 5;
        }

        public void WonRound()
        {
            if (roundsWon < 3)
                roundsWon++;
            else if (roundsWon >= 3)
                roundsWon = 3;
                //end game;
                // winner = player etc
        }

        public void UpBombPower()
        {
            if (bombPower < 5)
                bombPower++;
            else if (bombPower >= 5)
                bombPower = 5;
        }

        public void HasMightyTNT()
        {
            MightyTNT = true;
        }

        public void HasTimedBombs()
        { 
            
        }

    }

    public class Upgrade
    {
        private static Upgrade instance;

        public static Upgrade Instance
        {
            get 
            {
                if (instance == null)
                    instance = new Upgrade();
                return instance;
            }
        }
    }
}
