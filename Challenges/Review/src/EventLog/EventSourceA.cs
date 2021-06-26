using System;
using System.Collections.Generic;
using System.Text;

namespace EventLog
{
    class EventSourceA : IEventSource
    {
        public event EventHandler EventOccured;

        public void Dispose()
        {
            throw new NotImplementedException();
        }
    }
}
