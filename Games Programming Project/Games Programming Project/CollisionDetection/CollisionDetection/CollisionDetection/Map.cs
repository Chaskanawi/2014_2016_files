using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CollisionDetection
{
    class Map
    {
        private List<CollisionTiles> collisionTiles = new List<CollisionTiles>();
        //get collision tiles to collide with the player
        public List<CollisionTiles> CollisionTiles
        {
            get { return collisionTiles; }
        }
        //width and height of each tile
        private int width, height;
        //get the width of the tile
        public int Width
        {
            get { return width; }
        }
        //get the height of the tile
        public int Height
        {
            get { return height; }
        }
        public Map() { } //blank constructor

        //generate map
        public void Generate(int[,] map, int size)
        {
            for (int x = 0; x < map.GetLength(1); x++)
                for (int y = 0; y < map.GetLength(0); y++)
                {
                    //determine texture
                    int number = map[y, x];

                    if (number > 0)
                        collisionTiles.Add(new CollisionTiles(number, new Microsoft.Xna.Framework.Rectangle(x * size, y * size, size, size)));
                    //camera variables
                    width = (x + 1) * size;
                    height = (y + 1) * size;
                }
        }

        public void Draw(SpriteBatch spriteBatch)
        {
            foreach (CollisionTiles tile in collisionTiles)
                tile.Draw(spriteBatch);
        }
    }
}


