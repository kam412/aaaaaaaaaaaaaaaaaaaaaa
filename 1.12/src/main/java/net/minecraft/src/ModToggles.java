package net.minecraft.src;

import org.lwjgl.input.Keyboard;

/**
 * Central on/off state for custom client mods, toggled with Right Shift.
 * Add new mods by adding a boolean field + a line in the cycle order.
 */
public class ModToggles
{
    public static boolean fullbright = false;
    public static boolean zoom = false; // zoom uses hold-to-activate separately, see below
    public static boolean fpsBooster = true; // on by default, it's pure optimization
    public static boolean noHurtCam = false;   // example "more mods" - disables screen shake on damage
    public static boolean noHitDelay = false;  // example - removes weapon "cooldown" screen tint

    private static boolean rShiftWasDown = false;
    private static boolean tabWasDown = false;

    /** menu state: is the toggle overlay currently open */
    public static boolean menuOpen = false;

    private static final String[] MOD_NAMES = {
        "Fullbright",
        "Zoom (hold Z)",
        "FPS Booster",
        "No Hurt Cam",
        "No Hit Delay"
    };

    private static int selectedIndex = 0;

    /**
     * Call this once per client tick (e.g. from Minecraft.runTick or a similar
     * always-running tick method).
     */
    public static void onTick()
    {
        boolean rShiftDown = Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);

        // rising edge only - toggles the menu open/closed, doesn't spam-toggle every tick
        if (rShiftDown && !rShiftWasDown)
        {
            menuOpen = !menuOpen;
        }
        rShiftWasDown = rShiftDown;

        if (menuOpen)
        {
            boolean tabDown = Keyboard.isKeyDown(Keyboard.KEY_TAB);
            if (tabDown && !tabWasDown)
            {
                selectedIndex = (selectedIndex + 1) % MOD_NAMES.length;
            }
            tabWasDown = tabDown;

            if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
            {
                toggleByIndex(selectedIndex);
            }
        }
    }

    private static void toggleByIndex(int index)
    {
        switch (index)
        {
            case 0: fullbright = !fullbright; break;
            case 1: zoom = !zoom; break;
            case 2: fpsBooster = !fpsBooster; break;
            case 3: noHurtCam = !noHurtCam; break;
            case 4: noHitDelay = !noHitDelay; break;
        }
    }

    public static String[] getModNames()
    {
        return MOD_NAMES;
    }

    public static int getSelectedIndex()
    {
        return selectedIndex;
    }

    public static boolean isEnabled(int index)
    {
        switch (index)
        {
            case 0: return fullbright;
            case 1: return zoom;
            case 2: return fpsBooster;
            case 3: return noHurtCam;
            case 4: return noHitDelay;
            default: return false;
        }
    }
}
