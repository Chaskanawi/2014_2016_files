using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;

namespace MightyBomberGame
{
    //singleton class global state used to manage screens
    public class ScreenManager
    {
        #region Variables

        GameScreen currentScreen;
        GameScreen newScreen;
        ContentManager content;

        private static ScreenManager instance;

        Stack<GameScreen> screenStack = new Stack<GameScreen>();

        Vector2 dimentions;
        bool transition;
        FadeAnimation fade;
        Texture2D fadeTexure;
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

        public ContentManager Content
        {
            get { return content; }
        }

        public Vector2 Dimentions
        {
            get{ return dimentions;}
            set{ dimentions = value;}
        }
        #endregion

        #region Main Methods

        public void AddScreen(GameScreen screen)
        {
            transition = true;
            newScreen = screen; //Keep to delete screens from the stack in other methods
            fade.IsActive = true; //animation takes place
            fade.Alpha = 0.0f; //transparent
            fade.ActivateValue = 1.0f; //fade complete
        }

        //initalize to be called more than once (acts as constructor
        public void Initalize() 
        {
            currentScreen = new SplashScreen();
            fade = new FadeAnimation();
            
        }

        public void LoadContent(ContentManager Content)
        {
            content = new ContentManager(Content.ServiceProvider, "Content");
            currentScreen.LoadContent(Content);
            fadeTexure = content.Load < Texture2D > ("Fade");
            fade.LoadContent(content, fadeTexure, "", Vector2.Zero);
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

        #region Private 
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
