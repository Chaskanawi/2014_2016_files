using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;

namespace MightyBomber.Managers
{
    class Content_Manager
    {
        private static Content_Manager instance;
        ContentManager CM;

        public Dictionary<string, Texture2D> Textures;

        private Content_Manager()
        {
            Textures = new Dictionary<string, Texture2D>();
        }

        public static Content_Manager getInstance()
        {
            if (instance == null)
                instance = new Content_Manager();
            return instance;
        }

        public void LoadTextures(ContentManager Content)
        {
            CM = Content;
            addTexture("MightyBomber", "player1");
        }

        public void addTexture(string file, string name = "")
        {
            Texture2D newTexture = CM.Load<Texture2D>(file);
            if (name == "")
                Textures.Add(file, newTexture);
            else
                Textures.Add(name, newTexture);
        }
    }
}
