package bancomat;

import mware_lib.objects.MWareObject;

public abstract class _BankmanagerImplBase{

    public abstract String getAccountID(int key);

    public static _BankmanagerImplBase narrowCast(Object rawObjectRef) {
        return new Bankmanager((MWareObject)rawObjectRef);
    }
}
