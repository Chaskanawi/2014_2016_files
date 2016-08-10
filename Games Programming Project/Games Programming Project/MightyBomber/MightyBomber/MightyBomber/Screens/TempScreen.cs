using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MightyBomber.Managers;

namespace MightyBomber.Screens
{
    class TempScreen : Screen
    {
        public TempScreen()
            : base()
        { 
        }

        public override void Draw(SpriteBatch spriteBatch)
        {
           spriteBatch.Draw(Content_Manager.getInstance().Textures["MightyBomber"], new Vector2 (300,300), Color.Yellow);
        }
    }
}
