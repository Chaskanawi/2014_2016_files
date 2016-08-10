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
    public class MainMenu : GameScreen
    {
        //KeyboardState keyState;
        SpriteFont font;
        int menuChoice = 0;
        public override void LoadContent(Microsoft.Xna.Framework.Content.ContentManager Content)
        {
            base.LoadContent(Content);
            if (font == null)
                font = content.Load<SpriteFont>("MenuFont");
            if (menuImage == null)
                menuImage = content.Load<Texture2D>("MainMenu/StartMenu");
        }
        public override void UnloadContent()
        {
            base.UnloadContent();
        }

        public override void Update(Microsoft.Xna.Framework.GameTime gameTime)
        {
            inputManager.Update();

            if (inputManager.KeyPressed(Keys.Down) || inputManager.KeyPressed(Keys.S))
                menuChoice = 1;
            else if (inputManager.KeyPressed(Keys.Up) || inputManager.KeyPressed(Keys.W))
                menuChoice = 0;

            if(menuChoice == 0)
                if(inputManager.keyPressed(Keys.Enter) && menuChoice == 0)
                    ScreenManager.Instance.AddScreen(new SetupScreen());
        }
        public override void Draw(Microsoft.Xna.Framework.Graphics.SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(menuImage, pos, Color.White);
            if (menuChoice == 0)
            {
                spriteBatch.DrawString(font, "New Game", new Vector2(760, 620), Color.Red);
                spriteBatch.DrawString(font, "Continue", new Vector2(760, 720), Color.White);
            }
            else if (menuChoice == 1)
            {
                spriteBatch.DrawString(font, "New Game", new Vector2(760, 620), Color.White);
                spriteBatch.DrawString(font, "Continue", new Vector2(760, 720), Color.Red);
            }
        }

    }
}
