using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
namespace MightyBomberGame
{
    public class SetupScreen : GameScreen
    {
        #region spritefonts
        SpriteFont font;
        SpriteFont menu1;
        SpriteFont menu2;
        SpriteFont menu3;
        SpriteFont menu4;
        SpriteFont menu5;
        #endregion

        #region private variables
        private int menuchoice = 1;
        private int optionchoice1 = 1;
        private int optionchoice2 = 1;
        private int optionchoice3 = 1;
        private int optionchoice4 = 1;
        private int optionchoice5 = 1;
        private int playerChoice = 1;
        private int roundChoice = 1;
        private int timeChoice = 1;
        private int mapChoice = 1;
        private int gameChoice = 1;
        private bool up;
        private bool down;
        private bool selectLeft;
        private bool selectRight;
        SpriteBatch spriteBatch;
        #endregion

        public override void LoadContent(Microsoft.Xna.Framework.Content.ContentManager Content)
        {
            base.LoadContent(Content);
            #region load font
            font = content.Load<SpriteFont>("Font1");
            menu1 = content.Load<SpriteFont>("SetupScrn/menu1");
            menu2 = content.Load<SpriteFont>("SetupScrn/menu2");
            menu3 = content.Load<SpriteFont>("SetupScrn/menu3");
            menu4 = content.Load<SpriteFont>("SetupScrn/menu4");
            menu5 = content.Load<SpriteFont>("SetupScrn/menu5");
            #endregion
        }

        public override void UnloadContent()
        {
            base.UnloadContent();
            setup = null; //de-allocate setup screen background
            #region unload font
            font = null;
            menu1 = menu2 = menu3 = menu4 = menu5 = null;
            #endregion
        }

        public override void Update(Microsoft.Xna.Framework.GameTime gameTime)
        {
            up = down = selectLeft = selectRight = false;
            inputManager.Update();
            
            #region menu and option select
            if (inputManager.keyPressed(Keys.Up))
                up = true;
            else if (inputManager.keyPressed(Keys.Down))
                down = true;
            else if (inputManager.keyPressed(Keys.Left))
                selectLeft = true;
            else if (inputManager.keyPressed(Keys.Right))
                selectRight = true;
            else if (inputManager.keyPressed(Keys.Enter))
                ScreenManager.Instance.AddScreen(new MainGameplay());
            menu(up, down);

            #endregion 

            #region menu choice statements
            switch (menuchoice)
            {
                case 1:
                    Players();
                    break;
                case 2:
                    RoundTime();
                    break;
                case 3:
                    MapSize();
                    break;
                case 4:
                    Rounds();
                    break;
                case 5:
                    GameType();
                    break;
            }
            #endregion
        }

