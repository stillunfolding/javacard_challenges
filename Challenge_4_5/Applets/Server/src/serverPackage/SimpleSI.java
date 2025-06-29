package serverPackage;

import javacard.framework.Shareable;

public interface SimpleSI extends Shareable {
    public void foo(short sw_cond1, short sw_cond2, short sw_cond3);
}
