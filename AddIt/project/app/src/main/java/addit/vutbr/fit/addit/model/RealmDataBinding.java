package addit.vutbr.fit.addit.model;

import io.realm.RealmChangeListener;

/**
 * Neccessary hack for data binding with RealmObjects
 * @see https://medium.com/@Zhuinden/realm-1-2-0-android-data-binding-1dc06822287f#.u6zq2fenf
 */
public interface RealmDataBinding {
    interface Factory {
        RealmChangeListener create();
    }

    RealmDataBinding.Factory FACTORY = () -> element -> {
        if(element instanceof RealmDataBinding) {
            ((RealmDataBinding)element).notifyChange();
        }
    };

    void notifyChange();
}
