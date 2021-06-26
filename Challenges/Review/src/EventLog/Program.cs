using System;

namespace EventLog
{
    class Program
    {
        static void Main(string[] args)
        {
            using (var logger = new Logger(1))
            {
                //Do things here
            }
        }
    }
}
