package serverPackage;

import javacard.framework.Applet;
import javacard.framework.*;

public class ServerApplet extends Applet implements SimpleSI{

    private static byte[] state_nvm;
    private static byte[] state_ram;

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        state_nvm = new byte[] {(byte)0};
        state_ram = JCSystem.makeTransientByteArray((short)1, JCSystem.CLEAR_ON_RESET);
        new ServerApplet().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
    }

    public Shareable getShareableInterfaceObject​(AID clientAID, byte parameter){
        return this;
    }

    public void process(APDU apdu) {
    }

    public void foo(short sw_corrupted_both, short sw_corrupted_nvm, short sw_corrupted_ram) {
        JCSystem.beginTransaction();
        ++state_nvm[0];
        ++state_ram[0];
        bar(sw_corrupted_both, sw_corrupted_nvm, sw_corrupted_ram);
        --state_ram[0];
        --state_nvm[0];
        JCSystem.commitTransaction();
    }

    private void bar(short sw_corrupted_both, short sw_corrupted_nvm, short sw_corrupted_ram) {

        if (state_nvm[0] > 1 && state_ram[0] > 1)
            ISOException.throwIt(sw_corrupted_both);

        if (state_nvm[0] > 1)
            ISOException.throwIt(sw_corrupted_nvm);

        if (state_ram[0] > 1)
            ISOException.throwIt(sw_corrupted_ram);
    }
}