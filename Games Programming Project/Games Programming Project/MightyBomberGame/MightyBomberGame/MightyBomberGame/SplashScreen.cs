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
    public class SplashScreen : GameScreen
    {
        KeyboardState keyState;
        SpriteFont font;
        //creating custom manager for content
        private float counter;
        private int counterEnd = 3;


        public override void LoadContent(ContentManager Content)
        {
            base.LoadContent(Content);
            if (font == null)
                font = content.Load<SpriteFont>("Font1");
        }

        public override void UnloadContent()
        {
            base.UnloadContent();
        }

        public override void Update(GameTime gameTime)
        {
            keyState = Keyboard.GetState();
            //if(keyState.IsKeyDown(Keys.Z))
                //ScreenManager.Instance.AddScreen(new TitleScreen());

            //inputManager.Update();
            //if(inputManager.keyPressed(Keys.Z))
                //ScreenManager.Instance.AddScreen(new TitleScreen());

            counter += (float)gameTime.ElapsedGameTime.TotalSeconds;
            if (counter >= counterEnd || keyState.IsKeyDown(Keys.Escape))
            {
                ScreenManager.Instance.AddScreen(new TitleScreen());
                
            }

        }

        public override void Draw(SpriteBatch spriteBatch)
        {
            //spriteBatch.DrawString(font, "SplashScreen", new Vector2(100, 100), Color.Black);
            spriteBatch.Draw(image2, pos, Color.White);
        }

    }
}
