package server.org.world.map;

import java.util.Hashtable;
import java.util.Map;


public class VirtualWorld
{

    public VirtualWorld()
    {
    }

    public static final void main(String args[])
    {
        init();
    }

	public static final void init()
    {
        for(int i = 0; i < 10331; i++)
        {
            I[i] = null;
            I[i] = new Hashtable();
        }

        server.org.world.map.I.I(true);
        for(int j = 0; j < 0x989680; j++)
            I(0, 3222, 3242, 3223, 3243, 0);

    }

    public static final boolean I(int height, int currentX, int currentY, int futureX, int futureY, int a)
    {
		if (height % 4 == 0)
			height = 0;
		if (currentX != futureX && futureY != currentY) {
			return server.org.world.map.I.I(height, currentX, currentY, currentX, futureY, a) && server.org.world.map.I.I(height, currentX, currentY, futureX, currentY, a) 
					&& server.org.world.map.I.I(height, currentX, currentY, futureX, futureY, a);
		}
        return server.org.world.map.I.I(height, currentX, currentY, futureX, futureY, a);
    }

	public static Map I[] = new Hashtable[10331];

}


