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
    public class TitleScreen : GameScreen
    {
        KeyboardState keyState;
        SpriteFont font;
        private float counter;
        private int counterEnd = 3;
        //creating custom manager for content


        public override void LoadContent(ContentManager Content)
        {
            base.LoadContent(Content);
            if (font == null)
                font = content.Load<SpriteFont>("Font1");
            else if (image == null)
                image = content.Load<Texture2D>("SplashScreen/Splash2");
        }

        public override void UnloadContent()
        {
            base.UnloadContent();
            image = null;
        }

        public override void Update(GameTime gameTime)
        {
            //inputManager.Update();
            keyState = Keyboard.GetState();
            //if (keyState.IsKeyDown(Keys.Enter))
            //ScreenManager.Instance.AddScreen(new SplashScreen());
            //add main menu here
            counter += (float)gameTime.ElapsedGameTime.TotalSeconds;
            if (counter >= counterEnd || keyState.IsKeyDown(Keys.Escape))
            {
                ScreenManager.Instance.AddScreen(new MainMenu());
            }


        }

        public override void Draw(SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(image, pos, Color.White);
            //spriteBatch.DrawString(font, "TitleScreen", new Vector2(100, 100), Color.Black);
        }
    }
}
