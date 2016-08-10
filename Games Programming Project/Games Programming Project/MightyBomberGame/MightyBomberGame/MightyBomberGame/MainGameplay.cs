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
    public class MainGameplay : GameScreen
    {
        Map map;
        Player player;
        public SpriteBatch spritebatch;
        public bool hit;

        public override void LoadContent(ContentManager Content)
        {
            base.LoadContent(Content);
            map = new Map();
            
            if (floor == null)
                floor = content.Load<Texture2D>("Gameplay/floor");
            if (score == null)
                score = content.Load<SpriteFont>("Gameplay/ScoreFont");
            player = new Player();
            Tiles.TileContent = content;
            

            map.Generate(new int[,]{
                {4,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,4,},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
                {1,0,2,0,2,0,2,0,2,5,2,0,2,0,2,0,2,0,2,0,2,0,1,},
                {1,0,0,0,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,1,},
                {1,5,2,0,2,5,2,5,2,0,2,0,2,0,2,0,2,0,2,0,2,0,1,},
                {1,5,5,5,0,0,0,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,1,},
                {1,5,2,5,2,5,2,5,2,0,2,0,2,0,2,0,2,0,2,0,2,0,1,},
                {1,5,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
                {1,5,2,0,2,0,2,5,2,0,2,0,2,0,2,0,2,0,2,0,2,0,1,},
                {1,5,5,5,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
                {1,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,1,},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
                {1,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,1,},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
                {1,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,1,},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
                {4,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,4,},
            }, 64);
            Player.PlayerContent = content;
            player.Load(content);
        }

        public override void UnloadContent()
        {
            base.UnloadContent();
        }

        public override void Update(GameTime gameTime)
        {
            hit = false;
            player.Update(gameTime);
            base.Update(gameTime);
        }

        public override void Draw(SpriteBatch spriteBatch)
        {
            //spriteBatch.DrawString(font, "SplashScreen", new Vector2(100, 100), Color.Black);
            spriteBatch.Draw(floor, pos, Color.White);
            spriteBatch.DrawString(score, "Player 1 Score:", new Vector2(1500,50), Color.Red);
            map.Draw(spriteBatch);
            player.Draw(spriteBatch);
        }


      
    }
}
