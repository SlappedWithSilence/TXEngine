package txengine.systems.dungeon;

import txengine.systems.room.action.Action;

import java.util.List;

public abstract class DungeonGimmick {

    // If locked, whenever this gimmick is used all doors in that room will require a key to be used.
    public enum Type {
        LOCKED,
        UNLOCKED
    }

    protected final Dungeon owner;

    public DungeonGimmick(Dungeon owner) {
        this.owner = owner;
    }

    public abstract List<Action> get();

    protected Type type;

    public Dungeon getOwner() {
        return owner;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
