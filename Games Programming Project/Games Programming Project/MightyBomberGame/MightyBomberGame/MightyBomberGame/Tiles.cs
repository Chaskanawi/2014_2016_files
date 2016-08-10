using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MightyBomberGame
{
    class Tiles
    {
        protected Texture2D texture;
        private Rectangle rectangle;


        public Rectangle Rectangle
        {
            get { return rectangle; }
            protected set { rectangle = value; }
        }

        private static ContentManager tilecontent;
        public static ContentManager TileContent
        {
            protected get { return tilecontent; }
            set { tilecontent = value; }
        }

        public void Draw(SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(texture, rectangle, Color.White);
        }

    }

    class CollisionTiles : Tiles
    {

        public CollisionTiles(int i, Rectangle newRectangle)
        {
            texture = TileContent.Load<Texture2D>("Gameplay/tile" + i);
            this.Rectangle = newRectangle;
        }
    }
}
