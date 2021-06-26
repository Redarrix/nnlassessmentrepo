using System;

namespace EventLog
{
    internal interface IEventSource: IDisposable
    {
        event EventHandler EventOccured;
    }
}