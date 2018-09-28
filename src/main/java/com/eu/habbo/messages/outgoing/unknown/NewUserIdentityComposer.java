package com.eu.habbo.messages.outgoing.unknown;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class NewUserIdentityComposer extends MessageComposer
{
    private final Habbo habbo;

    public NewUserIdentityComposer(Habbo habbo)
    {
        this.habbo = habbo;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.NewUserIdentityComposer);
        this.response.appendInt(this.habbo.noobStatus());
        return this.response;
    }
}
