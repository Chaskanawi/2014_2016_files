using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;

namespace xnaProject
{
    public class ScreenManager
    {
        #region Variables
        /// <summary>
        /// Creating a custom content manager.
        /// </summary>
        
        /// <summary>
        /// Current screen stores the loaded screen and new screen loads in the new screen.
        /// </summary>

        GameScreen currentScreen;
        GameScreen newScreen;
        ContentManager content;
        /// <summary>
        /// ScreenManager Instance
        /// </summary>

        private static ScreenManager instance;
       
        /// <summary>
        /// Screen Stack
        /// </summary>
        
        Stack<GameScreen> screenStack = new Stack<GameScreen>();

        /// <summary>
        /// Screens width and height.
        /// </summary>
        /// 
        Vector2 dimentions;

        bool transition;
        FadeAnimation fade;
        Texture2D fadeTexture, nullImage;


        #endregion

        #region Properties
        public static ScreenManager Instance
        {
            get
            {
                if (instance == null)
                    instance = new ScreenManager();
                return instance;
            }
        }

        public Vector2 Dimentions
        {
            get { return dimentions; }
            set { dimentions = value; }
        }

        #endregion

        #region Main Methods

        public void AddScreen(GameScreen screen)
        {
            transition = true;
            newScreen = screen;
            fade.IsActive = true;
            fade.Alpha = 0.0f;
            fade.ActivateValue = 1.0f;
        }
        /// <summary>
        /// Initialize is like a constructor that can be called more than once
        /// </summary>
        public void Initialize() 
        {
            currentScreen = new SplashScreen();
            fade = new FadeAnimation();

        }

        public void LoadContent(ContentManager Content)
        {
            content = new ContentManager(Content.ServiceProvider, "Content");
            currentScreen.LoadContent(Content);

            fadeTexture = content.Load<Texture2D>("Fade");
            fade.LoadContent(content, fadeTexture, "", Vector2.Zero);
            fade.Scale = dimentions.X;
            
        }

        public void Update(GameTime gameTime) 
        {
            if (!transition)
                currentScreen.Update(gameTime);
            else
                Transition(gameTime);
        }
        public void Draw(SpriteBatch spriteBatch) 
        {
            currentScreen.Draw(spriteBatch);
            if (transition)
                fade.Draw(spriteBatch);
        }

        #endregion
   
        #region Private Methods

        private void Transition(GameTime gameTime)
        {
            fade.Update(gameTime);
            if (fade.Alpha == 1.0f && fade.Timer.TotalSeconds == 1.0f)
            {
                screenStack.Push(newScreen);
                currentScreen.UnloadContent();
                currentScreen = newScreen;
                currentScreen.LoadContent(content);
            }
            else if (fade.Alpha == 0.0f)
            {
                transition = false;
                fade.IsActive = false;
            }
        }
        #endregion
    }
}
