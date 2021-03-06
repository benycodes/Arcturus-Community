package com.eu.habbo.habbohotel.items.interactions;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionTeleportTile extends InteractionTeleport
{
    public InteractionTeleportTile(ResultSet set, Item baseItem) throws SQLException
    {
        super(set, baseItem);
    }

    public InteractionTeleportTile(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects)
    {
        return true;
    }

    @Override
    public void onWalkOn(RoomUnit roomUnit, Room room, Object[] objects) throws Exception
    {
        if (roomUnit != null)
        {
            if (roomUnit.getGoal().is(this.getX(), this.getY()) && this.canWalkOn(roomUnit, room, objects))
            {
                Habbo habbo = room.getHabbo(roomUnit);

                if (habbo != null)
                {
                    if (!habbo.getRoomUnit().isTeleporting)
                    {
                        this.startTeleport(room, habbo);
                    }
                }
            }
        }
    }

    @Override
    protected boolean canUseTeleport(GameClient client, Room room)
    {
        if(client.getHabbo().getRoomUnit().isTeleporting)
            return false;

        if (client.getHabbo().getRoomUnit().getCurrentLocation().is(this.getX(), this.getY()))
            return true;

        return true;
    }
}
