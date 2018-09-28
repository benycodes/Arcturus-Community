package com.eu.habbo.messages.incoming.modtool;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;

public class ModToolChangeRoomSettingsEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        if(this.client.getHabbo().hasPermission(Permission.ACC_SUPPORTTOOL))
        {
            Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(this.packet.readInt());

            if (room != null)
            {
                boolean lockDoor = this.packet.readInt() == 1;
                boolean changeTitle = this.packet.readInt() == 1;
                boolean kickUsers = this.packet.readInt() == 1;

                Emulator.getGameEnvironment().getModToolManager().roomAction(room, this.client.getHabbo(), kickUsers, lockDoor, changeTitle);
            }
        }
        else
        {
            Emulator.getGameEnvironment().getModToolManager().quickTicket(this.client.getHabbo(), "Scripter", Emulator.getTexts().getValue("scripter.warning.modtools.roomsettings").replace("%username%", this.client.getHabbo().getHabboInfo().getUsername()));
        }
    }
}
