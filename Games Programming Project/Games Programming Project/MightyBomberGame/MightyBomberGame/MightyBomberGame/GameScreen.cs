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
    public class GameScreen //base class for all screens.
    {
        protected ContentManager content;
        protected Vector2 pos = new Vector2(0, 0);
        protected Texture2D image;
        protected Texture2D image2;
        protected Texture2D setup;
        protected InputManager inputManager;
        protected Texture2D menuImage;
        protected Texture2D gamePlayFloor;
        protected Texture2D floor;
        protected Texture2D texture;
        protected SpriteFont score;
        //Map map;



        public virtual void Initialize()
        {
            //map = new Map();
        }

        public virtual void LoadContent(ContentManager Content)
        {
            content = new ContentManager(Content.ServiceProvider, "Content");
            image = content.Load<Texture2D>("SplashScreen/Splash2");
            image2 = content.Load<Texture2D>("SplashScreen/Splash1");
            menuImage = content.Load<Texture2D>("MainMenu/StartMenu");
            setup = content.Load<Texture2D>("SetupScrn/SetupScreen");
            floor = content.Load<Texture2D>("Gameplay/floor");
            score = content.Load<SpriteFont>("Gameplay/ScoreFont");
            //gamePlayFloor = content.Load<Texture2D>("background");

            inputManager = new InputManager();
        }

        public virtual void UnloadContent()
        {
            //unload each instance of content manager
            content.Unload();
            inputManager = null;
        }

        public virtual void Update(GameTime gameTime) 
        {

        }

        public virtual void Draw(SpriteBatch spriteBatch)
        {
        }
    }
}