        public override void Draw(Microsoft.Xna.Framework.Graphics.SpriteBatch spriteBatch)
        {
            //draw menu screen background
            spriteBatch.Draw(setup, pos, Color.White);

            #region default draw menu
            if(playerChoice == 1)
                spriteBatch.DrawString(menu1, "1", new Vector2(860, 270), Color.Red);
            if (playerChoice == 2)
                spriteBatch.DrawString(menu1, "2", new Vector2(860, 270), Color.Red);
            if (playerChoice == 3)
                spriteBatch.DrawString(menu1, "3", new Vector2(860, 270), Color.Red);
            if (playerChoice == 4)
                spriteBatch.DrawString(menu1, "4", new Vector2(860, 270), Color.Red);
            
            
            if(timeChoice == 1)
                spriteBatch.DrawString(menu2, "1 Minute", new Vector2(860, 390), Color.Red);
            if (timeChoice == 2)
                spriteBatch.DrawString(menu2, "2 Minutes", new Vector2(860, 390), Color.Red);
            if (timeChoice == 3)
                spriteBatch.DrawString(menu2, "3 Minutes", new Vector2(860, 390), Color.Red);

            if(mapChoice == 1)
                spriteBatch.DrawString(menu3, "Small", new Vector2(860, 500), Color.Red);
            if(mapChoice == 2)
                spriteBatch.DrawString(menu3, "Large", new Vector2(860, 500), Color.Red);

            if(roundChoice == 1)
                spriteBatch.DrawString(menu4, "1", new Vector2(860, 620), Color.Red);

            if(roundChoice == 2)
                spriteBatch.DrawString(menu4, "2", new Vector2(860, 620), Color.Red);
            
            if(roundChoice == 3)
                spriteBatch.DrawString(menu4, "3", new Vector2(860, 620), Color.Red);
           
            if(roundChoice == 4)
                spriteBatch.DrawString(menu4, "4", new Vector2(860, 620), Color.Red);
            if(roundChoice == 5)
                spriteBatch.DrawString(menu4, "5", new Vector2(860, 620), Color.Red);


            if(gameChoice == 1)
                spriteBatch.DrawString(menu5, "Free For All", new Vector2(860, 730), Color.Red);
            if(gameChoice == 2)
                spriteBatch.DrawString(menu5, "Teams", new Vector2(860, 730), Color.Red);
            //spriteBatch.DrawString(menu2, "1 Minute", new Vector2(860, 390), Color.White);
            //spriteBatch.DrawString(menu3, "Small", new Vector2(860, 500), Color.White);
            //spriteBatch.DrawString(menu4, "1", new Vector2(860, 620), Color.White);
            //spriteBatch.DrawString(menu5, "Free For All", new Vector2(860, 730), Color.White);
            #endregion
        }

        public void menu(bool up, bool down)
        {
            #region up and down choices
            if (up && menuchoice > 1)
                menuchoice--;
            if (down && menuchoice < 5)
                menuchoice++;
            #endregion
            
        }

        public void Players()
        {
            if (selectLeft && optionchoice1 > 1)
                optionchoice1--;
            if (selectRight && optionchoice1 < 4)
                optionchoice1++;

            switch (optionchoice1)
            {
                case 1:
                    playerChoice = 1;
                    break;
                case 2:
                    playerChoice = 2;
                    break;
                case 3:
                    playerChoice = 3;
                    break;
                case 4:
                    playerChoice = 4;
                    break;
            }
        }

        public void RoundTime()
        {
            if (selectLeft && optionchoice2 > 1)
                optionchoice2--;
            if (selectRight && optionchoice2 < 3)
                optionchoice2++;

            switch (optionchoice2)
            { 
                case 1:
                    timeChoice = 1;
                    break;
                case 2:
                    timeChoice = 2;
                    break;
                case 3:
                    timeChoice = 3;
                    break;
            }
        }

        public void MapSize()
        {
            if (selectLeft && optionchoice3 > 1)
                optionchoice3--;
            if (selectRight && optionchoice3 < 2)
                optionchoice3++;

            switch (optionchoice3)
            { 
                case 1:
                    mapChoice = 1;
                    break;
                case 2:
                    mapChoice = 2;
                    break;
            }
        }

        public void Rounds()
        {
            if (selectLeft && optionchoice4 > 1 )
                optionchoice4--;
            if (selectRight && optionchoice4 < 5)
                optionchoice4++;

            switch (optionchoice4)
            { 
                case 1:
                    roundChoice = 1;
                    break;
                case 2:
                    roundChoice = 2;
                    break;
                case 3:
                    roundChoice = 3;
                    break;
                case 4:
                    roundChoice = 4;
                    break;
                case 5:
                    roundChoice = 5;
                    break;
            }
        }

        public void GameType()
        {
            if (selectLeft && optionchoice5 > 1)
                optionchoice5--;
            if (selectRight && optionchoice5 < 2)
                optionchoice5++;

            switch (optionchoice5)
            { 
                case 1:
                    gameChoice = 1;
                    break;
                case 2:
                    gameChoice = 2;
                    break;
            }
        }
    }
}
