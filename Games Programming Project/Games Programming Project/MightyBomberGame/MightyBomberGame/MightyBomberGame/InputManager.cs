using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace MightyBomberGame
{
    public class InputManager
    {
        KeyboardState previousKeyState, keyState;

        public void Update()
        {
            previousKeyState = keyState;
            keyState = Keyboard.GetState();
        }

        public bool KeyPressed(Keys key)
        { 
            if(keyState.IsKeyDown(key) && previousKeyState.IsKeyUp(key))
                return true;
            return false;
        }
        //two buttons pressed at once
        public bool keyPressed(params Keys[] keys)
        {
            foreach (Keys key in keys)
            {
                if (keyState.IsKeyDown(key) && previousKeyState.IsKeyUp(key))
                    return true;
            }
            return false;
        }
        //single key being released
        public bool KeyReleased(Keys key)
        {
            if (keyState.IsKeyUp(key) && previousKeyState.IsKeyDown(key))
                return true;
            return false;
        }
        //multiple key releases
        public bool KeyReleased(params Keys[] keys)
        {
            foreach (Keys key in keys)
            {
                if (keyState.IsKeyUp(key) && previousKeyState.IsKeyDown(key))
                    return true;
            }
            return false;
        }
        //multiple keys being pressed
        public bool KeyDown(Keys key)
        {
            if (keyState.IsKeyDown(key))
                return true;
            return false;
        }

        public bool KeyDown(params Keys[] keys)
        {
            foreach (Keys key in keys)
            {
                if (keyState.IsKeyDown(key))
                    return true;
            }
            return false;
        }
    }
}
