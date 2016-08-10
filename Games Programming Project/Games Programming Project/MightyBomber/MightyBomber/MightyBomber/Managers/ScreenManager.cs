using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections; //collections for storing lists
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

using MightyBomber.Screens;


namespace MightyBomber.Managers
{
    class ScreenManager
    {
        ArrayList Screens;
        Screen CurrentScreen;

        public ScreenManager()
        {
            Screens = new ArrayList();
            Screens.Add(new TempScreen());
            CurrentScreen = (Screen)Screens[0]; //type cast array position as Screen
        }

        public void Draw(SpriteBatch spriteBatch)
        {
            CurrentScreen.Draw(spriteBatch);
        }
    }
}
