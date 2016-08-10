using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MightyBomberGame
{
    public static class CollisionDetection
    {
        public static bool PersonHit;


        public static bool CollisionDetected(this Rectangle PlayerRectangle, Rectangle TileRectangle)
        {
            if (PlayerRectangle.Intersects(TileRectangle))
            {
                PersonHit = true;
                return true;
            }
            else
            {
                PersonHit = false;
                return PersonHit;
            }
            
        }

       

    }
}
