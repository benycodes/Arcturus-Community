package com.eu.habbo.messages.incoming.rooms.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatType;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.plugin.events.users.UserTalkEvent;

public class RoomUserShoutEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        if(this.client.getHabbo().getHabboInfo().getCurrentRoom() == null)
            return;

        if(!this.client.getHabbo().getHabboStats().allowTalk())
            return;


        RoomChatMessage message = new RoomChatMessage(this);

        if (message.getMessage().length() <= RoomChatMessage.MAXIMUM_LENGTH)
        {
            if (Emulator.getPluginManager().fireEvent(new UserTalkEvent(this.client.getHabbo(), message, RoomChatType.SHOUT)).isCancelled())
            {
                return;
            }

            this.client.getHabbo().getHabboInfo().getCurrentRoom().talk(this.client.getHabbo(), message, RoomChatType.SHOUT);

            if (!message.isCommand)
            {
                if (RoomChatMessage.SAVE_ROOM_CHATS)
                {
                    Emulator.getThreading().run(message);
                }
            }
        }
        else
        {
            String reportMessage = Emulator.getTexts().getValue("scripter.warning.chat.length").replace("%username%", this.client.getHabbo().getHabboInfo().getUsername()).replace("%length%", message.getMessage().length() + "");
            Emulator.getGameEnvironment().getModToolManager().quickTicket(this.client.getHabbo(), "Scripter", reportMessage);
            Emulator.getLogging().logUserLine(reportMessage);
        }
    }
}
