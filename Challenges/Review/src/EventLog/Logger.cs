using System;
using System.Collections.Generic;
using System.Text;

namespace EventLog
{
    class Logger : IDisposable
    {
        private IEventSource _et;

        public Logger(int et)
        {
            if (et == 0)
            {
                _et = new EventSourceA();
            }
            else
            {
                _et = new EventSourceB();
            }

            _et.EventOccured += _et_EventOccured;
        }

        private void _et_EventOccured(object sender, EventArgs e)
        {
            Console.WriteLine("Event Occured");

            try
            {
                ProcessEvent();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }


        private static void ProcessEvent()
        {
            Random rm = new Random(0);
            int j = 0;

            for(var lt =0; lt<3; lt++)
            {
                j = j >> rm.Next(0, 3);
            }

            Console.WriteLine(j);
        }

        public void Dispose()
        {
        }
    }
}
