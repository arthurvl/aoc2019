import day13.ArcadeCabinet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    @Test
    public void drawsWallTile() {
        ArcadeCabinet cabinet = new ArcadeCabinet();
        cabinet.connectInput(new ByteArrayInputStream("0\n0\n1\n".getBytes()));
        cabinet.run();
        cabinet.join();

        assertEquals(1, cabinet.tileAt(0,0));
    }

    @Test
    public void drawsWallTileAtOtherLocation() {
        ArcadeCabinet cabinet = new ArcadeCabinet();
        cabinet.connectInput(new ByteArrayInputStream("5\n5\n1\n".getBytes()));
        cabinet.run();
        cabinet.join();

        assertEquals(0, cabinet.tileAt(0,0));
        assertEquals(1, cabinet.tileAt(5,5));
    }
}