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
    public class Player : PlayerStats
    {
        //private Texture2D playerTexture;
        public bool personHit;
        //private bool Detected;
        private Vector2 position = new Vector2(192, 256);
        private InputManager inputManager;
        private Rectangle personRectangle;
        private static ContentManager playerContent;
        private string DirectionFacing;
        private bool playerCollides;
        private float movementSpeed = 2;
        private bool PlacedBomb;
        private bool Explosion;
        public bool BlowUp = false;
        private int BombTimerEnd = 4;
        float bombTimer;
        float elapsed;
        float delay = 200f;
        int frames = 0;
        int BombFrames = 0;
        /// <summary>
        /// Animation Textures allocated in a private method for multiplayer.
        /// </summary>
        Texture2D RightWalk;
        Texture2D LeftWalk;
        Texture2D UpWalk;
        Texture2D DownWalk;
        Texture2D Idle;

        //Bomb textures
        Texture2D Bomb1;
        Texture2D Bang;
        //source detination
        Rectangle SourceRect;
        Rectangle BombRectangle;
        Rectangle BombSourceRect;
        public static ContentManager PlayerContent
        {
            protected get { return playerContent; }
            set { playerContent = value; }
        }

        public Vector2 Position
        {
            get { return position; }
        }

        public Player() { }

        public void Load(ContentManager PlayeConent)
        {
            inputManager = new InputManager();
            RightWalk = PlayerContent.Load<Texture2D>("PlayerTextures/RightWalking");
            LeftWalk = playerContent.Load<Texture2D>("PlayerTextures/LeftWalking");
            UpWalk = playerContent.Load<Texture2D>("PlayerTextures/UpWalking");
            DownWalk = playerContent.Load<Texture2D>("PlayerTextures/DownWalking");
            Idle = playerContent.Load<Texture2D>("PlayerTextures/Idle");
            Bomb1 = playerContent.Load<Texture2D>("Bombs/Bomb1");
            Bang = playerContent.Load<Texture2D>("Bombs/explosion");

            playerCollides = false;
            personRectangle = new Rectangle(192, 192, 32, 32);
            
            
        }

        public void Update(GameTime gameTime)
        {
            playerCollides = false;
            PlayerMovement();

            // cast gameTime as a float
            elapsed += (float)gameTime.ElapsedGameTime.TotalMilliseconds;

            if(elapsed >= delay)
            {
                if (frames >= 1)
                {
                    frames = 0;
                }
                else
                {
                    frames++;
                }
                elapsed = 0;
            }
           

            bombTimer += (float)gameTime.ElapsedGameTime.TotalSeconds;
            if (bombTimer >= delay)
            {
                if (bombTimer >= BombTimerEnd)
                {
                    Explosion = true;
                }
                else
                    Explosion = false;
                bombTimer = 0;
            }
            


            // get section of sprite using frame number
            SourceRect = new Rectangle(32 * frames, 0, 32, 32);
            BombSourceRect = new Rectangle(0, 0, 48, 48);
            BombGoesOff(gameTime);
           
        }

        public void PlayerMovement() //pass in relavent keys for multiplayer
        {
            
            KeyboardState keyboard = Keyboard.GetState();
            DirectionFacing = "Idle";
            //up and down movement velocity
            if (Keyboard.GetState().IsKeyDown(Keys.W))
            {
                position.Y -= movementSpeed;
                DirectionFacing = "WalkingUp";
            }
            

            if (Keyboard.GetState().IsKeyDown(Keys.S))
            {
                position.Y += movementSpeed;
                DirectionFacing = "WalkingDown";
            }
            //left and right movement velocity
            if (Keyboard.GetState().IsKeyDown(Keys.D))
            {
                position.X += movementSpeed;
                DirectionFacing = "WalkingRight";
            }
            if (Keyboard.GetState().IsKeyDown(Keys.A))
            {
                position.X -= movementSpeed;
                DirectionFacing = "WalkingLeft";
            }

            if (Keyboard.GetState().IsKeyDown(Keys.Space))
            {
                BombRectangle = personRectangle;
                PlacedBomb = true;
                
            }
            personRectangle = new Rectangle((int)position.X, (int)position.Y, 32, 32);
            
            Collision(personRectangle);
        }

        public void BombGoesOff(GameTime gameTime)
        {
            float TheBombTimer = 0;
            int ticks = 3;

            TheBombTimer += (float)gameTime.ElapsedGameTime.TotalSeconds;
            if (TheBombTimer >= ticks)
            {
                BlowUp = true;
            }
            else 
            {
                BlowUp = false;
            }

        }

        public void Collision(Rectangle newRectangle)
        {

            personRectangle.CollisionDetected(newRectangle);
        }

        public void CollisionOn()
        {
            //Detected = true;
        }

        public void CollisionOff()
        {
            //Detected = false;
        }

        public void Draw(SpriteBatch spriteBatch)
        {
            if (DirectionFacing == "WalkingRight")
                spriteBatch.Draw(RightWalk, personRectangle, SourceRect, Color.White);
            else if (DirectionFacing == "WalkingLeft")
                spriteBatch.Draw(LeftWalk, personRectangle, SourceRect, Color.White);
            else if (DirectionFacing == "WalkingUp")
                spriteBatch.Draw(UpWalk, personRectangle, SourceRect, Color.White);
            else if (DirectionFacing == "WalkingDown")
                spriteBatch.Draw(DownWalk, personRectangle, SourceRect, Color.White);
            else
                spriteBatch.Draw(Idle, personRectangle, SourceRect, Color.White);


            if(PlacedBomb)
            {
                if (Explosion == true)
                {
                    spriteBatch.Draw(Bomb1, BombRectangle, Color.Red);
                }
                else if(Explosion == false)
                    spriteBatch.Draw(Bomb1, BombRectangle, Color.White);

            }
        }

       
       

    }
}
